package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicationReaction implements java.io.Serializable   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String PatientID;
	public int MedicationID;
	public String ReactionType;
	public String Description;

	public MedicationReaction() {
		new MedicationReaction(null, 0, null, null);
	}

	public MedicationReaction(String patientID, int medicationID, String reactionType, String description) {
		this.PatientID = patientID;
		this.MedicationID = medicationID;
		this.ReactionType = reactionType;
		this.Description = description;
	}
	public List<MedicationReaction> convertRsToList(ResultSet rs) throws SQLException{
		List<MedicationReaction> MedicationReaction=new ArrayList<MedicationReaction>();
		while(rs.next()) {
			MedicationReaction medicationReaction=new MedicationReaction();
			medicationReaction.PatientID=rs.getString("PatientID");
			medicationReaction.MedicationID=rs.getInt("MedicationID");
			medicationReaction.ReactionType=rs.getString("ReactionType");
			medicationReaction.Description=rs.getString("Description");
			
			MedicationReaction.add(medicationReaction);
		} 
		return MedicationReaction;
	}
}
