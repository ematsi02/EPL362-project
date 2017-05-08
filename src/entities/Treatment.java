package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Treatment implements java.io.Serializable   {
	

	private static final long serialVersionUID = 1L;
	public int TreatmentID;
	public String PatientID;
	public String StartDate;
	public String EndDate;
	public String Diagnosis;
	public String Description;
	public String StaffID;

	public Treatment() {
		new Treatment(0, null, null, null, null, null,null);
	}

	public Treatment(int treatmentID, String patientID, String startDate, String endDate, String diagnosis, 
			String description,String staffID) {
		this.TreatmentID = treatmentID;
		this.PatientID = patientID;
		this.StartDate = startDate;
		this.EndDate = endDate;
		this.Diagnosis = diagnosis;
		this.Description = description;
		this.StaffID = staffID;
	}
	public List<Treatment> convertRsToList(ResultSet rs) throws SQLException{
		List<Treatment> Treatment=new ArrayList<Treatment>();
		while(rs.next()) {
			Treatment treatment=new Treatment();
			treatment.TreatmentID=rs.getInt("TreatmentID");
			treatment.PatientID=rs.getString("PatientID");
			treatment.StartDate=rs.getString("StartDate");
			treatment.EndDate=rs.getString("EndDate");
			treatment.Diagnosis=rs.getString("Diagnosis");
			treatment.Description=rs.getString("Description");
			treatment.StaffID=rs.getString("StaffID");
			
			Treatment.add(treatment);
		} 
		return Treatment;
	}

}
