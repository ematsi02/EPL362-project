package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class WarningLetter implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public String PatientID;
	public String Subject;
	public String Message;
	public int Informed;
	public String StaffID;
	public static ArrayList<String> columnNames = fillColumnNames();
	
	public String getfield(int i) {
		switch (i) {
		case 0:
			return PatientID;
		case 1:
			return Subject;
		case 2:
			return Message;
		case 3:
			return Integer.toString(Informed);
		default:
			return StaffID;
		}
	}

	public static DefaultTableModel buildTableModel(List<WarningLetter> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			WarningLetter t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}
	
	public List<WarningLetter> convertRsToList(ResultSet rs) throws SQLException {
		List<WarningLetter> wl = new ArrayList<WarningLetter>();
		while (rs.next()) {
			WarningLetter w = new WarningLetter();
			w.PatientID = rs.getString("PatientID");
			w.Subject = rs.getString("Subject");
			w.Message = rs.getString("Message");
			w.Informed = rs.getInt("Informed");
			w.StaffID = rs.getString("StaffID");

			wl.add(w);
		}
		return wl;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("PatientID");
		columnNames.add("Subject");
		columnNames.add("Message");
		columnNames.add("Informed");
		columnNames.add("StaffID");
		return columnNames;
	}
}