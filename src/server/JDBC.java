/**
 * This class implements the connection with the database and the SQL queries
 * that return the result sets needed.
 * 
 * @author Sotia Gregoriou, Elena Matsi, Erasmia Shimitra
 */

package server;

//Libraries
import java.io.*;
import java.sql.*;

public class JDBC {
	public static boolean dbDriverLoaded = false;
	public static Connection conn = null;
	public static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * This function connects the system with the database. Username:
	 * EPL362project, Password: GzMV6jFrWTLnB6Pp
	 * 
	 * @return Connection the connection with the database
	 */
	public Connection getDBConnection() {
		String url = "jdbc:mysql://studentdb.in.cs.ucy.ac.cy:3306/EPL362project";
		String username = "EPL362project";
		String password = "GzMV6jFrWTLnB6Pp";
		if (!dbDriverLoaded)
			try {
				Class.forName("com.mysql.jdbc.Driver");
				dbDriverLoaded = true;
			} catch (ClassNotFoundException e) {
				System.out.println("Cannot load DB driver!");
				return null;
			}
		try {
			if (conn == null)
				conn = DriverManager.getConnection(url, username, password);
			else if (conn.isClosed())
				conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.print("Cannot connect to the DB!\nGot error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * This function implements the query for the log in action.
	 * 
	 * @param Username
	 *            the username of the user
	 * @param Password
	 *            the password of the user
	 * @param Role
	 *            the role of the user (Staff or Patient)
	 * @return ResultSet the result of the query
	 */
	public ResultSet login(String Username, String Password, String Role) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (Role.equals("Patient"))
				query = "SELECT * FROM Patient WHERE PatientID='" + Username + "' AND Password='" + Password + "';";
			else
				query = "SELECT * FROM Staff WHERE StaffID='" + Username + "' AND Password='" + Password + "';";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the sign up action.
	 * 
	 * @param username
	 *            the username of the user
	 * @param password
	 *            the password of the user
	 * @param name
	 *            the name of the user
	 * @param surname
	 *            the surname of the user
	 * @param role
	 *            the role of the user (Doctor, Nurser, Health Visitor,
	 *            Receptionist, Medical Records, Management)
	 * @param phone
	 *            the phone of the user
	 * @param email
	 *            the email of the user
	 * @param address
	 *            the address of the user
	 * @return void
	 */
	public void signup(String username, String password, String name, String surname, String role, int phone,
			String email, String address) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Staff (StaffID, Password, Name, Surname, StaffType, Phone, Email, Address) VALUES ('"
					+ username + "', '" + password + "', '" + name + "', '" + surname + "', '" + role + "', '" + phone
					+ "', '" + email + "', '" + address + "');";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the changing password action.
	 * 
	 * @param username
	 *            the username of the user
	 * @param oldpassword
	 *            the old password of the user
	 * @param newpassword
	 *            the new password of the user
	 * @param role
	 *            the role of the user
	 * @return void
	 */
	public void changepassword(String username, String oldpassword, String newpassword, String role) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (role.equals("Patient"))
				query = "UPDATE Patient SET Password='" + newpassword + "' WHERE PatientID='" + username
						+ "' AND Password='" + oldpassword + "';";
			else
				query = "UPDATE Staff SET Password='" + newpassword + "' WHERE StaffID='" + username
						+ "' AND Password='" + oldpassword + "';";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the checking sign up action.
	 * 
	 * @param Username
	 *            the username of the user
	 * @return ResultSet the result of the query
	 */
	public ResultSet checksignup(String Username) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT StaffID FROM Staff WHERE StaffID='" + Username + "';";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the updating profile action.
	 * 
	 * @param name
	 *            the name of the user
	 * @param surname
	 *            the surname of the user
	 * @param username
	 *            the username of the user
	 * @param phone
	 *            the phone of the user
	 * @param email
	 *            the email of the user
	 * @param address
	 *            the address of the user
	 * @param role
	 *            the role of the user
	 * @return void
	 */
	public void updateProfile(String name, String surname, String username, int phone, String email, String address,
			String role) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (role.equals("Patient"))
				query = "UPDATE Patient SET Name='" + name + "', Surname='" + surname + "', Phone=" + phone
						+ ", Email='" + email + "', Address='" + address + "', ChangedByPatient=1 WHERE PatientID='" + username + "';";
			else
				query = "UPDATE Staff SET Name='" + name + "', Surname='" + surname + "', Phone=" + phone + ", Email='"
						+ email + "', Address='" + address + "' WHERE StaffID='" + username + "';";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the printing profile action.
	 * 
	 * @param username
	 *            the username of the user
	 * @param role
	 *            the role of the user
	 * @return ResultSet the result of the query
	 */
	public ResultSet printProfile(String username, String role) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			if (role.equals("Patient"))
				rs = stmt.executeQuery("SELECT * FROM Patient WHERE PatientID='" + username + "';");
			else
				rs = stmt.executeQuery("SELECT * FROM Staff WHERE StaffID='" + username + "';");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the adding patient action.
	 * 
	 * @param name
	 *            the name of the patient
	 * @param surname
	 *            the surname of the patient
	 * @param username
	 *            the username of the patient
	 * @param password
	 *            the password of the patient
	 * @param phone
	 *            the phone of the patient
	 * @param email
	 *            the email of the patient
	 * @param address
	 *            the address of the patient
	 * @return void
	 */
	public void addPatient(String name, String surname, String username, String password, int phone, String email,
			String address) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Patient (PatientID, Password, Name, Surname, Phone, Email, Address) VALUES ('"
					+ username + "', '" + password + "', '" + name + "', '" + surname + "', " + phone + ", '" + email
					+ "', '" + address + "');";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the updating patient action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param name
	 *            the name of the patient
	 * @param surname
	 *            the surname of the patient
	 * @param phone
	 *            the phone of the patient
	 * @param email
	 *            the email of the patient
	 * @param address
	 *            the address of the patient
	 * @return void
	 */
	public void updatePatient(String patientid, String name, String surname, int phone, String email, String address) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Patient SET Name='" + name + "', Surname='" + surname + "', Phone=" + phone
					+ ", Email='" + email + "', Address='" + address + "' WHERE PatientID='" + patientid + "';";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the updating harm risk record
	 * action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param self
	 *            the self harm risk possibility of the patient
	 * @param others
	 *            the others harm risk possibility of the patient
	 * @param status
	 *            the risk status of the patient
	 * @param dead
	 *            if patient dies then his/her records become readable only
	 * @return void
	 */
	public void updateHarmRisk(String patientid, int self, int others, String status, int dead) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Patient SET SelfHarmRisk=" + self + ", OthersHarmRisk=" + others + ", RiskStatus='"
					+ status + "', DeadReadOnly=" + dead + " WHERE PatientID='" + patientid + "';";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the deleting patient action.
	 * 
	 * @param username
	 *            the username of the patient
	 * @return void
	 */
	public void deletePatient(String username) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Patient WHERE PatientID='" + username + "';";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the printing patient action.
	 * 
	 * @param username
	 *            the username of the patient
	 * @return ResultSet the result of the query
	 */
	public ResultSet printPatient(String username) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			if (username == null)
				rs = stmt.executeQuery("SELECT * FROM Patient;");
			else
				rs = stmt.executeQuery("SELECT * FROM Patient WHERE PatientID='" + username + "';");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the adding relative action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param name
	 *            the name of the relative
	 * @param surname
	 *            the surname of the relative
	 * @param phone
	 *            the phone of the relative
	 * @param email
	 *            the email of the relative
	 * @param address
	 *            the address of the relative
	 * @param relationship
	 *            the relationship between patient and relative
	 * @return void
	 */
	public void addRelative(String patientid, String name, String surname, int phone, String email, String address,
			String relationship) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Relative (PatientID, Name, Surname, Phone, Email, Address, Relationship) VALUES ('"
					+ patientid + "', '" + name + "', '" + surname + "', " + phone + ", '" + email + "', '" + address
					+ "', '" + relationship + "');";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the updating relative action.
	 * 
	 * @param relativeid
	 *            the ID of the relative
	 * @param patientid
	 *            the username of the patient
	 * @param name
	 *            the name of the relative
	 * @param surname
	 *            the surname of the relative
	 * @param phone
	 *            the phone of the relative
	 * @param email
	 *            the email of the relative
	 * @param address
	 *            the address of the relative
	 * @param relationship
	 *            the relationship between patient and relative
	 * @return void
	 */
	public void updateRelative(int relativeid, String patientid, String name, String surname, int phone, String email,
			String address, String relationship) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Relative SET PatientID='" + patientid + "', Name='" + name + "', Surname='" + surname
					+ "', Phone=" + phone + ", Email='" + email + "', Address='" + address + "', Relationship='"
					+ relationship + "' WHERE RelativeID=" + relativeid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the deleting relative action.
	 * 
	 * @param relativeid
	 *            the ID of the relative
	 * @return void
	 */
	public void deleteRelative(int relativeid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Relative WHERE RelativeID=" + relativeid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the printing relative action.
	 * 
	 * @param id
	 *            the ID of the relative
	 * @return ResultSet the result of the query
	 */
	public ResultSet printRelative(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			if (id == -1)
				rs = stmt.executeQuery("SELECT * FROM Relative;");
			else
				rs = stmt.executeQuery("SELECT * FROM Relative WHERE RelativeID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the adding incident action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param type
	 *            the type of the incident (Accidental Treatment Incident,
	 *            Deliberate Incident, Threat)
	 * @param shortDescription
	 *            a short description of the incident
	 * @param description
	 *            a description of the incident
	 * @param date
	 *            the date of the incident
	 * @return void
	 */
	public void addIncident(String patientid, String type, String shortDescription, String description, String date) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Incident (PatientID, IncidentType, ShortDescription, Description, Date) VALUES ('"
					+ patientid + "', '" + type + "', '" + shortDescription + "', '" + description + "', '" + date
					+ "');";
			stmt.executeUpdate(query);
			Statement stmt2 = conn.createStatement();
			String query2 = "UPDATE Patient SET NumOfIncidents=NumOfIncidents+1 WHERE PatientID='" + patientid + "';";
			stmt2.executeUpdate(query2);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the updating incident action.
	 * 
	 * @param incidentid
	 *            the ID of the incident
	 * @param patientid
	 *            the username of the incident
	 * @param type
	 *            the type of the incident (Accidental Treatment Incident,
	 *            Deliberate Incident, Threat)
	 * @param shortDescription
	 *            a short description of the incident
	 * @param description
	 *            a description of the incident
	 * @param date
	 *            the date of the incident
	 * @return void
	 */
	public void updateIncident(int incidentid, String patientid, String type, String shortDescription,
			String description, String date) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Incident SET IncidentType='" + type + "', PatientID='" + patientid
					+ "', ShortDescription='" + shortDescription + "', Description='" + description + "', Date='" + date
					+ "' WHERE IncidentID=" + incidentid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the deleting incident action.
	 * 
	 * @param incidentid
	 *            the ID of the incident
	 * @return void
	 */
	public void deleteIncident(int incidentid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Incident WHERE IncidentID=" + incidentid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the printing incident action.
	 * 
	 * @param id
	 *            the ID of the incident
	 * @return ResultSet the result of the query
	 */
	public ResultSet printIncident(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			if (id == -1)
				rs = stmt.executeQuery("SELECT * FROM Incident;");
			else
				rs = stmt.executeQuery("SELECT * FROM Incident WHERE IncidentID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the adding treatment action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param startDate
	 *            the starting date of the treatment
	 * @param endDate
	 *            the ending date of the treatment
	 * @param diagnosis
	 *            the diagnosis of the treatment
	 * @param description
	 *            the description of the treatment
	 * @param staffid
	 *            the username of the staff
	 * @return void
	 */
	public void addTreatment(String patientid, String startDate, String endDate, String diagnosis, String description,
			String staffid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Treatment (PatientID, StartDate, EndDate, Diagnosis, Description, StaffID) VALUES ('"
					+ patientid + "', '" + startDate + "', '" + endDate + "', '" + diagnosis + "', '" + description
					+ "', '" + staffid + "');";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the adding treatment's medication
	 * action.
	 * 
	 * @param treatmentid
	 *            the ID of the treatment
	 * @param medicationid
	 *            the ID of the medication
	 * @param dose
	 *            the maximum dose of the medication
	 * @param description
	 *            the description of the medication
	 * @return void
	 */
	public void addTreatmentMedication(int treatmentid, int medicationid, int dose, String description) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO TreatmentMedication (TreatmentID, MedicationID, Dose, DoseDescription) VALUES ("
					+ treatmentid + ", " + medicationid + ", " + dose + ", '" + description + "');";
			stmt.executeUpdate(query);
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM Medication WHERE MedicationID=" + medicationid + ";");
			rs2.next();
			Statement stmt4 = conn.createStatement();
			ResultSet rs4 = stmt4.executeQuery("SELECT * FROM Treatment WHERE TreatmentID=" + treatmentid + ";");
			rs4.next();
			ResultSet rs5 = stmt4.executeQuery("SELECT * FROM MedicationReaction WHERE PatientID='"
					+ rs4.getString("PatientID") + "' AND MedicationID=" + medicationid + ";");
			int x = 1;
			if (!rs5.next())
				x = 0;
			if (dose > rs2.getInt("MaxDose") || x == 1) {
				String query3;
				Statement stmt3 = conn.createStatement();
				if (dose > rs2.getInt("MaxDose") && x == 1)
					query3 = "UPDATE TreatmentMedication SET WarningMessage='Prescription is overdosed and patient is allergic to medication!' WHERE TreatmentID="
							+ treatmentid + " AND MedicationID=" + medicationid + ";";
				else if (dose > rs2.getInt("MaxDose"))
					query3 = "UPDATE TreatmentMedication SET WarningMessage='Prescription is overdosed!' WHERE TreatmentID="
							+ treatmentid + " AND MedicationID=" + medicationid + ";";
				else
					query3 = "UPDATE TreatmentMedication SET WarningMessage='Patient is allergic to medication!' WHERE TreatmentID="
							+ treatmentid + " AND MedicationID=" + medicationid + ";";
				stmt3.executeUpdate(query3);
			}
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the checking for warning action.
	 * 
	 * @param treatmentid
	 *            the ID of the treatment
	 * @param medicationid
	 *            the ID of the medication
	 * @return void
	 */
	public String checkForWarning(int treatmentid, int medicationid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM TreatmentMedication WHERE TreatmentID=" + treatmentid
					+ " AND MedicationID=" + medicationid + ";");
			rs.next();
			return rs.getString("WarningMessage");
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the updating treatment action.
	 * 
	 * @param treatmentid
	 *            the ID of the treatment
	 * @param patientid
	 *            the username of the patient
	 * @param startDate
	 *            the starting date of the treatment
	 * @param endDate
	 *            the ending date of the treatment
	 * @param diagnosis
	 *            the diagnosis of the treatment
	 * @param description
	 *            the description of the treatment
	 * @param staffid
	 *            the username of the staff
	 * @return void
	 */
	public void updateTreatment(int treatmentid, String patientid, String startDate, String endDate, String diagnosis,
			String description, String staffid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Treatment SET PatientID='" + patientid + "', StartDate='" + startDate
					+ "', EndDate='" + endDate + "', Diagnosis='" + diagnosis + "', Description='" + description
					+ "', StaffID='" + staffid + "' WHERE TreatmentID=" + treatmentid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the finding treatment action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param startDate
	 *            the starting date of the treatment
	 * @param endDate
	 *            the ending date of the treatment
	 * @param diagnosis
	 *            the diagnosis of the treatment
	 * @param description
	 *            the description of the treatment
	 * @param staffid
	 *            the username of the staff
	 * @return
	 */
	public ResultSet findTreatment(String patientid, String startDate, String endDate, String diagnosis,
			String description, String staffid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Treatment WHERE PatientID='" + patientid
					+ "' AND StartDate='" + startDate + "' AND EndDate='" + endDate + "' AND Diagnosis='" + diagnosis
					+ "' AND Description='" + description + "' AND StaffID='" + staffid + "';");
			return rs;
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the finding previous treatment
	 * action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @return ResultSet the result of the query
	 */
	public ResultSet findPreviousTreatment(String patientid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM Treatment WHERE PatientID='" + patientid + "' ORDER BY StartDate DESC LIMIT 1");
			return rs;
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the renewing treatment action.
	 * 
	 * @param treatmentid
	 *            the ID of the treatment
	 * @param patientid
	 *            the username of the patient
	 * @param startDate
	 *            the starting date of the treatment
	 * @param endDate
	 *            the ending date of the treatment
	 * @param notes
	 *            the notes of the treatment
	 * @param staffid
	 *            the username of staff
	 * @return void
	 */
	public void renewTreatment(int treatmentid, String patientid, String startDate, String endDate, String notes,
			String staffid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO RenewTreatment (TreatmentID, PatientID, StartDate, EndDate, Notes, StaffID) VALUES ("
					+ treatmentid + ", '" + patientid + "', '" + startDate + "', '" + endDate + "', '" + notes + "', '"
					+ staffid + "');";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the deleting treatment action.
	 * 
	 * @param treatmentid
	 *            the ID of the treatment
	 * @return void
	 */
	public void deleteTreatment(int treatmentid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Treatment WHERE TreatmentID=" + treatmentid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the deleting treatment's
	 * medication action.
	 * 
	 * @param treatmentid
	 *            the ID of the treatment
	 * @param medicationid
	 *            the ID of the medication
	 * @return void
	 */
	public void deleteTreatmentMedication(int treatmentid, int medicationid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM TreatmentMedication WHERE TreatmentID=" + treatmentid + " AND MedicationID="
					+ medicationid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the overruling warning action.
	 * 
	 * @param treatmentid
	 *            the ID of the treatment
	 * @param medicationid
	 *            the ID of the medication
	 * @return void
	 */
	public void overruleWarning(int treatmentid, int medicationid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE TreatmentMedication SET OverruledWarning=1 WHERE TreatmentID=" + treatmentid
					+ " AND MedicationID=" + medicationid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the printing treatment action.
	 * 
	 * @param id
	 *            the ID of the treatment
	 * @return ResultSet the result of the query
	 */
	public ResultSet printTreatment(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			if (id == -1)
				rs = stmt.executeQuery("SELECT * FROM Treatment;");
			else
				rs = stmt.executeQuery("SELECT * FROM Treatment WHERE TreatmentID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the adding medication action.
	 * 
	 * @param brand
	 *            the brand of the medication
	 * @param name
	 *            the name of the medication
	 * @param description
	 *            the description of the medication
	 * @param effects
	 *            the known side effects of the medication
	 * @param dose
	 *            the dose of the medication
	 * @return void
	 */
	public void addMedication(String brand, String name, String description, String effects, int dose) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Medication (Brand, Name, Description, KnownSideEffects,MaxDose) VALUES ('"
					+ brand + "', '" + name + "', '" + description + "', '" + effects + "', " + dose + ");";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the updating medication action.
	 * 
	 * @param medicationid
	 *            the ID of the medication
	 * @param brand
	 *            the brand of the medication
	 * @param name
	 *            the name of the medication
	 * @param description
	 *            the description of the medication
	 * @param effects
	 *            the known side effects of the medication
	 * @param dose
	 *            the dose of the medication
	 * @return void
	 */
	public void updateMedication(int medicationid, String brand, String name, String description, String effects,
			int dose) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Medication SET Brand='" + brand + "', Name='" + name + "', Description='"
					+ description + "', KnownSideEffects='" + effects + "', MaxDose=" + dose + " WHERE MedicationID="
					+ medicationid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the deleting medication action.
	 * 
	 * @param medicationid
	 *            the ID of the medication
	 * @return void
	 */
	public void deleteMedication(int medicationid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Medication WHERE MedicationID=" + medicationid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the printing medication action.
	 * 
	 * @param id
	 *            the ID of the medication
	 * @return ResultSet the result of the query
	 */
	public ResultSet printMedication(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			if (id == -1)
				rs = stmt.executeQuery("SELECT * FROM Medication;");
			else
				rs = stmt.executeQuery("SELECT * FROM Medication WHERE MedicationID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the adding medication reaction
	 * action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param medicationid
	 *            the ID of the medication
	 * @param type
	 *            the type of the medication reaction
	 * @param description
	 *            the description of the medication reaction
	 * @return void
	 */
	public void addMedicationReaction(String patientid, int medicationid, String type, String description) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO MedicationReaction (PatientID, MedicationID, ReactionType, Description) VALUES ('"
					+ patientid + "', " + medicationid + ", '" + type + "', '" + description + "');";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the updating medication reaction
	 * action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param medicationid
	 *            the ID of the medication
	 * @param type
	 *            the type of the medication reaction
	 * @param description
	 *            the description of the medication reaction
	 * @return void
	 */
	public void updateMedicationReaction(String patientid, int medicationid, String type, String description) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE MedicationReaction SET PatientID='" + patientid + "', MedicationID=" + medicationid
					+ ", ReactionType='" + type + "', Description='" + description + "' WHERE PatientID='" + patientid
					+ "' AND MedicationID=" + medicationid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the deleting medication reaction
	 * action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param medicationid
	 *            the ID of the medication
	 * @return void
	 */
	public void deleteMedicationReaction(String patientid, int medicationid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM MedicationReaction WHERE PatientID='" + patientid + "' AND MedicationID="
					+ medicationid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the printing medication reaction
	 * action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param medicationid
	 *            the ID of the medication
	 * @return ResultSet the result of the query
	 */
	public ResultSet printMedicationReaction(String patientid, int medicationid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			if (patientid == null || medicationid == -1)
				rs = stmt.executeQuery("SELECT * FROM MedicationReaction ORDER BY PatientID;");
			else
				rs = stmt.executeQuery("SELECT * FROM MedicationReaction WHERE PatientID='" + patientid
						+ "' AND MedicationID=" + medicationid + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the adding consultation action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param staffid
	 *            the username of the staff
	 * @param subject
	 *            the subject of the consultation
	 * @param dateBooked
	 *            the date that the consultation was booked
	 * @param date
	 *            the date of the consultation
	 * @param time
	 *            the time of the consultation
	 * @param treatmentid
	 *            the ID of the treatment
	 * @return void
	 */
	public void addConsultation(String patientid, String staffid, String subject, String dateBooked, String date,
			String time, int treatmentid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Consultation (PatientID, StaffID, Subject, DateBooked, Date, Time, TreatmentID) VALUES ('"
					+ patientid + "', '" + staffid + "', '" + subject + "', '" + dateBooked + "', '" + date + "', '"
					+ time + "', " + treatmentid + ");";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the updating consultation action.
	 * 
	 * @param consultationid
	 *            the ID of the consultation
	 * @param patientid
	 *            the username of the patient
	 * @param staffid
	 *            the username of the staff
	 * @param subject
	 *            the subject of the consultation
	 * @param dateBooked
	 *            the date that the consultation was booked
	 * @param date
	 *            the date of the consultation
	 * @param time
	 *            the time of the consultation
	 * @param attended
	 *            1 if patient attended the consultation, 0 otherwise
	 * @param updated
	 *            1 if changed by medical record staff, 0 otherwise
	 * @param treatmentid
	 *            the ID of the treatment
	 * @return void
	 */
	public void updateConsultation(int consultationid, String patientid, String staffid, String subject,
			String dateBooked, String date, String time, int attended, int updated, int treatmentid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Consultation SET PatientID='" + patientid + "', StaffID='" + staffid + "', Subject='"
					+ subject + "', DateBooked='" + dateBooked + "', Date='" + date + "', Time='" + time
					+ "', Attended=" + attended + ", MedicalRecordUpdated=" + updated + ", TreatmentID=" + treatmentid
					+ " WHERE ConsultationID=" + consultationid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the deleting consultation action.
	 * 
	 * @param consultationid
	 *            the ID of the consultation
	 * @return void
	 */
	public void deleteConsultation(int consultationid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Consultation WHERE ConsultationID=" + consultationid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the printing consultation action.
	 * 
	 * @param id
	 *            the ID of the consultation
	 * @return ResultSet the result of the query
	 */
	public ResultSet printConsultation(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			if (id == -1)
				rs = stmt.executeQuery("SELECT * FROM Consultation;");
			else
				rs = stmt.executeQuery("SELECT * FROM Consultation WHERE ConsultationID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the adding comment action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param staffid
	 *            the username of the staff
	 * @param subject
	 *            the subject of the comment
	 * @param comment
	 *            the message of the comment
	 * @return
	 */
	public void addComment(String patientid, String staffid, String subject, String comment) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Comments (PatientID, StaffID,Subject, Comment) VALUES ('" + patientid + "', '"
					+ staffid + "', '" + subject + "', '" + comment + "');";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the updating comment action.
	 * 
	 * @param commentid
	 *            the ID of the comment
	 * @param patientid
	 *            the username of the patient
	 * @param staffid
	 *            the username of the staff
	 * @param subject
	 *            the subject of the comment
	 * @param comment
	 *            the message of the comment
	 * @return
	 */
	public void updateComment(int commentid, String patientid, String staffid, String subject, String comment) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Comments SET PatientID='" + patientid + "', StaffID='" + staffid + "', Subject='"
					+ subject + "', Comment='" + comment + "' WHERE CommentID=" + commentid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the deleting comment action.
	 * 
	 * @param commentid
	 *            the ID of the comment
	 * @return void
	 */
	public void deleteComment(int commentid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Comments WHERE CommentID=" + commentid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This function implements the query for the printing comment action.
	 * 
	 * @param commentid
	 *            the ID of the comment
	 * @return ResultSet the result of the query
	 */
	public ResultSet printComment(int commentid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			if (commentid == -1)
				rs = stmt.executeQuery("SELECT * FROM Comments;");
			else
				rs = stmt.executeQuery("SELECT * FROM Comments WHERE CommentID=" + commentid + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function implements the query for the informing relatives action.
	 * 
	 * @param patientid
	 *            the username of the patient
	 * @param staffid
	 *            the username of the staff
	 * @param subject
	 *            the subject of the email
	 * @param message
	 *            the message of the email
	 * @return void
	 */
	public void informRelatives(String patientid, String staffid, String subject, String message) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO InformRelatives (PatientID, StaffID, Subject, Message) VALUES ('" + patientid
					+ "', '" + staffid + "', '" + subject + "', '" + message + "');";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	public ResultSet viewWarningLetters(String PatientID) {
		try {
			Statement stmt = conn.createStatement();
			String query = "";
			if (PatientID != null)
				query += "Select * From InformRelatives Where PatientID='" + PatientID + "';";
			else
				query += "Select * From InformRelatives";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ResultSet viewTodaysAppointments(String myDate) {
		try {
			Statement stmt = conn.createStatement();
			String query = "";
			if (myDate != null)
				query += "Select * From Consultation Where Date='" + myDate + "';";
			else
				query += "Select * From Consultation Order By Date DESC;";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
		return null;
	}

	// REPORTS

	/**
	 * This function returns the ResultSet of four reports, depending on the
	 * parameters : (A) Attended Daily Consultations; if attended=true and
	 * myDate!=null, (B) Attended General Consultations; if attended=true and
	 * myDate==null, (C) Non Attended Daily Consultations; if attended=false and
	 * myDate!=null (D) Non Attended General Consultations; if attended=false
	 * and myDate==null
	 * 
	 * @param boolean
	 *            attended : true if the patient attended the consultations,
	 *            false if the patient didn't attend the consultations
	 * 
	 * @param String
	 *            myDate : the date of the consultations in the form
	 *            "yyyy-mm-dd", or null if general instead of daily report is
	 *            requested
	 * 
	 * @return ResultSet : Values, the result of the query (Fields from entity
	 *         ConsultationReport, Fields: Consultation.ConsultationID,
	 *         Consultation.Subject, Consultation.Date, Consultation.Time,
	 *         Patient.PatientID, Patient.Name, Patient.Surname, Staff.StaffID,
	 *         Staff.Name, Staff.Surname, Consultation.MedicalRecordUpdated)
	 */
	public ResultSet getConsultationReport(boolean attended, String myDate) {
		try {
			Statement stmt = conn.createStatement();
			String attend = "'0'";
			if (attended)
				attend = "'1'";
			String query = "Select Consultation.ConsultationID, Consultation.Subject, "
					+ "Consultation.Date, Consultation.Time, Patient.PatientID, "
					+ "Patient.Name, Patient.Surname, Staff.StaffID, Staff.Name, "
					+ "Staff.Surname, Consultation.MedicalRecordUpdated From Patient, "
					+ "Consultation, Staff Where Consultation.Attended=" + attend;
			if (myDate != null) { // Daily
				query += "AND Consultation.Date='" + myDate + "' ";
			}
			query += "AND Patient.PatientID=Consultation.PatientID AND Consultation.StaffID=Staff.StaffID;";

			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function returns the ResultSet of four reports, depending on the
	 * parameters : (A) Non Updated Daily Medical Records; if myDate!=null and
	 * (B) Non Updated General Medical Records; myDate==null. The ResultSet
	 * returned gets info about ConsultationReport records (object).
	 * 
	 * @param String
	 *            myDate : the date of the consultations in the form
	 *            "yyyy-mm-dd", or null if general instead of daily report is
	 *            requested
	 * 
	 * @return ResultSet : Values, the result of the query (Fields from entity
	 *         ConsultationReport, Fields: Consultation.ConsultationID,
	 *         Consultation.Subject, Consultation.Date, Consultation.Time,
	 *         Patient.PatientID, Patient.Name, Patient.Surname, Staff.StaffID,
	 *         Staff.Name, Staff.Surname, Consultation.MedicalRecordUpdated)
	 */
	public ResultSet getUpdatedMedicalRecordsReport(String myDate) {
		try {
			Statement stmt = conn.createStatement();
			String query = "Select Consultation.ConsultationID, Consultation.Subject, "
					+ "Consultation.Date, Consultation.Time, Patient.PatientID, "
					+ "Patient.Name, Patient.Surname, Staff.StaffID, Staff.Name, "
					+ "Staff.Surname, Consultation.MedicalRecordUpdated From Patient, "
					+ "Consultation, Staff Where Consultation.Attended='1'";
			if (myDate != null) { // Daily
				query += "AND Consultation.Date='" + myDate + "' ";
			}
			query += "AND Consultation.MedicalRecordUpdated='0' AND Patient.PatientID="
					+ "Consultation.PatientID AND Consultation.StaffID=Staff.StaffID;";

			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * This function returns the ResultSet of three reports, depending on the
	 * parameters : (A) Changes of Patient Profiles; if option==1 and
	 * value==null, (B) Patients with specific Condition; if option==2 and value
	 * !=null, (C) Patients with specific Treatment/Medication; if option==3 and
	 * value !=null. The RetultSet returnd gets info about Patient records
	 * (object).
	 * 
	 * @param int
	 *            option : 1 for Changes of Patient Profiles, 2 for Patients
	 *            with specific Condition, 3 for Patients with specific
	 *            Treatment/Medication
	 * 
	 * @param String
	 *            value : null if option 1 is selected, Name of a condition if
	 *            option 2 is selected, Name of a Medication if option 3 is
	 *            selected
	 * 
	 * @return ResultSet : Values, the result of the query (Fields of Patient
	 *         entity)
	 * 
	 */
	public ResultSet getPatientReport(int option, String value) {
		try {
			Statement stmt = conn.createStatement();
			String query = "";
			switch (option) {
			case 1: // Changes of Patient Profiles
				query = "Select * From Patient Where ChangedByPatient='1';";
				break;

			case 2: // Patients with specific Condition/Diagnosis,
					// so value = condition/diagnosis name
				query = "Select Patient.* From Patient, Treatment Where Treatment.Diagnosis='" + value
						+ "' AND Patient.PatientID=Treatment.PatientID Group By Patient.PatientID;";
				break;

			case 3: // Patients with specific Treatment/Medication,
					// so value = medication name
				query = "Select Patient.* From Patient, Treatment, TreatmentMedication, Medication Where "
						+ "Medication.Name='" + value + "' AND Patient.PatientID=Treatment.PatientID AND "
						+ "Treatment.TreatmentID=TreatmentMedication.TreatmentID AND "
						+ "TreatmentMedication.MedicationID=Medication.MedicationID Group By Patient.PatientID;";
			}

			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Number of Patients Attended. This function returns the result set of the
	 * report which shows the number of patients who attended the clinic for
	 * each day.
	 * 
	 * @return ResultSet : Values, the result of the query (Entity
	 *         AttendanceReport, fields: int, String-Date)
	 */
	public ResultSet getAttendanceReport() {
		try {
			Statement stmt = conn.createStatement();
			String query = "Select COUNT(PatientID), Date From Consultation "
					+ "Where Attended='1' Group By Date Order By COUNT(PatientID);";

			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Number of Patients by Condition. This function returns the result set of
	 * the report which shows the number of patients who were marked with the
	 * same diagnosis/condition, for each one of them.
	 * 
	 * @return ResultSet : Values, the result of the query (Entity
	 *         ConditionReport, fields: int, String)
	 */
	public ResultSet getConditionReport() {
		try {
			Statement stmt = conn.createStatement();
			String query = "Select COUNT(PatientID), Diagnosis From Treatment "
					+ "Group By Diagnosis Order By COUNT(PatientID) DESC";

			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Number of Patients by Treatment/Medication, Report. This function returns
	 * the result set of the report which shows the number of patients who were
	 * prescribed with the same medication (of the same brand), for each one of
	 * them.
	 * 
	 * @return ResultSet : Values, the result of the query (Entity
	 *         MedicationReport, fields: int, int, String)
	 */
	public ResultSet getMedicationReport() {
		try {
			Statement stmt = conn.createStatement();
			String query = "Select COUNT(Treatment.PatientID), Medication.MedicationID, Medication.Name "
					+ "From Treatment, TreatmentMedication, Medication Where "
					+ "Treatment.TreatmentID=TreatmentMedication.TreatmentID AND "
					+ "TreatmentMedication.MedicationID=Medication.MedicationID "
					+ "Group By Medication.MedicationID Order By COUNT(Treatment.PatientID) DESC;";

			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Medication prescriptions summary. This function returns the result set of
	 * the report which shows a summary of the medication prescriptions from all
	 * treatment records of the clinic.
	 * 
	 * @return ResultSet : Values, the result of the query (Entity used is
	 *         MedicationPrescription, which contains fields from 3 entities:
	 *         Treatment, TreatmentMedication, Medication)
	 */
	public ResultSet getMedicationPrescriptionsSummary() {
		try {
			Statement stmt = conn.createStatement();
			String query = "Select * From Treatment, TreatmentMedication, Medication "
					+ "Where Treatment.TreatmentID=TreatmentMedication.TreatmentID "
					+ "AND TreatmentMedication.MedicationID=Medication.MedicationID;";

			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}
