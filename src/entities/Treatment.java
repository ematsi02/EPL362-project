package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Treatment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public int TreatmentID;
	public String PatientID;
	public String StartDate;
	public String EndDate;
	public String Diagnosis;
	public String Description;
	public String StaffID;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return Integer.toString(TreatmentID);
		case 1:
			return PatientID;
		case 2:
			return StartDate;
		case 3:
			return EndDate;
		case 4:
			return Diagnosis;
		case 5:
			return Description;
		default:
			return StaffID;
		}
	}

	public static DefaultTableModel buildTableModel(List<Treatment> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			Treatment t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}

	public List<Treatment> convertRsToList(ResultSet rs) throws SQLException {
		List<Treatment> Treatment = new ArrayList<Treatment>();
		while (rs.next()) {
			Treatment treatment = new Treatment();
			treatment.TreatmentID = rs.getInt("TreatmentID");
			treatment.PatientID = rs.getString("PatientID");
			treatment.StartDate = rs.getString("StartDate");
			treatment.EndDate = rs.getString("EndDate");
			treatment.Diagnosis = rs.getString("Diagnosis");
			treatment.Description = rs.getString("Description");
			treatment.StaffID = rs.getString("StaffID");
			Treatment.add(treatment);
		}
		return Treatment;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("TreatmentID");
		columnNames.add("PatientID");
		columnNames.add("StartDate");
		columnNames.add("EndDate");
		columnNames.add("Diagnosis");
		columnNames.add("Description");
		columnNames.add("StaffID");
		return columnNames;
	}
}
