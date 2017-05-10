/**
 * This class implements the server that communicates with the client (GUI)
 * and with the database through the JDBC class.
 * 
 * @author Sotia Gregoriou, Elena Matsi, Erasmia Shimitra
 */

package server;

// Libraries
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import entities.Staff;
import entities.AttendanceReport;
import entities.Comment;
import entities.ConditionReport;
import entities.Consultation;
import entities.ConsultationReport;
import entities.Incident;
import entities.Medication;
import entities.Patient;
import entities.Relative;
import entities.Treatment;
import entities.WarningLetter;
import entities.MedicationReaction;
import entities.MedicationReport;
import entities.MedicationPrescription;

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
	Staff staff;
	ConsultationReport consultationReport;
	AttendanceReport attendanceReport;
	ConditionReport conditionReport;
	MedicationReport medicationReport;
	MedicationPrescription medicationPrescription;
	WarningLetter letter;

	/**
	 * This function gets username, password and role from client, checks if
	 * user exists, sends message according to role and writes in log file the
	 * action and the date/time.
	 * 
	 * @return void
	 */
	void login() throws Exception {
		// Get data from client
		System.out.println("mpika sto login");
		String username = inFromClient.readLine();
		System.out.println("diavasa username " + username);
		String password = inFromClient.readLine();
		System.out.println("diavasa password " + password);
		String role = inFromClient.readLine();
		System.out.println("diavasa role " + role);
		try {
			// Communicate with database
			ResultSet rs = jdbc.login(username, password, role);
			// Successful log in
			if (rs.next()) {
				// User is patient
				if (role.equals("Patient")) {
					// Write to log file
					file.print(username + " log in as Patient... ");
					file.println(dtf.format(now));
					file.flush();
					// Send data to client
					outToClient.println("Patient");
					outToClient.flush();
				} else { // User is staff
					String roleGUI = rs.getString("StaffType");
					// User is doctor
					if (roleGUI.equals("Doctor")) {
						// Write to log file
						file.print(username + " log in as Doctor... ");
						file.println(dtf.format(now));
						file.flush();
						// Send data to client
						outToClient.println("Doctor");
						outToClient.flush();
						// User is nurse or health visitor
					} else if (roleGUI.equals("Nurse") || roleGUI.equals("Health Visitor")) {
						// Write to log file
						file.print(username + " log in as " + roleGUI + "... ");
						file.println(dtf.format(now));
						file.flush();
						// Send data to client
						outToClient.println("Nurse-HealthVisitor");
						outToClient.flush();
						// User is receptionist
					} else if (roleGUI.equals("Receptionist")) {
						// Write to log file
						file.print(username + " log in as Receptionist... ");
						file.println(dtf.format(now));
						file.flush();
						// Send data to client
						outToClient.println("Receptionist");
						outToClient.flush();
						// User is medical records
					} else if (roleGUI.equals("Medical Records")) {
						// Write to log file
						file.print(username + " log in as Medical Records... ");
						file.println(dtf.format(now));
						file.flush();
						// Send data to client
						outToClient.println("MedicalRecords");
						outToClient.flush();
						// User is management
					} else if (roleGUI.equals("Management")) {
						// Write to log file
						file.print(username + " log in as Management... ");
						file.println(dtf.format(now));
						file.flush();
						// Send data to client
						outToClient.println("Management");
						outToClient.flush();
					}
				}
				// Unsuccessful log in
			} else {
				// Write to log file
				file.print(username + " try to log in but failed... ");
				file.println(dtf.format(now));
				file.flush();
				// Send data to client
				outToClient.println("wrong");
				outToClient.flush();
			}

		} catch (Exception er) {
			System.out.println("Exception: login");
		}
	}

	/**
	 * This function gets some fields from client, add the new user with those
	 * fields in database, sends successful or not message and writes in log
	 * file the action and the date/time.
	 * 
	 * @return void
	 */
	void signup() throws Exception {
		// Get data from client
		String username = inFromClient.readLine();
		String password = inFromClient.readLine();
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		String role = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		// Write to log file
		file.print(name + " " + surname + " sign up with username " + username + "... ");
		file.println(dtf.format(now));
		file.flush();
		try {
			// Communicate with database
			ResultSet rs = jdbc.checksignup(username);
			// Successful sign up
			if (!rs.next()) {
				// Communicate with database
				jdbc.signup(username, password, name, surname, role, phone, email, address);
				// Send data to client
				outToClient.println("success");
				// Unsuccessful sign up
			} else {
				// Send data to client
				outToClient.println("alreadyExists");
			}
			outToClient.flush();
		} catch (Exception er) {
			System.out.println("Exception: signup");
		}
	}

	/**
	 * When client log outs, server writes in log file that a user with
	 * "username" have just log out at this time.
	 * 
	 * @return void
	 */
	void logout() {
		try {
			// Get data from client
			String username = inFromClient.readLine();
			// Write to log file
			file.print(username + " log out... ");
			file.println(dtf.format(now));
			file.flush();
		} catch (IOException e) {
			System.out.println("Exception: logout");
			e.printStackTrace();
		}
	}

	/**
	 * This function gets username, old password and new password from client,
	 * checks if username exists, changes old password with new password and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void changePassword() throws IOException {
		// Get data from client
		String username = inFromClient.readLine();
		String oldpassword = inFromClient.readLine();
		String newpassword = inFromClient.readLine();
		String role = inFromClient.readLine();
		// Write to log file
		file.print(username + " changed password... ");
		file.println(dtf.format(now));
		file.flush();
		System.out.println(oldpassword + " " + username + " " + role);
		try {
			// Communicate with database
			ResultSet rs = jdbc.login(username, oldpassword, role);
			System.out.println("ekana login");
			// Successful update
			if (rs.next()) {
				// Communicate with database
				jdbc.changepassword(username, oldpassword, newpassword, role);
				System.out.println("allaksa ton kwdiko");
				// Send data to client
				outToClient.println("passwordChanged");
				outToClient.flush();
				// Unsuccessful update
			} else {
				// Send data to client
				outToClient.println("wrongPassword");
				outToClient.flush();
			}
		} catch (SQLException e) {
			System.out.println("Exception: changePassword");
			e.printStackTrace();
		}
	}

	/**
	 * This function gets some fields from client, updates the profile and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updateProfile() throws IOException, SQLException {
		// Get data from client
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		String username = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		String role = inFromClient.readLine();
		System.out.println("update before");
		// Communicate with database
		jdbc.updateProfile(name, surname, username, phone, email, address, role);
		System.out.println("update after");
		// Write to log file
		file.print("user with name " + name + " " + surname + " updated profile... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("profileUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printProfile(username, role);
		if (role.equals("Patient")) {
			// Send data to client
			List<Patient> ls = patient.convertRsToList(rs);
			outObject.writeObject(ls);
		} else {
			// Send data to client
			List<Staff> ls = staff.convertRsToList(rs);
			outObject.writeObject(ls);
		}
	}

	/**
	 * This function gets some fields from client and prints the profile.
	 * 
	 * @return void
	 */
	void printProfile() throws IOException, SQLException {
		// Get data from client
		String username = inFromClient.readLine();
		String role = inFromClient.readLine();
		// Communicate with database
		ResultSet rs = jdbc.printProfile(username, role);
		if (role.equals("Patient")) {
			// Send data to client
			List<Patient> ls = patient.convertRsToList(rs);
			outObject.writeObject(ls);
		} else {
			// Send data to client
			List<Staff> ls = staff.convertRsToList(rs);
			outObject.writeObject(ls);
		}
	}

	/**
	 * This function gets some fields from client, add the patient and writes in
	 * log file the action and the date/time.
	 * 
	 * @return void
	 */
	void addPatient() throws IOException {
		// Get data from client
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		String username = inFromClient.readLine();
		String password = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		// Communicate with database
		jdbc.addPatient(name, surname, username, password, phone, email, address);
		// Write to log file
		file.print("patient " + name + " " + surname + " added with username " + username + "... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("patientAdded");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, updates the patient and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updatePatient() throws IOException, SQLException {
		// Get data from client
		String username = inFromClient.readLine();
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		System.out.println("update before");
		// Communicate with database
		jdbc.updatePatient(username, name, surname, phone, email, address);
		System.out.println("update after");
		// Write to log file
		file.print("patient with name " + name + " " + surname + " updated... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("patientUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printPatient(username);
		// Send data to client
		List<Patient> ls = patient.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	/**
	 * This function gets some fields from client, updates the harm risk record
	 * and writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updateHarmRisk() throws IOException, SQLException {
		// Get data from client
		String username = inFromClient.readLine();
		int self = Integer.parseInt(inFromClient.readLine());
		int others = Integer.parseInt(inFromClient.readLine());
		String status = inFromClient.readLine();
		int dead = Integer.parseInt(inFromClient.readLine());
		System.out.println("update before");
		// Communicate with database
		jdbc.updateHarmRisk(username, self, others, status, dead);
		System.out.println("update after");
		// Write to log file
		file.print("patient's harm risk record with username " + username + " updated... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("harmRiskUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printPatient(username);
		// Send data to client
		List<Patient> ls = patient.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	/**
	 * This function gets some fields from client, searches the patient and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchPatient() throws IOException, SQLException {
		// Get data from client
		String username = inFromClient.readLine();
		if (username.equals("null")) {
			username = null;
			// Write to log file
			file.print("all patients printed... ");
			file.println(dtf.format(now));
			file.flush();
		} else {
			// Write to log file
			file.print("patient with username " + username + " searched... ");
			file.println(dtf.format(now));
			file.flush();
		}
		// Communicate with database
		ResultSet rs = jdbc.printPatient(username);
		// Send data to client
		outToClient.println("patientSearched");
		outToClient.flush();
		// Send data to client
		List<Patient> ls = patient.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("search after");
	}

	/**
	 * This function gets some fields from client, searches the harm risk record
	 * and writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchHarmRisk() throws IOException, SQLException {
		// Get data from client
		String username = inFromClient.readLine();
		// Communicate with database
		ResultSet rs = jdbc.printPatient(username);
		// Write to log file
		file.print("patient with username " + username + " searched... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("harmRiskSearched");
		outToClient.flush();
		// Send data to client
		List<Patient> ls = patient.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("search after");
	}

	/**
	 * This function gets some fields from client, deletes the patient and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void deletePatient() throws IOException, SQLException {
		// Get data from client
		String username = inFromClient.readLine();
		// Communicate with database
		jdbc.deletePatient(username);
		// Write to log file
		file.print("patient with username " + username + " deleted... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("patientDeleted");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, adds the relative and writes
	 * in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void addRelative() throws IOException {
		// Get data from client
		String patientusername = inFromClient.readLine();
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		String relationship = inFromClient.readLine();
		// Communicate with database
		jdbc.addRelative(patientusername, name, surname, phone, email, address, relationship);
		// Write to log file
		file.print(relationship + " of " + patientusername + " added... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("success");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, updates the relative and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updateRelative() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		System.out.println("id: " + id);
		String patientid = inFromClient.readLine();
		String name = inFromClient.readLine();
		String surname = inFromClient.readLine();
		int phone = Integer.parseInt(inFromClient.readLine());
		String email = inFromClient.readLine();
		String address = inFromClient.readLine();
		String relationship = inFromClient.readLine();
		// Communicate with database
		jdbc.updateRelative(id, patientid, name, surname, phone, email, address, relationship);
		// Write to log file
		file.print(relationship + " of " + patientid + " updated... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("relativeUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printRelative(id);
		// Send data to client
		List<Relative> ls = relative.convertRsToRelatList(rs);
		outObject.writeObject(ls);
		System.out.println("esteila kai to resultset");
	}

	/**
	 * This function gets some fields from client, searches the relative and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchRelative() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		if (id == -1) {
			// Write to log file
			file.print("all relatives printed... ");
			file.println(dtf.format(now));
			file.flush();
		} else {
			// Write to log file
			file.print("relative with id " + id + " searched... ");
			file.println(dtf.format(now));
			file.flush();
		}
		// Communicate with database
		ResultSet rs = jdbc.printRelative(id);
		// Send data to client
		outToClient.println("relativeSearched");
		outToClient.flush();
		// Send data to client
		List<Relative> ls = relative.convertRsToRelatList(rs);
		outObject.writeObject(ls);
		System.out.println("relative search after");
	}

	/**
	 * This function gets some fields from client, deletes the relative and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void deleteRelative() throws IOException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.deleteRelative(id);
		// Write to log file
		file.print("relative with id " + id + " deleted... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("relativeDeleted");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, informs the relatives and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void informRelatives() throws IOException {
		// Get data from client
		String patientid = inFromClient.readLine();
		String staffid = inFromClient.readLine();
		String subject = inFromClient.readLine();
		String message = inFromClient.readLine();
		// Communicate with database
		jdbc.informRelatives(patientid, staffid, subject, message);
		// Write to log file
		file.print("relatives of patient with username " + patientid + " informed...");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("relativesInformed");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, adds the incident and writes
	 * in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void addincident() throws IOException {
		// Get data from client
		String patientid = inFromClient.readLine();
		String type = inFromClient.readLine();
		String shortDescription = inFromClient.readLine();
		String description = inFromClient.readLine();
		String date = inFromClient.readLine();
		// Communicate with database
		jdbc.addIncident(patientid, type, shortDescription, description, date);
		// Write to log file
		file.print("Incident for " + patientid + " added... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("success");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, updates the incident and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updateIncident() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		String patientid = inFromClient.readLine();
		String type = inFromClient.readLine();
		String shortDescription = inFromClient.readLine();
		String description = inFromClient.readLine();
		String date = inFromClient.readLine();
		// Communicate with database
		jdbc.updateIncident(id, patientid, type, shortDescription, description, date);
		// Write to log file
		file.print(patientid + "incident's " + id + " updated... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("incidentUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printIncident(id);
		// Send data to client
		List<Incident> ls = incident.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("esteila kai to resultset");
	}

	/**
	 * This function gets some fields from client, deletes the incident and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void deleteIncident() throws IOException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.deleteIncident(id);
		// Write to log file
		file.print("incident with id " + id + " deleted... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("incidentDeleted");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, searches the incident and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchIncident() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		if (id == -1) {
			// Write to log file
			file.print("all incident printed... ");
			file.println(dtf.format(now));
			file.flush();
		} else {
			// Write to log file
			file.print("incident with id " + id + " searched... ");
			file.println(dtf.format(now));
			file.flush();
		}
		// Communicate with database
		ResultSet rs = jdbc.printIncident(id);
		// Send data to client
		outToClient.println("incidentSearched");
		outToClient.flush();
		// Send data to client
		List<Incident> ls = incident.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("incident search after");
	}

	/**
	 * This function gets some fields from client, adds the treatment and writes
	 * in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void addTreatment() throws IOException, SQLException {
		// Get data from client
		String patientid = inFromClient.readLine();
		String startDate = inFromClient.readLine();
		String endDate = inFromClient.readLine();
		String diagnosis = inFromClient.readLine();
		String description = inFromClient.readLine();
		String staffid = inFromClient.readLine();
		// Communicate with database
		jdbc.addTreatment(patientid, startDate, endDate, diagnosis, description, staffid);
		// Write to log file
		file.print("Treatment for " + patientid + " added... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("treatmentAdded");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.findTreatment(patientid, startDate, endDate, diagnosis, description, staffid);
		// Send data to client
		List<Treatment> ls = treatment.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("esteila kai to resultset");
	}

	/**
	 * This function gets some fields from client, adds medication to the
	 * treatment and writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void addTreatmentMedication() throws IOException, SQLException {
		// Get data from client
		int treatmentid = Integer.parseInt(inFromClient.readLine());
		int medicationid = Integer.parseInt(inFromClient.readLine());
		int dose = Integer.parseInt(inFromClient.readLine());
		String description = inFromClient.readLine();
		// Communicate with database
		jdbc.addTreatmentMedication(treatmentid, medicationid, dose, description);
		file.print("Medication for treatment with id " + treatmentid + " added... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("treatmentMedicationAdded");
		outToClient.flush();
		outToClient.println(jdbc.checkForWarning(treatmentid, medicationid));
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, updates the treatment and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updateTreatment() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		String patientid = inFromClient.readLine();
		String startDate = inFromClient.readLine();
		String endDate = inFromClient.readLine();
		String diagnosis = inFromClient.readLine();
		String description = inFromClient.readLine();
		String staffid = inFromClient.readLine();
		// Communicate with database
		jdbc.updateTreatment(id, patientid, startDate, endDate, diagnosis, description, staffid);
		// Write to log file
		file.print(patientid + "treatment's " + id + " updated... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("treatmentUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printTreatment(id);
		// Send data to client
		List<Treatment> ls = treatment.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("esteila kai to resultset");
	}

	/**
	 * This function gets some fields from client, deletes the treatment and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void deleteTreatment() throws IOException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.deleteTreatment(id);
		// Write to log file
		file.print("treatment with id " + id + " deleted... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("treatmentDeleted");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, deletes medication of
	 * treatment and writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void deleteTreatmentMedication() throws IOException {
		// Get data from client
		int treatmentid = Integer.parseInt(inFromClient.readLine());
		int medicationid = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.deleteTreatmentMedication(treatmentid, medicationid);
		// Write to log file
		file.print("treatment with id " + treatmentid + " and medication id " + medicationid + " record deleted... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("treatmentMedicationDeleted");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, renews the treatment and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void renewTreatment() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		String patientid = inFromClient.readLine();
		String startDate = inFromClient.readLine();
		String endDate = inFromClient.readLine();
		String notes = inFromClient.readLine();
		String staffid = inFromClient.readLine();
		// Communicate with database
		jdbc.renewTreatment(id, patientid, startDate, endDate, notes, staffid);
		// Write to log file
		file.print(patientid + "treatment's " + id + " renewed... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("treatmentRenewed");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printTreatment(id);
		// Send data to client
		List<Treatment> ls = treatment.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("esteila kai to resultset");
	}

	/**
	 * This function gets some fields from client, overrules the warning and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void overruleWarning() throws IOException, SQLException {
		// Get data from client
		int treatmentid = Integer.parseInt(inFromClient.readLine());
		int medicationid = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.overruleWarning(treatmentid, medicationid);
		// Write to log file
		file.print("warning overruled for treatment with id " + treatmentid + " and medication id " + medicationid
				+ " updated ... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("overruledWarning");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, searches the treatment and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchTreatment() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		if(id==-1){
			// Write to log file
			file.print("all treatments printed... ");
			file.println(dtf.format(now));
			file.flush();
		}else{
			// Write to log file
			file.print("treatment with id " + id + " searched... ");
			file.println(dtf.format(now));
			file.flush();
		}
		// Communicate with database
		ResultSet rs = jdbc.printTreatment(id);
		// Send data to client
		outToClient.println("treatmentSearched");
		outToClient.flush();
		// Send data to client
		List<Treatment> ls = treatment.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("incident search after");
	}

	/**
	 * This function gets some fields from client, searches the previous treatment and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchPreviousTreatment() throws IOException, SQLException {
		// Get data from client
		String id = inFromClient.readLine();
		// Communicate with database
		ResultSet rs = jdbc.findPreviousTreatment(id);
		// Write to log file
		file.print("treatment with patient's username " + id + " searched... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("previousTreatmentSearched");
		outToClient.flush();
		// Send data to client
		List<Treatment> ls = treatment.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("previous treatment search after");
	}

	/**
	 * This function gets some fields from client, searches the renewed treatment and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchRenewTreatment() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		ResultSet rs = jdbc.printTreatment(id);
		// Write to log file
		file.print("treatment with id " + id + " searched... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("renewTreatmentSearched");
		outToClient.flush();
		// Send data to client
		List<Treatment> ls = treatment.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("incident search after");
	}

	/**
	 * This function gets some fields from client, adds the medication and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void addMedication() throws IOException {
		// Get data from client
		String brand = inFromClient.readLine();
		String name = inFromClient.readLine();
		String description = inFromClient.readLine();
		String effects = inFromClient.readLine();
		int dose = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.addMedication(brand, name, description, effects, dose);
		// Write to log file
		file.print("Medication with name " + name + " added... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("medicationAdded");
	}

	/**
	 * This function gets some fields from client, updates the medication and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updateMedication() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		String brand = inFromClient.readLine();
		String name = inFromClient.readLine();
		String description = inFromClient.readLine();
		String effects = inFromClient.readLine();
		int dose = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.updateMedication(id, brand, name, description, effects, dose);
		// Write to log file
		file.print("Medication " + id + " updated... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("medicationUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printTreatment(id);
		// Send data to client
		List<Medication> ls = medication.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("esteila kai to resultset");
	}

	/**
	 * This function gets some fields from client, deletes the medication and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void deleteMedication() throws IOException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.deleteMedication(id);
		// Write to log file
		file.print("medication with id " + id + " deleted... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("medicationDeleted");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, searches the medication and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchMedication() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		if(id==-1){
			// Write to log file
			file.print("all medications printed... ");
			file.println(dtf.format(now));
			file.flush();
		}else{
			// Write to log file
			file.print("Medication with id " + id + " searched... ");
			file.println(dtf.format(now));
			file.flush();
		}
		// Communicate with database
		ResultSet rs = jdbc.printMedication(id);
		// Send data to client
		outToClient.println("medicationSearched");
		outToClient.flush();
		// Send data to client
		List<Medication> ls = medication.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("search after");
	}

	/**
	 * This function gets some fields from client, adds the consultation and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void addConsultation() throws IOException {
		// Get data from client
		String patientid = inFromClient.readLine();
		String staffid = inFromClient.readLine();
		String subject = inFromClient.readLine();
		String dateBooked = inFromClient.readLine();
		String date = inFromClient.readLine();
		String time = inFromClient.readLine();
		int treatmentid = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.addConsultation(patientid, staffid, subject, dateBooked, date, time, treatmentid);
		// Write to log file
		file.print("Consultation for patient with username " + patientid + " with" + staffid + " added... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("consultationAdded");
	}

	/**
	 * This function gets some fields from client, updates the consultation and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updateConsultation() throws IOException, SQLException {
		// Get data from client
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
		// Communicate with database
		jdbc.updateConsultation(id, patientid, staffid, subject, dateBooked, date, time, attended, updated,
				treatmentid);
		// Write to log file
		file.print("Consultation " + id + " updated... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("consultationUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printConsultation(id);
		// Send data to client
		List<Consultation> ls = consultation.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("esteila kai to resultset");
	}

	/**
	 * This function gets some fields from client, deletes the consultation and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void deleteConsultation() throws IOException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.deleteConsultation(id);
		// Write to log file
		file.print("Consultation with id " + id + " deleted... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("consultationDeleted");
		outToClient.flush();

	}

	/**
	 * This function gets some fields from client, searches the consultation and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchConsultation() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		if(id==-1){
			// Write to log file
			file.print("All consultation printed... ");
			file.println(dtf.format(now));
			file.flush();
		}else{
			// Write to log file 
			file.print("Consultation with id " + id + " searched... ");
			file.println(dtf.format(now));
			file.flush();
		}
		// Communicate with database
		ResultSet rs = jdbc.printConsultation(id);
		// Send data to client
		outToClient.println("consultationSearched");
		outToClient.flush();
		// Send data to client
		List<Consultation> ls = consultation.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("search after");

	}

	/**
	 * This function gets some fields from client, adds the medication reaction
	 * and writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void addReaction() throws IOException {
		// Get data from client
		String patientid = inFromClient.readLine();
		int medicationid = Integer.parseInt(inFromClient.readLine());
		String reactionType = inFromClient.readLine();
		String description = inFromClient.readLine();
		// Communicate with database
		jdbc.addMedicationReaction(patientid, medicationid, reactionType, description);
		// Write to log file
		file.print("reaction of patient with username " + patientid + " added with medication id " + medicationid
				+ "... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("reactionAdded");
		outToClient.flush();
		System.out.println("telos");
	}

	/**
	 * This function gets some fields from client, updates the medication
	 * reaction and writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updateReaction() throws IOException, SQLException {
		// Get data from client
		String patientid = inFromClient.readLine();
		int medicationid = Integer.parseInt(inFromClient.readLine());
		String reactionType = inFromClient.readLine();
		String description = inFromClient.readLine();
		System.out.println("update before");
		// Communicate with database
		jdbc.updateMedicationReaction(patientid, medicationid, reactionType, description);
		System.out.println("update after");
		// Write to log file
		file.print("reaction of patient with username " + patientid + " and medication id " + medicationid
				+ " updated ... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("reactionUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printMedicationReaction(patientid, medicationid);
		// Send data to client
		List<MedicationReaction> ls = reaction.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	/**
	 * This function gets some fields from client, searches the medication reaction and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchReaction() throws IOException, SQLException {
		// Get data from client
		String patientid = inFromClient.readLine();
		int medicationid = Integer.parseInt(inFromClient.readLine());
		if(medicationid==-1){
			// Write to log file
			file.print("all medication reactions printed... ");
			file.println(dtf.format(now));
			file.flush();
		}else{
			// Write to log file
			file.print("reaction of patient with username " + patientid + " and medication id " + medicationid
					+ " searched... ");
			file.println(dtf.format(now));
			file.flush();
		}
		// Communicate with database
		ResultSet rs = jdbc.printMedicationReaction(patientid, medicationid);
		// Send data to client
		outToClient.println("reactionSearched");
		outToClient.flush();
		// Send data to client
		List<MedicationReaction> ls = reaction.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("search after");
	}

	/**
	 * This function gets some fields from client, deletes the medication
	 * reaction and writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void deleteReaction() throws IOException, SQLException {
		// Get data from client
		String patientid = inFromClient.readLine();
		int medicationid = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.deleteMedicationReaction(patientid, medicationid);
		// Write to log file
		file.print("reaction of patient with username " + patientid + " and medication id " + medicationid
				+ " deleted... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("reactionDeleted");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, adds the comment and writes
	 * in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void addComment() throws IOException {
		// Get data from client
		String patientid = inFromClient.readLine();
		String staffid = inFromClient.readLine();
		String subject = inFromClient.readLine();
		String comment = inFromClient.readLine();
		// Communicate with database
		jdbc.addComment(patientid, staffid, subject, comment);
		// Write to log file
		file.print("comment for patient with username " + patientid + " added...");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("commentAdded");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, updates the comment and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void updateComment() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		String patientid = inFromClient.readLine();
		String staffid = inFromClient.readLine();
		String subject = inFromClient.readLine();
		String comm = inFromClient.readLine();
		// Communicate with database
		jdbc.updateComment(id, patientid, staffid, subject, comm);
		// Write to log file
		file.print("comment for patient with username " + patientid + " updated ... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("commentUpdated");
		outToClient.flush();
		// Communicate with database
		ResultSet rs = jdbc.printComment(id);
		// Send data to client
		List<Comment> ls = comment.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	/**
	 * This function gets some fields from client, deletes the comment and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void deleteComment() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		// Communicate with database
		jdbc.deleteComment(id);
		// Write to log file
		file.print("comment " + id + " deleted... ");
		file.println(dtf.format(now));
		file.flush();
		// Send data to client
		outToClient.println("commentDeleted");
		outToClient.flush();
	}

	/**
	 * This function gets some fields from client, searches the comment and
	 * writes in log file the action and the date/time.
	 * 
	 * @return void
	 */
	void searchComment() throws IOException, SQLException {
		// Get data from client
		int id = Integer.parseInt(inFromClient.readLine());
		if(id==-1){
			// Write to log file
			file.print("All comments printed... ");
			file.println(dtf.format(now));
			file.flush();
		}else{
			// Write to log file
			file.print("Comment with id " + id + " searched... ");
			file.println(dtf.format(now));
			file.flush();
		}
		// Communicate with database
		ResultSet rs = jdbc.printComment(id);
		// Send data to client
		outToClient.println("commentSearched");
		outToClient.flush();
		// Send data to client
		List<Comment> ls = comment.convertRsToList(rs);
		outObject.writeObject(ls);
		System.out.println("search after");
	}

	void consultationReport() throws IOException, SQLException {
		boolean attendbool;
		int attend = Integer.parseInt(inFromClient.readLine());
		if (attend == 1)
			attendbool = true;
		else
			attendbool = false;
		String date = inFromClient.readLine();
		if (date.equals("null"))
			date = null;
		ResultSet rs = jdbc.getConsultationReport(attendbool, date);
		file.print("Consultation report printed... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("report");
		outToClient.flush();
		List<ConsultationReport> ls = consultationReport.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	void consultationReportMedical() throws IOException, SQLException {
		String date = inFromClient.readLine();
		if (date.equals("null"))
			date = null;
		ResultSet rs = jdbc.getUpdatedMedicalRecordsReport(date);
		file.print("Consultation report for Medical Records printed... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("medicalReport");
		outToClient.flush();
		List<ConsultationReport> ls = consultationReport.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	void patientReport() throws IOException, SQLException {
		int option = Integer.parseInt(inFromClient.readLine());
		String value = inFromClient.readLine();
		ResultSet rs = jdbc.getPatientReport(option, value);
		file.print("Patient report printed... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("patientReport");
		outToClient.flush();
		List<Patient> ls = patient.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	void AttendanceReport() throws IOException, SQLException {
		ResultSet rs = jdbc.getAttendanceReport();
		file.print("Attendance report printed... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("attendanceReport");
		outToClient.flush();
		List<AttendanceReport> ls = attendanceReport.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	void conditionReport() throws IOException, SQLException {
		ResultSet rs = jdbc.getConditionReport();
		file.print("Condition report printed... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("conditionReport");
		outToClient.flush();
		List<ConditionReport> ls = conditionReport.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	void medicationReport() throws IOException, SQLException {
		ResultSet rs = jdbc.getMedicationReport();
		file.print("Medication report printed... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("medicationReport");
		outToClient.flush();
		List<MedicationReport> ls = medicationReport.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	void medicationPrescription() throws IOException, SQLException {
		ResultSet rs = jdbc.getMedicationPrescriptionsSummary();
		file.print("medication Prescription report printed... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("medicationPrescription");
		outToClient.flush();
		List<MedicationPrescription> ls = medicationPrescription.convertRsToList(rs);
		outObject.writeObject(ls);
	}

	void warningLetters() throws IOException, SQLException {
		String id = inFromClient.readLine();
		if (id.equals("null"))
			id = null;
		ResultSet rs = jdbc.viewWarningLetters(id);
		file.print("Warning Letters printed... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("warningLetters");
		outToClient.flush();
		List<WarningLetter> ls = letter.convertRsToList(rs);
		outObject.writeObject(ls);
	}
	void todaysAppointments() throws IOException, SQLException{
		String date = inFromClient.readLine();
		if (date.equals("null"))
			date = null;
		ResultSet rs = jdbc.viewTodaysAppointments(date);
		file.print("Today appontments printed... ");
		file.println(dtf.format(now));
		file.flush();
		outToClient.println("todaysAppointments");
		outToClient.flush();
		List<Consultation> ls = consultation.convertRsToList(rs);
		outObject.writeObject(ls);
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
				file = new PrintWriter(new BufferedWriter(new FileWriter("log\\log.txt", true)));
				patient = new Patient();
				relative = new Relative();
				incident = new Incident();
				treatment = new Treatment();
				medication = new Medication();
				consultation = new Consultation();
				reaction = new MedicationReaction();
				comment = new Comment();
				staff = new Staff();
				consultationReport=new ConsultationReport();
				attendanceReport=new AttendanceReport();
				conditionReport = new ConditionReport();
				medicationReport = new MedicationReport();
				medicationPrescription = new MedicationPrescription();
				consultationReport = new ConsultationReport();
				letter = new WarningLetter();
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
					if (messageFromClient.equals("printProfile"))
						printProfile();
					if (messageFromClient.equals("updateProfile"))
						updateProfile();
					if (messageFromClient.equals("addPatient"))
						addPatient();
					if (messageFromClient.equals("searchPatient"))
						searchPatient();
					if (messageFromClient.equals("updatePatient"))
						updatePatient();
					if (messageFromClient.equals("searchHarmRisk"))
						searchHarmRisk();
					if (messageFromClient.equals("updateHarmRisk"))
						updateHarmRisk();
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
					if (messageFromClient.equals("informRelatives"))
						informRelatives();
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
					if (messageFromClient.equals("addTreatmentMedication"))
						addTreatmentMedication();
					if (messageFromClient.equals("updateTreatment"))
						updateTreatment();
					if (messageFromClient.equals("renewTreatment"))
						renewTreatment();
					if (messageFromClient.equals("deleteTreatment"))
						deleteTreatment();
					if (messageFromClient.equals("deleteTreatmentMedication"))
						deleteTreatmentMedication();
					if (messageFromClient.equals("overruleWarning"))
						overruleWarning();
					if (messageFromClient.equals("searchTreatment"))
						searchTreatment();
					if (messageFromClient.equals("searchPreviousTreatment"))
						searchPreviousTreatment();
					if (messageFromClient.equals("searchRenewTreatment"))
						searchRenewTreatment();
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
					if (messageFromClient.equals("consultationReport"))
						consultationReport();
					if (messageFromClient.equals("consultationReportMedical"))
						consultationReportMedical();
					if (messageFromClient.equals("patientReport"))
						patientReport();
					if (messageFromClient.equals("attendanceReport"))
						AttendanceReport();
					if (messageFromClient.equals("conditionReport"))
						conditionReport();
					if (messageFromClient.equals("medicationReport"))
						medicationReport();
					if (messageFromClient.equals("medicationPrescription"))
						medicationPrescription();
					if (messageFromClient.equals("warningLetters"))
						warningLetters();
					if (messageFromClient.equals("todaysAppointments"))
						todaysAppointments();
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
