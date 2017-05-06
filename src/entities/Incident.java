package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Incident implements java.io.Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int IncidentID;
	public String PatientID;
	public String IncidentType;
	public String ShortDescription;
	public String Description;
	public String Date;

	public Incident() {
		new Incident(0, null, null, null, null, null);
	}

	public Incident(int incidentID, String patientID, String incidentType, String shortDescription, String description,
			String date) {
		this.IncidentID = incidentID;
		this.PatientID = patientID;
		this.IncidentType = incidentType;
		this.ShortDescription = shortDescription;
		this.Description = description;
		this.Date = date;
	}
	public List<Incident> convertRsToList(ResultSet rs) throws SQLException{
		List<Incident> Incident=new ArrayList<Incident>();
		while(rs.next()) {
			Incident incident=new Incident();
			incident.IncidentID=rs.getInt("IncidentID");
			incident.PatientID=rs.getString("PatientID");
			incident.IncidentType=rs.getString("IncidentType");
			incident.ShortDescription=rs.getString("ShortDescription");
			incident.Description=rs.getString("Description");
			incident.Date=rs.getString("Date");
			
			Incident.add(incident);
		} 
		return Incident;
	}
}
