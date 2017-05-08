package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Medication implements java.io.Serializable   {
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

	public Medication() {
		new Medication(0, null, null, null, null, 0);
	}

	public Medication(int medicationID, String brand, String name, String description, String knownSideEffects,
			int maxDose) {
		this.MedicationID = medicationID;
		this.Brand = brand;
		this.Name = name;
		this.Description = description;
		this.KnownSideEffects = knownSideEffects;
		this.MaxDose = maxDose;
	}
	public List<Medication> convertRsToList(ResultSet rs) throws SQLException{
		List<Medication> Medication=new ArrayList<Medication>();
		while(rs.next()) {
			Medication medication=new Medication();
			medication.MedicationID=rs.getInt("MedicationID");
			medication.Brand=rs.getString("Brand");
			medication.Name=rs.getString("Name");
			medication.Description=rs.getString("Description");
			medication.KnownSideEffects=rs.getString("KnownSideEffects");
			medication.MaxDose=rs.getInt("MaxDose");
			
			Medication.add(medication);
		} 
		return Medication;
	}
	
	private static ArrayList<String> fillColumnNames(){
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
