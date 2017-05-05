package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entities.Patient;
import entities.Relative;

public class PsychiatryServer implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JDBC jdbc = new JDBC();
	BufferedReader inFromClient;
	PrintWriter outToClient, file;
	ObjectOutputStream outObject;
	Socket socket;
	String messageFromClient;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	
	List<Patient> convertRsToList(ResultSet rs) throws SQLException{
		List<Patient> Patients=new ArrayList<Patient>();
		while(rs.next()) {
			Patient patient=new Patient();
			patient.PatientID=rs.getString("PatientID");
		   patient.Password=rs.getString("Password");
		   patient.Name=rs.getString("Name");
		   patient.Surname=rs.getString("Surname");
		   patient.Phone=rs.getInt("Phone");
		   System.out.println(patient.Phone);
		   patient.Email=rs.getString("Email");
		   patient.Address=rs.getString("Address");
		   patient.NumOfIncidents=rs.getInt("NumOfIncidents");
		   patient.SelfHarmRisk=rs.getBoolean("SelfHarmRisk");
		   patient.OthersHarmRisk=rs.getBoolean("OthersHarmRisk");
		   patient.RiskStatus=rs.getString("RiskStatus");
		   patient.ChangedByPatient=rs.getBoolean("ChangedByPatient");
		   patient.DeadReadOnly=rs.getBoolean("DeadReadOnly");

		  Patients.add(patient);
		} 
		return Patients;
	}
	
	List<Relative> convertRsToRelatList(ResultSet rs) throws SQLException{
		List<Relative> Relatives=new ArrayList<Relative>();
		while(rs.next()) {
			Relative relative=new Relative();
			relative.RelativeID=rs.getInt("RelativeID");
			relative.PatientID=rs.getString("PatientID");
		   relative.Name=rs.getString("Name");
		   relative.Surname=rs.getString("Surname");
		   relative.Phone=rs.getInt("Phone");
		   relative.Email=rs.getString("Email");
		   relative.Address=rs.getString("Address");
		   relative.Relationship=rs.getString("Relationship");


		  Relatives.add(relative);
		} 
		return Relatives;
	}

	/*
	 * Server gets username, password and role from client,checks if user exists,
	 * sends message accordingly role and writes in log file the action and the datetime.
	 */
	void login() throws Exception {
		System.out.println("mpika sto login");
		String username = inFromClient.readLine();
		System.out.println("diavasa username " + username);
		String password = inFromClient.readLine();
		System.out.println("diavasa password " + password);
		String role = inFromClient.readLine();
		System.out.println("diavasa role " + role);
		try {
			ResultSet rs = jdbc.login(username, password, role);
			if (rs.next()) {
				if (role.equals("Patient")) {
					file.print(username + " log in as Patient... ");
					file.println(dtf.format(now));
					file.flush();
					outToClient.println("Patient");
					outToClient.flush();
				}
				else {
					String roleGUI = rs.getString("StaffType");
					if (roleGUI.equals("Doctor")) {
						file.print(username + " log in as Doctor... ");
						file.println(dtf.format(now));
						file.flush();
						outToClient.println("Doctor");
						outToClient.flush();
					} else if (roleGUI.equals("Nurse") || roleGUI.equals("Health Visitor")) {
						file.print(username + " log in as "+ roleGUI+"... ");
						file.println(dtf.format(now));
						file.flush();
						outToClient.println("Nurse-HealthVisitor");
						outToClient.flush();
					} else if (roleGUI.equals("Receptionist")) {
						file.print(username + " log in as Receptionist... ");
						file.println(dtf.format(now));
						file.flush();
						outToClient.println("Receptionist");
						outToClient.flush();
					} else if (roleGUI.equals("Medical Records")) {
						file.print(username + " log in as Medical Records... ");
						file.println(dtf.format(now));
						file.flush();
						outToClient.println("MedicalRecords");
						outToClient.flush();
					} else if (roleGUI.equals("Management")) {
						file.print(username + " log in as Management... ");
						file.println(dtf.format(now));
						file.flush();
						outToClient.println("Management");
						outToClient.flush();
					}
				}
			} else {
				file.print(username + " try to log in but failed... ");
				file.println(dtf.format(now));
				file.flush();
				outToClient.println("wrong");
				outToClient.flush();
			}

		} catch (Exception er) {
			// Ignore the error and continues
		}
	}

	/*
	 * Server gets some fields from client, add the new user with those fields in database,
	 * sends successful or not message and writes in log file the action and the datetime.
	 */
	void signup() throws Exception {
		String username = inFromClient.readLine();
		String password = inFromClient.readLine();
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		String role = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		file.print(name + " " + surname + " sign up with username " + username + "... ");
		file.println(dtf.format(now));
		file.flush();

		try {
			ResultSet rs = jdbc.checksignup(username);
			if (!rs.next()) {
				jdbc.signup(username, password, name, surname, role, phone, email, address);
				outToClient.println("success");
			} else {
				outToClient.println("alreadyExists");
			}
			outToClient.flush();
		} catch (Exception er) {
			// Ignore the error and continues
		}
	}
	
	/*
	 * When client log outs, server writes in log file that a user with "username" have just log out at this time.
	 */
	void logout() {
		try {
			String username = inFromClient.readLine();
			file = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
			file.print(username + " log out... ");
			file.println(dtf.format(now));
			file.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Server gets username, old password and new password from client, checks if username exists,
	 * changes old password with new password and writes in log file the action and the datetime.
	 */
	void changePassword() throws IOException {
		String username = inFromClient.readLine();
		String oldpassword = inFromClient.readLine();
		String newpassword = inFromClient.readLine();
		String role = inFromClient.readLine();
		file.print(username + " changed password... ");
		file.println(dtf.format(now));
		file.flush();
		System.out.println(oldpassword + " " + username + " " + role);

		try {
			ResultSet rs = jdbc.login(username, oldpassword, role);
			System.out.println("ekana login");

			if (rs.next()) {
				jdbc.changepassword(username, oldpassword, newpassword, role);
				System.out.println("allaksa ton kwdiko");
				outToClient.println("passwordChanged");//send to client the message password changed
				outToClient.flush();
			} else {
				outToClient.println("wrongPassword");//send to client the message wrong password
				outToClient.flush();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void addPatient() throws IOException{
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		String username = inFromClient.readLine();
		String password = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		jdbc.addPatient(name,surname,username,password,phone,email,address);
		
		file.print("patient "+name+" "+surname+" added with username "+username+"... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("patientAdded");//add patient
		outToClient.flush();
	}
	
	void updatePatient() throws IOException, SQLException{
		String username = inFromClient.readLine();
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();		
		System.out.println("update before");
		jdbc.updatePatient(username, name, surname,phone, email, address);
		System.out.println("update after");
		
		file.print("patient with name "+name+" "+surname+" updated... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("patientUpdated");//updated patient
		outToClient.flush();
		ResultSet rs=jdbc.printPatient(username);
		List<Patient> ls = convertRsToList(rs);
		outObject.writeObject(ls);	}
	
	void searchPatient() throws IOException, SQLException{
		String username = inFromClient.readLine();
		ResultSet rs=jdbc.printPatient(username);
		file.print("patient with username "+username+" searched... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("patientSearched");//searched patient
		outToClient.flush();
		List<Patient> ls = convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("search after");
	}
	void deletePatient()throws IOException, SQLException{
		String username = inFromClient.readLine();
		jdbc.deletePatient(username);
		
		file.print("patient with username "+username+" deleted... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("patientDeleted");//deleted patient
		outToClient.flush();
	}	
	void addRelative()throws IOException{
		String patientusername = inFromClient.readLine();
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		String relationship = inFromClient.readLine();
		jdbc.addRelative(patientusername,name,surname,phone,email,address,relationship);
		file.print(relationship+" of "+patientusername+" added... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("success");//successfully added relative!
		outToClient.flush();
	}
	void updateRelative() throws IOException, SQLException{		
		int id = Integer.parseInt(inFromClient.readLine());
		System.out.println("id: "+id);
		String patientid = inFromClient.readLine();
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();		
		String relationship = inFromClient.readLine();	
		jdbc.updateRelative(id,patientid,name,surname,phone,email,address,relationship);
		file.print(relationship+" of "+patientid+" updated... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("relativeUpdated");//updated patient
		outToClient.flush();
		ResultSet rs=jdbc.printRelative(id);
		List<Relative> ls = convertRsToRelatList(rs);
		outObject.writeObject(ls);	
		System.out.println("esteila kai to resultset");
		}
	
		void searchRelative() throws IOException, SQLException {
			int id = Integer.parseInt(inFromClient.readLine());
			ResultSet rs=jdbc.printRelative(id);
			file.print("relative with id "+id+" searched... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("relativeSearched");//searched relative
			outToClient.flush();
			List<Relative> ls = convertRsToRelatList(rs);
			outObject.writeObject(ls);
			System.out.println("relative search after");			
		}

		void deleteRelative() throws IOException {
			int id = Integer.parseInt(inFromClient.readLine());
			jdbc.deleteRelative(id);
			
			file.print("relative with id "+id+" deleted... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("relativeDeleted");//deleted patient
			outToClient.flush();
		
		}
	
	void start() throws IOException {
		jdbc.conn = jdbc.getDBConnection();
		if (jdbc.conn == null) {
			return;
		}
		ServerSocket listener = new ServerSocket(8080);
		try {
			while (true) {
				socket = listener.accept();// accept request
				System.out.println("connected");
				inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outToClient = new PrintWriter(socket.getOutputStream(), true);
				outObject = new ObjectOutputStream(socket.getOutputStream());
				file = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
				file.print("Start... ");
				file.println(dtf.format(now));
				file.flush();
				System.out.println("egrapsa sto log start");

				while ((messageFromClient = inFromClient.readLine()) != null) {
					System.out.println("Message from client : " + messageFromClient);
					if (messageFromClient.equals("login"))
						login();
					if (messageFromClient.equals("signup"))
						signup();
					if (messageFromClient.equals("logout"))
						logout();
					if (messageFromClient.equals("changePassword"))
						changePassword();
					if (messageFromClient.equals("addPatient"))
						addPatient();
					if (messageFromClient.equals("searchPatient"))
						searchPatient();
					if (messageFromClient.equals("updatePatient"))
						updatePatient();
					if (messageFromClient.equals("deletePatient"))
						deletePatient();
					if (messageFromClient.equals("relativeForm"))
						addRelative();
					if (messageFromClient.equals("updateRelative"))
						updateRelative();
					if (messageFromClient.equals("deleteRelative"))
						deleteRelative();
					if (messageFromClient.equals("searchRelative"))
						searchRelative();

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			file.close();
			listener.close();
			socket.close();
		}

	}
	public static void main(String[] args) throws IOException {
		PsychiatryServer s = new PsychiatryServer();
		s.start();
	}

}
