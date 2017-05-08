package server;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class JDBC {
	public static boolean dbDriverLoaded = false;
	public static Connection conn = null;
	public static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

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

	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		return new DefaultTableModel(data, columnNames);
	}

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

	public void updateProfile(String name, String surname, String username, int phone, String email, String address,
			String role) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (role.equals("Patient"))
				query = "UPDATE Patient SET Name='" + name + "', Surname='" + surname + "', Phone=" + phone
						+ ", Email='" + email + "', Address='" + address + "' WHERE PatientID='" + username + "';";
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

	public ResultSet printPatient(String username) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Patient WHERE PatientID='" + username + "';");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

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

	public ResultSet printRelative(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Relative WHERE RelativeID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void addIncident(String patientid, String type, String shortDescription, String description, String date) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Incident (PatientID, IncidentType, ShortDescription, Description, Date) VALUES ('"
					+ patientid + "', '" + type + "', '" + shortDescription + "', '" + description + "', '" + date
					+ "');";
			stmt.executeUpdate(query);
			Statement stmt2 = conn.createStatement();
			String query2 = "UPDATE Patient SET NumOfIncidents=NumOfIncidents+1 WHERE PatientID='"+ patientid + "';";
			stmt2.executeUpdate(query2);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

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

	public ResultSet printIncident(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Incident WHERE IncidentID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

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

	public ResultSet printTreatment(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Treatment WHERE TreatmentID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void addMedication(String brand, String name, String description, String effects,int dose) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Medication (Brand, Name, Description, KnownSideEffects,MaxDose) VALUES ('" + brand
					+ "', '" + name + "', '" + description + "', '" + effects + "', "+dose+");";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	public void updateMedication(int medicationid, String brand, String name, String description,
			String effects,int dose) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Medication SET Brand='" + brand + "', Name='" + name + "', Description='"
					+ description + "', KnownSideEffects='" + effects + "', MaxDose=" + dose + " WHERE MedicationID=" + medicationid + ";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

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

	public ResultSet printMedication(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Medication WHERE MedicationID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public ResultSet erasmia() {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String q = "SELECT Consultation.ConsultationID, Consultation.Subject, "
					+ "Consultation.Date, Consultation.Time, Patient.PatientID, Patient.Name, "
					+ "Patient.Surname, Staff.Name, Staff.Surname, Consultation.MedicalRecordUpdated "
					+ "FROM Patient, Consultation, Staff "
					+ "WHERE Consultation.Attended='0' AND Consultation.Date='2017-05-14' "
					+ "AND Patient.PatientID=Consultation.PatientID AND Consultation.StaffID=Staff.StaffID;";
			q = "Select * from Patient, Consultation, Staff where Patient.PatientID=Consultation.PatientID AND Consultation.StaffID=Staff.StaffID;";
			ResultSet rs = stmt.executeQuery("Select * from Patient;");
			return rs;
		} catch (SQLException e) {
			System.out.println("throw exception from jdbc");
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void addConsultation(String patientid, String staffid, String subject, String dateBooked, String date,
			String time, String treatmentid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Consultation (PatientID, StaffID, Subject, DateBooked, Date, Time, TreatmentID) VALUES ('"
					+ patientid + "', '" + staffid + "', '" + subject + "', '" + dateBooked + "', '" + date + "', '"
					+ time + "', '" + treatmentid + "');";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

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

	public ResultSet printConsultation(int id) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Consultation WHERE ConsultationID=" + id + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void addComment(String patientid, String staffid, String subject, String comment) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Comments (PatientID, StaffID, Comment) VALUES ('" + patientid + "', '" + staffid
					+ "', '" + subject + "', '" + comment + "');";
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

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

	public ResultSet printComment(int commentid) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Comments WHERE CommentID=" + commentid + ";");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

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
}
