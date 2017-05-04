package server;

import java.net.ServerSocket;
import java.net.Socket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PsychiatryServer {
	private static JDBC jdbc = new JDBC();
	BufferedReader inFromClient;
	PrintWriter outToClient,file;
	Socket socket;
	String messageFromClient;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();	

	void login() throws Exception {
		System.out.println("mpika sto login");
		String username = inFromClient.readLine();
		System.out.println("diavasa username "+username);
		String password = inFromClient.readLine();
		System.out.println("diavasa password "+password);
		String role = inFromClient.readLine();
		System.out.println("diavasa role "+role);
		try {
			ResultSet rs = jdbc.login(username, password, role);
			if (rs.next()) {
				String roleGUI = rs.getString("StaffType");
				if (roleGUI.equals("Doctor") || roleGUI.equals("Nurse") || roleGUI.equals("Health Visitor")){
					file.print(username+" log in as Staff... ");
					  file.println(dtf.format(now));
					  file.flush();
					outToClient.println("1");	
					outToClient.flush();
					}
				else{
					file.print(username+" log in as Receptionist... ");
					 file.println(dtf.format(now));
					 file.flush();
					outToClient.println("2");
					outToClient.flush();
				}
			} else {
				file.print(username+" try to log in but failed... ");
				 file.println(dtf.format(now));
				 file.flush();
				outToClient.println("3");
				outToClient.flush();
			}
			
		} catch (Exception er) {
			// Ignore the error and continues
		}
	}

	void signup() throws Exception {
		String username = inFromClient.readLine();
		String password = inFromClient.readLine();
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		String role = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		file.print(name+" "+surname+" sign up with username "+username+"... ");
		file.println(dtf.format(now));
		file.flush();
		
		try {
			ResultSet rs = jdbc.checksignup(username);
			if (!rs.next()) {
				jdbc.signup(username, password, name, surname, role, phone, email, address);
				outToClient.println("1");
			} else {
				outToClient.println("2");
			}
			outToClient.flush();
		} catch (Exception er) {
			// Ignore the error and continues
		}
	}
	void logout(){
		try {
			String username = inFromClient.readLine();
			file = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
			file.print(username+" log out... ");
			  file.println(dtf.format(now));
			  file.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	void changePassword() throws IOException{
		String username = inFromClient.readLine();
		String oldpassword = inFromClient.readLine();
		String newpassword = inFromClient.readLine();
		String role = inFromClient.readLine();
		file.print(username+" changed password... ");
		file.println(dtf.format(now));
		file.flush();
		System.out.println(oldpassword+" "+username + " "+role);
	
		try {
		ResultSet rs = jdbc.login(username,oldpassword,role);
		System.out.println("ekana login");
		
			if (rs.next()) {
				jdbc.changepassword(username,oldpassword,newpassword,role);
				System.out.println("allaksa ton kwdiko");
				outToClient.println("1");//password Changed
				outToClient.flush();
			}
			else{
				outToClient.println("2");//wrong password
				outToClient.flush();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
