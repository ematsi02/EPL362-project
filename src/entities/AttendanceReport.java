package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class AttendanceReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public int PatientsCount;
	public String Date;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return Integer.toString(PatientsCount);
		default:
			return Date;
		}
	}

	public static DefaultTableModel buildTableModel(List<AttendanceReport> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			AttendanceReport t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}
	
	public List<AttendanceReport> convertRsToList(ResultSet rs) throws SQLException {
		List<AttendanceReport> MedReport = new ArrayList<AttendanceReport>();
		while (rs.next()) {
			AttendanceReport nr = new AttendanceReport();
			nr.PatientsCount = rs.getInt("COUNT(PatientID)");
			nr.Date = rs.getString("Date");

			MedReport.add(nr);
		}
		return MedReport;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("PatientsCount");
		columnNames.add("Date");
		return columnNames;
	}
}
