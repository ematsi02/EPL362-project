package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
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

import entities.Comment;
import entities.Consultation;
import entities.Incident;
import entities.Medication;
import entities.Patient;
import entities.Relative;
import entities.Treatment;
import entities.MedicationReaction;

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
	Patient patient;
	Relative relative;
	Incident incident;
	Treatment treatment;
	Medication medication;
	Consultation consultation;
	MedicationReaction reaction;
	Comment comment;


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
		List<Patient> ls = patient.convertRsToList(rs);
		outObject.writeObject(ls);	}
	
	void searchPatient() throws IOException, SQLException{
		String username = inFromClient.readLine();
		ResultSet rs=jdbc.printPatient(username);
		file.print("patient with username "+username+" searched... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("patientSearched");//searched patient
		outToClient.flush();
		List<Patient> ls = patient.convertRsToList(rs);
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
		outToClient.println("relativeUpdated");//updated relative
		outToClient.flush();
		ResultSet rs=jdbc.printRelative(id);
		List<Relative> ls = relative.convertRsToRelatList(rs);
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
			List<Relative> ls = relative.convertRsToRelatList(rs);
			outObject.writeObject(ls);
			System.out.println("relative search after");			
		}

		void deleteRelative() throws IOException {
			int id = Integer.parseInt(inFromClient.readLine());
			jdbc.deleteRelative(id);
			
			file.print("relative with id "+id+" deleted... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("relativeDeleted");//deleted relative
			outToClient.flush();
		}
		void addincident() throws IOException{
			String patientid = inFromClient.readLine();
			String type = inFromClient.readLine();
			String shortDescription = inFromClient.readLine();
			String description = inFromClient.readLine();
			String date = inFromClient.readLine();			
			jdbc.addIncident(patientid,type,shortDescription,description,date);
			file.print("Incident for "+patientid+" added... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("success");//add incident
			outToClient.flush();
		}
		void updateIncident() throws IOException, SQLException{
			int id = Integer.parseInt(inFromClient.readLine());
			String patientid = inFromClient.readLine();
			String type = inFromClient.readLine();
			String shortDescription = inFromClient.readLine();
			String description = inFromClient.readLine();		
			String date = inFromClient.readLine();	
			jdbc.updateIncident(id,patientid,type,shortDescription,description,date);
			file.print(patientid+"incident's "+id+" updated... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("incidentUpdated");//updated incident
			outToClient.flush();
			ResultSet rs=jdbc.printIncident(id);
			List<Incident> ls = incident.convertRsToList(rs);
			outObject.writeObject(ls);	
			System.out.println("esteila kai to resultset");
		}
		void deleteIncident() throws IOException {
			int id = Integer.parseInt(inFromClient.readLine());
			jdbc.deleteIncident(id);
			
			file.print("incident with id "+id+" deleted... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("incidentDeleted");//deleted incident
			outToClient.flush();
		}
		void searchIncident() throws IOException, SQLException {
			int id = Integer.parseInt(inFromClient.readLine());			
			ResultSet rs=jdbc.printIncident(id);
			file.print("incident with id "+id+" searched... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("incidentSearched");//searched incident
			outToClient.flush();
			List<Incident> ls = incident.convertRsToList(rs);
			outObject.writeObject(ls);
			System.out.println("incident search after");			
		}

		void addTreatment()throws IOException{
			String patientid=inFromClient.readLine();
			String startDate=inFromClient.readLine();
			String endDate=inFromClient.readLine();
			String diagnosis=inFromClient.readLine();
			String description=inFromClient.readLine();
			String staffid=inFromClient.readLine();
			jdbc.addTreatment(patientid,startDate,endDate, diagnosis,description, staffid);
			file.print("Treatment for "+patientid+" added... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("treatmentAdded");//added treatment
		}
		void updateTreatment() throws IOException, SQLException{
			int id = Integer.parseInt(inFromClient.readLine());
			String patientid = inFromClient.readLine();
			String startDate = inFromClient.readLine();
			String endDate = inFromClient.readLine();
			String diagnosis = inFromClient.readLine();
			String description = inFromClient.readLine();		
			String staffid = inFromClient.readLine();
			jdbc.updateTreatment(id,patientid,startDate,endDate,diagnosis,description,staffid);
			file.print(patientid+"treatment's "+id+" updated... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("treatmentUpdated");//updated treatment
			outToClient.flush();
			ResultSet rs=jdbc.printTreatment(id);
			List<Treatment> ls = treatment.convertRsToList(rs);
			outObject.writeObject(ls);	
			System.out.println("esteila kai to resultset");
		}
		void deleteTreatment() throws IOException {
			int id = Integer.parseInt(inFromClient.readLine());
			jdbc.deleteTreatment(id);
			
			file.print("treatment with id "+id+" deleted... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("treatmentDeleted");//deleted treatment
			outToClient.flush();
		}
		void searchTreatment() throws IOException, SQLException {
			int id = Integer.parseInt(inFromClient.readLine());			
			ResultSet rs=jdbc.printTreatment(id);
			file.print("treatment with id "+id+" searched... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("treatmentSearched");//searched treatment
			outToClient.flush();
			List<Treatment> ls = treatment.convertRsToList(rs);
			outObject.writeObject(ls);
			System.out.println("incident search after");			
		}
		
		void addMedication()throws IOException{
			String brand=inFromClient.readLine();
			String name=inFromClient.readLine();
			String description=inFromClient.readLine();
			String effects=inFromClient.readLine();
			int dose=Integer.parseInt(inFromClient.readLine());				
			jdbc.addMedication(brand,name,description, effects,dose);
			file.print("Medication with name "+name+" added... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("medicationAdded");//added medication
		}
		
		void updateMedication() throws IOException, SQLException{
			int id = Integer.parseInt(inFromClient.readLine());
			String brand = inFromClient.readLine();
			String name = inFromClient.readLine();
			String description = inFromClient.readLine();		
			String effects = inFromClient.readLine();
			int dose = Integer.parseInt(inFromClient.readLine());
			jdbc.updateMedication(id,brand,name,description,effects,dose);
			file.print("Medication "+id+" updated... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("medicationUpdated");//updated medication
			outToClient.flush();
			ResultSet rs=jdbc.printTreatment(id);
			List<Medication> ls = medication.convertRsToList(rs);
			outObject.writeObject(ls);	
			System.out.println("esteila kai to resultset");
		}
		void deleteMedication() throws IOException {
			int id = Integer.parseInt(inFromClient.readLine());
			jdbc.deleteMedication(id);
			
			file.print("medication with id "+id+" deleted... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("medicationDeleted");//deleted medication
			outToClient.flush();
		}
		void searchMedication() throws IOException, SQLException{
			int id = Integer.parseInt(inFromClient.readLine());			
			ResultSet rs=jdbc.printMedication(id);
			file.print("Medication with id "+id+" searched... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("medicationSearched");//searched medication
			outToClient.flush();
			List<Medication> ls = medication.convertRsToList(rs);
			outObject.writeObject(ls);
			System.out.println("search after");
		}
		
		void addConsultation()throws IOException{
			String patientid=inFromClient.readLine();
			String staffid=inFromClient.readLine();
			String subject=inFromClient.readLine();
			String dateBooked=inFromClient.readLine();
			String date=inFromClient.readLine();
			String time=inFromClient.readLine();
			int treatmentid=Integer.parseInt(inFromClient.readLine());
			jdbc.addConsultation(patientid,staffid,subject, dateBooked,date,time,treatmentid);
			file.print("Consultation for patient with username "+patientid+" with"+staffid+" added... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("consulationAdded");//added consultation
		}
		void updateConsultation() throws IOException, SQLException{
			int id = Integer.parseInt(inFromClient.readLine());
			String patientid = inFromClient.readLine();
			String staffid = inFromClient.readLine();
			String subject = inFromClient.readLine();		
			String dateBooked = inFromClient.readLine();
			String date = inFromClient.readLine();		
			String time = inFromClient.readLine();	
			int attended = Integer.parseInt(inFromClient.readLine());
			int updated = Integer.parseInt(inFromClient.readLine());
			int treatmentid = Integer.parseInt(inFromClient.readLine());			
			jdbc.updateConsultation(id,patientid,staffid,subject,dateBooked,date,time,attended,updated,treatmentid);
			file.print("Consultation "+id+" updated... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("consultationUpdated");//updated Consultation
			outToClient.flush();
			ResultSet rs=jdbc.printConsultation(id);
			List<Consultation> ls = consultation.convertRsToList(rs);
			outObject.writeObject(ls);	
			System.out.println("esteila kai to resultset");
			
		}
		void deleteConsultation() throws IOException{
			int id = Integer.parseInt(inFromClient.readLine());
			jdbc.deleteConsultation(id);
			file.print("Consultation with id "+id+" deleted... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("consultationDeleted");//deleted medication
			outToClient.flush();
			
		}
		void searchConsultation() throws IOException, SQLException{
			int id = Integer.parseInt(inFromClient.readLine());			
			ResultSet rs=jdbc.printConsultation(id);
			file.print("Consultation with id "+id+" searched... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("consultationSearched");//searched Consultation
			outToClient.flush();
			List<Consultation> ls = consultation.convertRsToList(rs);
			outObject.writeObject(ls);
			System.out.println("search after");
			
		}
	
	void addReaction() throws IOException{
			String patientid = inFromClient.readLine();
			int medicationid = Integer.parseInt(inFromClient.readLine());
			String reactionType = inFromClient.readLine();
			String description = inFromClient.readLine();
			jdbc.addMedicationReaction(patientid, medicationid, reactionType, description);
			
			file.print("reaction of patient with username "+patientid+" added with medication id "+medicationid+"... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("reactionAdded");//add patient
			outToClient.flush();
			System.out.println("telos");
		}
		
		void updateReaction() throws IOException, SQLException{
			String patientid = inFromClient.readLine();
			int medicationid = Integer.parseInt(inFromClient.readLine());
			String reactionType = inFromClient.readLine();
			String description = inFromClient.readLine();
			System.out.println("update before");
			jdbc.updateMedicationReaction(patientid, medicationid, reactionType, description);
			System.out.println("update after");
			
			file.print("reaction of patient with username "+patientid+" and medication id "+medicationid+" updated ... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("reactionUpdated");//updated patient
			outToClient.flush();
			ResultSet rs=jdbc.printMedicationReaction(patientid, medicationid);
			List<MedicationReaction> ls = reaction.convertRsToList(rs);
			outObject.writeObject(ls);	}
		
		void searchReaction() throws IOException, SQLException{
			String patientid = inFromClient.readLine();
			int medicationid = Integer.parseInt(inFromClient.readLine());
			ResultSet rs=jdbc.printMedicationReaction(patientid, medicationid);
			file.print("reaction of patient with username "+patientid+" and medication id "+medicationid+" searched... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("reactionSearched");//searched patient
			outToClient.flush();
			List<MedicationReaction> ls = reaction.convertRsToList(rs);
			outObject.writeObject(ls);
			System.out.println("search after");
		}
		void deleteReaction()throws IOException, SQLException{
			String patientid = inFromClient.readLine();
			int medicationid = Integer.parseInt(inFromClient.readLine());
			jdbc.deleteMedicationReaction(patientid, medicationid);
			
			file.print("reaction of patient with username "+patientid+" and medication id "+medicationid+" deleted... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("reactionDeleted");//deleted patient
			outToClient.flush();
		}	
		void addComment() throws IOException{
			String patientid = inFromClient.readLine();
			String staffid = inFromClient.readLine();
			String subject = inFromClient.readLine();
			String comment = inFromClient.readLine();
			jdbc.addComment(patientid, staffid, subject, comment);
			file.print("comment for patient with username "+patientid+" added...");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("commentAdded");//add patient
			outToClient.flush();
		}
		void updateComment() throws IOException, SQLException{
			int id = Integer.parseInt(inFromClient.readLine());
			String patientid = inFromClient.readLine();
			String staffid = inFromClient.readLine();
			String subject = inFromClient.readLine();
			String comm = inFromClient.readLine();
			jdbc.updateComment(id,patientid, staffid, subject, comm);
			file.print("comment for patient with username "+patientid+" updated ... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("commentUpdated");//updated patient
			outToClient.flush();
			ResultSet rs=jdbc.printComment(id);
			List<Comment> ls = comment.convertRsToList(rs);
			outObject.writeObject(ls);	
			}
		void deleteComment()throws IOException, SQLException{
			int id = Integer.parseInt(inFromClient.readLine());
			jdbc.deleteComment(id);
			file.print("comment "+id+" deleted... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("commentDeleted");//deleted patient
			outToClient.flush();
		}	
		void searchComment() throws IOException, SQLException{
			int id = Integer.parseInt(inFromClient.readLine());			
			ResultSet rs=jdbc.printComment(id);
			file.print("Comment with id "+id+" searched... ");
			file.println(dtf.format(now));
			file.flush();
			outToClient.println("commentSearched");//searched medication
			outToClient.flush();
			List<Comment> ls = comment.convertRsToList(rs);
			outObject.writeObject(ls);
			System.out.println("search after");
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
				patient=new Patient();
				relative=new Relative();
				incident=new Incident();
				treatment=new Treatment();
				medication=new Medication();
				consultation=new Consultation();
				reaction=new MedicationReaction();
				comment = new Comment();
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
					if (messageFromClient.equals("incidentForm"))
						addincident();
					if (messageFromClient.equals("updateIncident"))
						updateIncident();
					if (messageFromClient.equals("deleteIncident"))
						deleteIncident();
					if (messageFromClient.equals("searchIncident"))
						searchIncident();
					if (messageFromClient.equals("addTreatment"))
						addTreatment();
					if (messageFromClient.equals("updateTreatment"))
						updateTreatment();
					if (messageFromClient.equals("deleteTreatment"))
						deleteTreatment();
					if (messageFromClient.equals("searchTreatment"))
						searchTreatment();
					if (messageFromClient.equals("addMedication"))
						addMedication();
					if (messageFromClient.equals("updateMedication"))
						updateMedication();
					if (messageFromClient.equals("deleteMedication"))
						deleteMedication();
					if (messageFromClient.equals("searchMedication"))
						searchMedication();
					if (messageFromClient.equals("addConsultation"))
						addConsultation();
					if (messageFromClient.equals("updateConsultation"))
						updateConsultation();
					if (messageFromClient.equals("deleteConsultation"))
						deleteConsultation();
					if (messageFromClient.equals("searchConsultation"))
						searchConsultation();
					if (messageFromClient.equals("addReaction"))
						addReaction();
					if (messageFromClient.equals("searchReaction"))
						searchReaction();
					if (messageFromClient.equals("updateReaction"))
						updateReaction();
					if (messageFromClient.equals("deleteReaction"))
						deleteReaction();
					if (messageFromClient.equals("addComment"))
						addComment();
					if (messageFromClient.equals("updateComment"))
						updateComment();
					if (messageFromClient.equals("deleteComment"))
						deleteComment();
					if (messageFromClient.equals("searchComment"))
						searchComment();
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
