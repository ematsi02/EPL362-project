package server;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class JDBC {
	public static boolean dbDriverLoaded = false;
	public Connection conn = null;
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
	
	public static DefaultTableModel buildTableModel(ArrayList<Object> list, ArrayList<String> columnNames) throws SQLException {
		int columnCount = columnNames.size();
		int i = 0;
		Vector<String> columns = new Vector<String>();
		for (int column = 1; column <= columnCount; column++) {
			columns.add(columnNames.get(i-1));
		}
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (i<columnCount) {
			Object element = list.get(i);
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(element);
			}
			data.add(vector);
		}
		return new DefaultTableModel(data, columns);
	}

	public void signup(String username, String password, String name, String surname, String role, int phone,
			String email, String address) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Staff (StaffID, Password, Name, Surname, StaffType, Phone, Email, Address) VALUES ('"
						+ username + "', '" + password + "', '" + name + "', '" + surname + "', '" + role + "', '"
						+ phone + "', '" + email + "', '" + address + "');";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}

	public void addPatient(String name, String surname, String username, String password, int phone, String email, String address) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Patient (PatientID, Password, Name, Surname, Phone, Email, Address) VALUES ('"+ username + "', '" + password + "', '" + name + "', '" + surname + "', "+ phone + ", '" + email + "', '" + address + "');";
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
			String query = "UPDATE Patient SET Name='"+name+"', Surname='"+surname+"', Phone="+phone+", Email='"+email+"', Address='"+address+"' WHERE PatientID='"+patientid+"';";
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
			String query = "DELETE FROM Patient WHERE PatientID='"+username+"';";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public ResultSet searchPatient(String name, String surname, String username, String phone, String email, String address, String incidents) {
		try {
			conn.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Patient WHERE ";
			
			boolean first = true;
			
			if (name.equals("") && surname.equals("") && username.equals("") && phone.equals("") && email.equals("") && address.equals("") && incidents.equals(""))
				query += "PatientID= ''";
			
			if (!name.equals("")) {
				query += "Name='" + name + "'" ;
				first = false;
			}
			if (!surname.equals("")) {
				if (first){
					query += "Surname='" + surname + "'";
				first = false;	
				}
				else
					query += " AND Surname='" + surname + "'";
			}
			if (!username.equals("")) {
				if (first){
					query += "PatientID='" + username + "'";
					first = false;	
				}
				else
					query += " AND PatientID='" + username + "'";
			}
			if (!phone.equals("")) {
				if (first){
					query += "Phone=" + phone;
					first = false;	
				}
				else
					query += " AND Phone=" + phone;
			}
			if (!email.equals("")) {
				if (first){
					query += "Email='" + email + "'";
					first = false;	
				}
				else
					query += " AND Email='" + email + "'";
			}
			if (!address.equals("")) {
				if (first){
					query += "Address='" + address + "'";
					first = false;	
				}
				else
					query += " AND Address='" + address + "'";
			}
			if (!incidents.equals("")) {
				if (first){
					query += "NumOfIncidents=" + incidents;
					first = false;	
				}
				else
					query += " AND NumOfIncidents=" + incidents;
			}		
			ResultSet rs = stmt.executeQuery(query);
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
	
	public ResultSet printPatients() {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("SELECT * FROM Patient");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void addRelative(String patientid, String name, String surname, int phone, String email, String address, String relationship) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Relative (PatientID, Name, Surname, Phone, Email, Address, Relationship) VALUES ('"+ patientid + "', '" + name + "', '" + surname + "', "+ phone + ", '" + email + "', '" + address + "', '"+relationship+"');";
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public void updateRelative(int relativeid, String patientid, String name, String surname, int phone, String email, String address, String relationship) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Relative SET PatientID='"+patientid+"', Name='"+name+"', Surname='"+surname+"', Phone="+phone+", Email='"+email+"', Address='"+address+"', Relationship='"+relationship+"' WHERE RelativeID="+relativeid+";";
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
			String query = "DELETE FROM Relative WHERE RelativeID="+relativeid+";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public ResultSet searchRelative(String relativeid, String patientid, String name, String surname, String phone, String email, String address, String relationship) {
		try {
			conn.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Relative WHERE ";
			
			boolean first = true;
			
			if (relativeid.equals("") && patientid.equals("") && name.equals("") && surname.equals("") && phone.equals("") && email.equals("") && address.equals("") && relationship.equals(""))
				query += "RelativeID= ''";
			
			if (!relativeid.equals("")) {
				query += "RelativeID=" + relativeid;
				first = false;
			}
			if (!patientid.equals("")) {
				if (first){
					query += "PatientID='" + patientid + "'";
				first = false;	
				}
				else
					query += " AND PatientID='" + patientid + "'";
			}
			if (!name.equals("")) {
				if (first){
					query += "Name='" + name + "'";
				first = false;	
				}
				else
					query += " AND Name='" + name + "'";
			}
			if (!surname.equals("")) {
				if (first){
					query += "Surname='" + surname + "'";
				first = false;	
				}
				else
					query += " AND Surname='" + surname + "'";
			}
			if (!phone.equals("")) {
				if (first){
					query += "Phone=" + phone;
					first = false;	
				}
				else
					query += " AND Phone=" + phone;
			}
			if (!email.equals("")) {
				if (first){
					query += "Email='" + email + "'";
					first = false;	
				}
				else
					query += " AND Email='" + email + "'";
			}
			if (!address.equals("")) {
				if (first){
					query += "Address='" + address + "'";
					first = false;	
				}
				else
					query += " AND Address='" + address + "'";
			}
			if (!relationship.equals("")) {
				if (first){
					query += "Relationship='" + relationship + "'";
					first = false;	
				}
				else
					query += " AND Relationshop='" + relationship + "'";
			}		
			ResultSet rs = stmt.executeQuery(query);
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
	
	public ResultSet printRelatives() {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("SELECT * FROM Relative");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void addIncident(String patientid, String type, String shortDescription, String description, String date) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Incident (PatientID, IncidentType, ShortDescription, Description, Date) VALUES ('"+ patientid + "', '" + type + "', '" + shortDescription + "', '" + description + "', '"+ date + "');";
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public void updateIncident(int incidentid, String patientid, String type, String shortDescription, String description, String date) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Incident SET IncidentType='"+type+"', PatientID='"+patientid+"', ShortDescription='"+shortDescription+"', Description='"+description+"', Date='"+date+"' WHERE IncidentID="+incidentid+";";
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
			String query = "DELETE FROM Incident WHERE IncidentID="+incidentid+";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public ResultSet searchIncident(String incidentid, String patientid, String type, String date) {
		try {
			conn.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Incident WHERE ";
			
			boolean first = true;
			
			if (incidentid.equals("") && patientid.equals("") && type.equals("") && date.equals(""))
				query += "IncidentID= ''";
			
			if (!incidentid.equals("")) {
				query += "IncidentID='" + incidentid + "'" ;
				first = false;
			}
			if (!patientid.equals("")) {
				if (first){
					query += "PatientID='" + patientid + "'";
				first = false;	
				}
				else
					query += " AND PatientID='" + patientid + "'";
			}
			if (!type.equals("")) {
				if (first){
					query += "IncidentType='" + type + "'";
				first = false;	
				}
				else
					query += " AND IncidentType='" + type + "'";
			}
			if (!date.equals("")) {
				if (first){
					query += "Date='" + date + "'";
					first = false;	
				}
				else
					query += " AND Date='" + date + "'";
			}
			ResultSet rs = stmt.executeQuery(query);
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
	
	public ResultSet printIncidents() {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("SELECT * FROM Incident");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void addTreatment(String patientid, String startDate, String endDate, String diagnosis, String description, String staffid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Treatment (PatientID, StartDate, EndDate, Diagnosis, Description, StaffID) VALUES ('"+ patientid + "', '" + startDate + "', '" + endDate + "', '" + diagnosis + "', '" + description + "', '" + staffid + "');";
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public void updateTreatment(int treatmentid, String patientid, String startDate, String endDate, String diagnosis, String description, String staffid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Treatment SET PatientID='"+patientid+"', StartDate='"+startDate+"', EndDate='"+endDate+"', Diagnosis='"+diagnosis+"', Description='"+description+"', StaffID='"+staffid+"' WHERE TreatmentID="+treatmentid+";";
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
			String query = "DELETE FROM Treatment WHERE TreatmentID="+treatmentid+";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public ResultSet searchTreatment(String treatmentid, String patientid, String startDate, String endDate, String staffid) {
		try {
			conn.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Treatment WHERE ";
			
			boolean first = true;
			
			if (treatmentid.equals("") && patientid.equals("") && startDate.equals("") && endDate.equals("") && staffid.equals(""))
				query += "TreatmentID= ''"; 
			
			if (!treatmentid.equals("")) {
				query += "TreatmentID='" + treatmentid + "'" ;
				first = false;
			}
			if (!patientid.equals("")) {
				if (first){
					query += "PatientID='" + patientid + "'";
				first = false;	
				}
				else
					query += " AND PatientID='" + patientid + "'";
			}
			if (!startDate.equals("")) {
				if (first){
					query += "StartDate='" + startDate + "'";
					first = false;	
				}
				else
					query += " AND StartDate='" + startDate + "'";
			}
			if (!endDate.equals("")) {
				if (first){
					query += "EndDate='" + endDate + "'";
					first = false;	
				}
				else
					query += " AND EndDate='" + endDate + "'";
			}
			if (!staffid.equals("")) {
				if (first){
					query += "StaffID='" + staffid + "'";
					first = false;	
				}
				else
					query += " AND StaffID='" + staffid + "'";
			}
			ResultSet rs = stmt.executeQuery(query);
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
	
	public ResultSet printTreatments() {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("SELECT * FROM Treatment");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void addMedication(String brand, String name, String description, String effects) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Medication (Brand, Name, Description, KnownSideEffects) VALUES ('"+ brand + "', '" + name + "', '" + description + "', '" + effects + "');";
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public void updateMedication(int medicationid, String brand, String name, String description, String effects) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Medication SET Brand='"+brand+"', Name='"+name+"', Description='"+description+"', KnownSideEffects='"+effects+"' WHERE MedicationID="+medicationid+";";
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
			String query = "DELETE FROM Medication WHERE MedicationID="+medicationid+";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public ResultSet searchMedication(String medicationid, String brand, String name) {
		try {
			conn.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Medication WHERE ";
			
			boolean first = true;
			
			if (medicationid.equals("") && brand.equals("") && name.equals(""))
				query += "MedicationID= ''"; 
			
			if (!medicationid.equals("")) {
				query += "MedicationID='" + medicationid + "'" ;
				first = false;
			}
			if (!brand.equals("")) {
				if (first){
					query += "Brand='" + brand + "'";
				first = false;	
				}
				else
					query += " AND Brand='" + brand + "'";
			}
			if (!name.equals("")) {
				if (first){
					query += "Name='" + name + "'";
					first = false;	
				}
				else
					query += " AND Name='" + name + "'";
			}
			ResultSet rs = stmt.executeQuery(query);
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
	
	public ResultSet printMedications() {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("SELECT * FROM Medication");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public void addConsultation(String patientid, String staffid, String subject, String dateBooked, String date, String time, String treatmentid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Consultation (PatientID, StaffID, Subject, DateBooked, Date, Time, TreatmentID) VALUES ('"+ patientid + "', '" + staffid + "', '" + subject + "', '" + dateBooked + "', '" + date + "', '" + time + "', '" + treatmentid + "');";
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public void updateConsultation(int consultationid, String patientid, String staffid, String subject, String dateBooked, String date, String time, int attended, int updated, int treatmentid) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Consultation SET PatientID='"+patientid+"', StaffID='"+staffid+"', Subject='"+subject+"', DateBooked='"+dateBooked+"', Date='"+date+"', Time='"+time+"', Attended="+attended+", MedicalRecordUpdated="+updated+", TreatmentID="+treatmentid+" WHERE ConsultationID="+consultationid+";";
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
			String query = "DELETE FROM Consultation WHERE ConsultationID="+consultationid+";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public ResultSet searchConsultation(String consultationid, String patientid, String staffid, String subject, String dateBooked, String date, String time, String attended, String updated, String treatmentid) {
		try {
			conn.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Consultation WHERE ";
			
			boolean first = true;
			
			if (consultationid.equals("") && patientid.equals("") && staffid.equals("") && subject.equals("") && dateBooked.equals("") && date.equals("") && time.equals("") && attended.equals("") && updated.equals("") && treatmentid.equals(""))
				query += "ConsultationID= ''"; 
			
			if (!consultationid.equals("")) {
				query += "ConsultationID='" + consultationid + "'" ;
				first = false;
			}
			if (!patientid.equals("")) {
				if (first){
					query += "PatientID='" + patientid + "'";
				first = false;	
				}
				else
					query += " AND PatientID='" + patientid + "'";
			}
			if (!staffid.equals("")) {
				if (first){
					query += "StaffID='" + staffid + "'";
					first = false;	
				}
				else
					query += " AND StaffID='" + staffid + "'";
			}
			if (!subject.equals("")) {
				if (first){
					query += "Subject='" + subject + "'";
					first = false;	
				}
				else
					query += " AND Subject='" + subject + "'";
			}
			if (!dateBooked.equals("")) {
				if (first){
					query += "DateBooked='" + dateBooked + "'";
					first = false;	
				}
				else
					query += " AND DateBooked='" + dateBooked + "'";
			}
			if (!date.equals("")) {
				if (first){
					query += "Date='" + date + "'";
					first = false;	
				}
				else
					query += " AND Date='" + date + "'";
			}
			if (!time.equals("")) {
				if (first){
					query += "Time='" + time + "'";
					first = false;	
				}
				else
					query += " AND Time='" + time + "'";
			}
			if (!attended.equals("")) {
				if (first){
					query += "Attended='" + attended + "'";
					first = false;	
				}
				else
					query += " AND Attended='" + attended + "'";
			}
			if (!updated.equals("")) {
				if (first){
					query += "MedicalRecordUpdated='" + updated + "'";
					first = false;	
				}
				else
					query += " AND MedicalRecordUpdated='" + updated + "'";
			}
			if (!treatmentid.equals("")) {
				if (first){
					query += "TreatmentID='" + treatmentid + "'";
					first = false;	
				}
				else
					query += " AND TreatmentID='" + treatmentid + "'";
			}
			ResultSet rs = stmt.executeQuery(query);
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
	
	public ResultSet printConsultations() {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("SELECT * FROM Consultation");
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
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

	public void changepassword(String username, String oldpassword, String newpassword, String role) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (role.equals("Patient"))
				query = "UPDATE Patient SET Password='" + newpassword + "' WHERE PatientID='" + username + "' AND Password='" + oldpassword +"';";
			else
				query = "UPDATE Staff SET Password='" + newpassword + "' WHERE StaffID='" + username + "' AND Password='" + oldpassword +"';";
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
}
