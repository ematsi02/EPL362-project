package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This object is used for the results of the Consultation Reports (Attended
 * Daily Consultations, Attended General Consultations, Non Attended Daily
 * Consultations, Non Attended General Consultations, Non Updated Daily Medical
 * Records). These reports have the same information returned/shown, so we can
 * use the same object for their results.
 * 
 * @author erasmia
 *
 */
public class ConsultationReport implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	public int Consultation_ConsultationID;
	public String Consultation_Subject;
	public String Consultation_Date;
	public String Consultation_Time;
	public int Patient_PatientID;
	public String Patient_Name;
	public String Patient_Surname;
	public int Staff_StaffID;
	public String Staff_Name;
	public String Staff_Surname;
	public int Consultation_MedicalRecordUpdated;
	public static ArrayList<String> columnNames = fillColumnNames();

	public List<ConsultationReport> convertRsToList(ResultSet rs) throws SQLException {
		List<ConsultationReport> ReportList = new ArrayList<ConsultationReport>();
		while (rs.next()) {
			ConsultationReport cr = new ConsultationReport();
			cr.Consultation_ConsultationID = rs.getInt("ConsultationID");
			cr.Consultation_Subject = rs.getString("Subject");
			cr.Consultation_Date = rs.getString("Date");
			cr.Consultation_Time = rs.getString("Time");
			cr.Patient_PatientID = rs.getInt("PatientID");
			cr.Patient_Name = rs.getString("Name");
			cr.Patient_Surname = rs.getString("Surname");
			cr.Staff_StaffID = rs.getInt("StaffID");
			cr.Staff_Name = rs.getString("Name");
			cr.Staff_Surname = rs.getString("Surname");
			cr.Consultation_MedicalRecordUpdated = rs.getInt("MedicalRecordUpdated");

			ReportList.add(cr);
		}
		return ReportList;
	}
	
	public String getfield(int i){
		switch (i){
		case 0: return ((Integer)Consultation_ConsultationID).toString();
		case 1: return Consultation_Date;
		// TODO continue with the rest columns
		}
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("ConsultationID");
		columnNames.add("Consultation_Subject");
		columnNames.add("Consultation_Date");
		columnNames.add("Consultation_Time");
		columnNames.add("Patient_PatientID");
		columnNames.add("Patient_Name");
		columnNames.add("Patient_Surname");
		columnNames.add("Staff_StaffID");
		columnNames.add("Staff_Name");
		columnNames.add("Staff_Surname");
		columnNames.add("Consultation_MedicalRecordUpdated");
		return columnNames;
	}
}
