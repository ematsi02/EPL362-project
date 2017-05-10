package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Medication implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int MedicationID;
	public String Brand;
	public String Name;
	public String Description;
	public String KnownSideEffects;
	public int MaxDose;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return Integer.toString(MedicationID);
		case 1:
			return Brand;
		case 2:
			return Name;
		case 3:
			return Description;
		case 4:
			return KnownSideEffects;
		default:
			return Integer.toString(MaxDose);
		}
	}

	public static DefaultTableModel buildTableModel(List<Medication> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			Medication t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}

	public List<Medication> convertRsToList(ResultSet rs) throws SQLException {
		List<Medication> Medication = new ArrayList<Medication>();
		while (rs.next()) {
			Medication medication = new Medication();
			medication.MedicationID = rs.getInt("MedicationID");
			medication.Brand = rs.getString("Brand");
			medication.Name = rs.getString("Name");
			medication.Description = rs.getString("Description");
			medication.KnownSideEffects = rs.getString("KnownSideEffects");
			medication.MaxDose = rs.getInt("MaxDose");

			Medication.add(medication);
		}
		return Medication;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("MedicationID");
		columnNames.add("Brand");
		columnNames.add("Name");
		columnNames.add("Description");
		columnNames.add("KnownSideEffects");
		columnNames.add("MaxDose");
		return columnNames;
	}
}
