package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Staff implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String StaffID;
	public String Password;
	public String StaffType;
	public String Name;
	public String Surname;
	public int Phone;
	public String Email;
	public String Address;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return StaffID;
		case 1:
			return Password;
		case 2:
			return StaffType;
		case 3:
			return Name;
		case 4:
			return Surname;
		case 5:
			return Integer.toString(Phone);
		case 6:
			return Email;
		default:
			return Address;
		}
	}

	public static DefaultTableModel buildTableModel(List<Staff> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			Staff t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}

	public List<Staff> convertRsToList(ResultSet rs) throws SQLException {
		List<Staff> Patients = new ArrayList<Staff>();
		while (rs.next()) {
			Staff patient = new Staff();
			patient.StaffID = rs.getString("StaffID");
			patient.Password = rs.getString("Password");
			patient.StaffType = rs.getString("StaffType");
			patient.Name = rs.getString("Name");
			patient.Surname = rs.getString("Surname");
			patient.Phone = rs.getInt("Phone");
			patient.Email = rs.getString("Email");
			patient.Address = rs.getString("Address");

			Patients.add(patient);
		}
		return Patients;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("StaffID");
		columnNames.add("Password");
		columnNames.add("StaffType");
		columnNames.add("Name");
		columnNames.add("Surname");
		columnNames.add("Phone");
		columnNames.add("Email");
		columnNames.add("Address");
		return columnNames;
	}
}
