package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class MedicationReaction implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String PatientID;
	public int MedicationID;
	public String ReactionType;
	public String Description;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return PatientID;
		case 1:
			return Integer.toString(MedicationID);
		case 2:
			return ReactionType;
		default:
			return Description;
		}
	}

	public static DefaultTableModel buildTableModel(List<MedicationReaction> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			MedicationReaction t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}

	public List<MedicationReaction> convertRsToList(ResultSet rs) throws SQLException {
		List<MedicationReaction> MedicationReaction = new ArrayList<MedicationReaction>();
		while (rs.next()) {
			MedicationReaction medicationReaction = new MedicationReaction();
			medicationReaction.PatientID = rs.getString("PatientID");
			medicationReaction.MedicationID = rs.getInt("MedicationID");
			medicationReaction.ReactionType = rs.getString("ReactionType");
			medicationReaction.Description = rs.getString("Description");

			MedicationReaction.add(medicationReaction);
		}
		return MedicationReaction;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("PatientID");
		columnNames.add("MedicationID");
		columnNames.add("ReactionType");
		columnNames.add("Description");
		return columnNames;
	}
}
