package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Consultation implements java.io.Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int ConsultationID;
	public String PatientID;
	public String StaffID;
	public String Subject;
	public String DateBooked;
	public String Date;
	public String Time;
	public int Attended;
	public int MedicalRecordUpdated;
	public int TreatmentID;

	public Consultation() {
		new Consultation(0, null, null, null, null, null, null, 0, 0, 0);
	}

	public Consultation(int consultationID, String patientID, String staffID, String subject, String dateBooked,
			String date, String time, int attended, int medicalRecordUpdated, int treatmentID) {
		this.ConsultationID = consultationID;
		this.PatientID = patientID;
		this.StaffID = staffID;
		this.Subject = subject;
		this.DateBooked = dateBooked;
		this.Date = date;
		this.Time = time;
		this.Attended = attended;
		this.MedicalRecordUpdated = medicalRecordUpdated;
		this.TreatmentID = treatmentID;
	}
	public List<Consultation> convertRsToList(ResultSet rs) throws SQLException{
		List<Consultation> Consultation=new ArrayList<Consultation>();
		while(rs.next()) {
			Consultation consultation=new Consultation();
			consultation.ConsultationID=rs.getInt("ConsultationID");
			consultation.PatientID=rs.getString("PatientID");
			consultation.StaffID=rs.getString("StaffID");
			consultation.Subject=rs.getString("Subject");
			consultation.DateBooked=rs.getString("DateBooked");
			consultation.Date=rs.getString("Date");
			consultation.Time=rs.getString("Time");
			consultation.Attended=rs.getInt("Attended");
			consultation.MedicalRecordUpdated=rs.getInt("MedicalRecordUpdated");
			consultation.TreatmentID=rs.getInt("TreatmentID");
			
			Consultation.add(consultation);
		} 
		return Consultation;
	}
}
