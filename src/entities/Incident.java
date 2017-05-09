package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Incident implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int IncidentID;
	public String PatientID;
	public String IncidentType;
	public String ShortDescription;
	public String Description;
	public String Date;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return Integer.toString(IncidentID);
		case 1:
			return PatientID;
		case 2:
			return IncidentType;
		case 3:
			return ShortDescription;
		case 4:
			return Description;
		default:
			return Date;
		}
	}

	public static DefaultTableModel buildTableModel(List<Incident> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			Incident t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}

	public List<Incident> convertRsToList(ResultSet rs) throws SQLException {
		List<Incident> Incident = new ArrayList<Incident>();
		while (rs.next()) {
			Incident incident = new Incident();
			incident.IncidentID = rs.getInt("IncidentID");
			incident.PatientID = rs.getString("PatientID");
			incident.IncidentType = rs.getString("IncidentType");
			incident.ShortDescription = rs.getString("ShortDescription");
			incident.Description = rs.getString("Description");
			incident.Date = rs.getString("Date");

			Incident.add(incident);
		}
		return Incident;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("IncidentID");
		columnNames.add("PatientID");
		columnNames.add("IncidentType");
		columnNames.add("ShortDescription");
		columnNames.add("Description");
		columnNames.add("Date");
		return columnNames;
	}
}
