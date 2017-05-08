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
	public static ArrayList<String> columnNames = fillColumnNames();

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
	
	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("IncidentID");
		columnNames.add("PatientID");
		columnNames.add("IncidentType");
		columnNames.add("ShortDescription");
		columnNames.add("Description");
		columnNames.add("Date");
		return columnNames;
	}
}
