package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
