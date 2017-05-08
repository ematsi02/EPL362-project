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
	public static ArrayList<String> columnNames = fillColumnNames();

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

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("TreatmentID");
		columnNames.add("PatientID");
		columnNames.add("StartDate");
		columnNames.add("EndDate");
		columnNames.add("Diagnosis");
		columnNames.add("Description");
		columnNames.add("StaffID");
		return columnNames;
	}
}
