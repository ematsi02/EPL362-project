package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 * 
 * This object is used for the results of a General Report, the Medication
 * Prescriptions Summary. This summary contains fileds of three entities:
 * Treatment, TreatmentMedication, Medication.
 * 
 * @author erasmia
 *
 */
public class MedicationPrescription implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Treatment Fields
	public int TreatmentID;
	public String PatientID;
	public String StartDate;
	public String EndDate;
	public String Diagnosis;
	public String Description;
	public String StaffID;
	// TreatmentMedication fields
	public int MTreatmentID;
	public int TMedicationID;
	public int Dose;
	public String DoseDescription;
	public int OverruledWarning;
	public String WarningMessage;
	// Medication fields
	public int MedicationID;
	public String Brand;
	public String Name;
	public String KnownSideEffects;
	public int MaxDose;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		// Treatment Fields
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
		case 6:
			return StaffID;
		// TreatmentMedication fields
		case 7:
			return Integer.toString(MTreatmentID);
		case 8:
			return Integer.toString(TMedicationID);
		case 9:
			return Integer.toString(Dose);
		case 10:
			return DoseDescription;
		case 11:
			return Integer.toString(OverruledWarning);
		case 12:
			return WarningMessage;
		// Medication fields
		case 13:
			return Integer.toString(MedicationID);
		case 14:
			return Brand;
		case 15:
			return Name;
		case 16:
			return KnownSideEffects;
		default:
			return Integer.toString(MaxDose);
		}
	}

	public static DefaultTableModel buildTableModel(List<MedicationPrescription> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			MedicationPrescription t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}

	public List<MedicationPrescription> convertRsToList(ResultSet rs) throws SQLException {
		List<MedicationPrescription> medPresc = new ArrayList<MedicationPrescription>();
		while (rs.next()) {
			MedicationPrescription mp = new MedicationPrescription();
			// Treatment fields
			mp.TreatmentID = rs.getInt("TreatmentID");
			mp.PatientID = rs.getString("PatientID");
			mp.StartDate = rs.getString("StartDate");
			mp.EndDate = rs.getString("EndDate");
			mp.Diagnosis = rs.getString("Diagnosis");
			mp.Description = rs.getString("Treatment.Description");
			mp.StaffID = rs.getString("StaffID");
			// TreatmentMedication fields
			mp.MTreatmentID = rs.getInt("TreatmentID");
			mp.TMedicationID = rs.getInt("MedicationID");
			mp.Dose = rs.getInt("Dose");
			mp.DoseDescription = rs.getString("DoseDescription");
			mp.OverruledWarning = rs.getInt("OverruledWarning");
			mp.WarningMessage = rs.getString("WarningMessage");
			// Medication fields
			mp.MedicationID = rs.getInt("MedicationID");
			mp.Brand = rs.getString("Brand");
			mp.Name = rs.getString("Name");
			mp.KnownSideEffects = rs.getString("KnownSideEffects");
			mp.MaxDose = rs.getInt("MaxDose");

			medPresc.add(mp);
		}
		return medPresc;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		// Treatment fields
		columnNames.add("TreatmentID");
		columnNames.add("PatientID");
		columnNames.add("StartDate");
		columnNames.add("EndDate");
		columnNames.add("Diagnosis");
		columnNames.add("TDescription");
		columnNames.add("StaffID");
		// TreatmentMedication fields
		columnNames.add("MTreatmentID");
		columnNames.add("TMedicationID");
		columnNames.add("Dose");
		columnNames.add("DoseDescription");
		columnNames.add("OverruledWarning");
		columnNames.add("WarningMessage");
		// Medication fields
		columnNames.add("MedicationID");
		columnNames.add("Brand");
		columnNames.add("Name");
		columnNames.add("KnownSideEffects");
		columnNames.add("MaxDose");

		return columnNames;
	}

}
