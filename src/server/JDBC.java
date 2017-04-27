package server;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
	
	public void updatePatient(String name, String surname, int phone, String email, String address) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Patient SET Name='"+name+"', Surname='"+surname+"', Phone="+phone+", Email='"+email+"', Address='"+address+"';";
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
	
	public void addRelative(String patientusername, String name, String surname, int phone, String email, String address, String relationship) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Relative (PatientID, Name, Surname, Phone, Email, Address, Relationship) VALUES ('"+ patientusername + "', '" + name + "', '" + surname + "', '"+ phone + "', '" + email + "', '" + address + "', '"+relationship+"';";
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public void updateRelative(String patientusername, String name, String surname, int phone, String email, String address, String relationship) {
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Relative SET PatientID='"+patientusername+"', Name='"+name+"', Surname='"+surname+"', Phone="+phone+", Email='"+email+"', Address='"+address+"', Relationship='"+relationship+"';";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public void deleteRelative(int RelativeID) {
		try {
			Statement stmt = conn.createStatement();
			String query = "DELETE FROM Relative WHERE RelativeID="+RelativeID+";";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
	}
	
	public ResultSet searchRelative(String relativeid, String patientusername, String name, String surname, String phone, String email, String address, String relationship) {
		try {
			conn.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String query = "SELECT * FROM Patient WHERE ";
			
			boolean first = true;
			
			if (relativeid.equals("") && name.equals("") && surname.equals("") && phone.equals("") && email.equals("") && address.equals("") && relationship.equals(""))
				query += "PatientID= ''";
			
			if (!relativeid.equals("")) {
				query += "Relative=" + relativeid;
				first = false;
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
			// System.out.println(e.getMessage());
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
