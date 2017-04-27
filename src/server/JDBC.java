package gui;

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
			String query;
			if (role.equals("Doctor") || role.equals("Nurse") || role.equals("Health Visitor"))
				query = "INSERT INTO ClinicalStaff (ClinicalStaffID, Password, Name, Surname, StaffType, Phone, Email, Address) VALUES ('"
						+ username + "', '" + password + "', '" + name + "', '" + surname + "', '" + role + "', '"
						+ phone + "', '" + email + "', '" + address + "');";
			else
				query = "INSERT INTO SimpleStaff (SimpleStaffID, Password, Name, Surname, StaffType, Phone, Email, Address) VALUES ('"
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
			String query = "INSERT INTO Patient (PatientID, Password, Name, Surname, Phone, Email, Address) VALUES ('"+ username + "', '" + password + "', '" + name + "', '" + surname + "', '"+ phone + "', '" + email + "', '" + address + "');";
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.print("Got error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
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

	public ResultSet login(String Username, String Password, String Role) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (Role.equals("Doctor") || Role.equals("Nurse") || Role.equals("Health Visitor"))
				query = "SELECT * FROM ClinicalStaff WHERE ClinicalStaffID='" + Username + "' AND Password='" + Password
						+ "' AND StaffType='" + Role + "';";
			else if (Role.equals("Patient"))
				query = "SELECT * FROM Patient WHERE PatientID='" + Username + "' AND Password='" + Password + "';";
			else
				query = "SELECT * FROM SimpleStaff WHERE SimpleStaffID='" + Username + "' AND Password='" + Password
						+ "' AND StaffType='" + Role + "';";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void changepassword(String username, String newpassword, String role) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (role.equals("Doctor") || role.equals("Nurse") || role.equals("Health Visitor"))
				query = "UPDATE ClinicalStaff SET Password='" + newpassword + "' WHERE ClinicalStaffID='" + username + "';";
			else if (role.equals("Receptionist") || role.equals("Medical Records Staff"))
				query = "UPDATE SimpleStaff SET Password='" + newpassword + "' WHERE SimpleStaffID='" + username + "';";
			else
				query = "UPDATE Patient SET Password='" + newpassword + "' WHERE PatientID='" + username + "';";
				stmt.executeUpdate(query);
		} catch (SQLException e) {
			// System.out.println(e.getMessage());
		}
	}

	public ResultSet checksignup(String Username, String Role) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (Role.equals("Doctor") || Role.equals("Nurse") || Role.equals("Health Visitor"))
				query = "SELECT ClinicalStaffID FROM ClinicalStaff WHERE ClinicalStaffID='" + Username + "';";
			else
				query = "SELECT SimpleStaffID FROM SimpleStaff WHERE SimpleStaffID='" + Username + "';";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
