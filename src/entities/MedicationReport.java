package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class MedicationReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public int PatientsCount;
	public int MedicationID;
	public String MedicationName;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return Integer.toString(PatientsCount);
		case 1:
			return Integer.toString(MedicationID);
		default:
			return MedicationName;
		}
	}

	public static DefaultTableModel buildTableModel(List<MedicationReport> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			MedicationReport t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}
	
	public List<MedicationReport> convertRsToList(ResultSet rs) throws SQLException {
		List<MedicationReport> MedReport = new ArrayList<MedicationReport>();
		while (rs.next()) {
			MedicationReport mr = new MedicationReport();
			mr.PatientsCount = rs.getInt("COUNT(Treatment.PatientID)");
			mr.MedicationID = rs.getInt("MedicationID");
			mr.MedicationName = rs.getString("Name");

			MedReport.add(mr);
		}
		return MedReport;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("PatientsCount");
		columnNames.add("MedicationID");
		columnNames.add("MedicationName");
		return columnNames;
	}
}
