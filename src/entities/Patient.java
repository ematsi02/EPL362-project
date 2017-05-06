package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Patient implements java.io.Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String PatientID;
	public String Password;
	public String Name;
	public String Surname;
	public int Phone;
	public String Email;
	public String Address;
	public int NumOfIncidents;
	public boolean SelfHarmRisk;
	public boolean OthersHarmRisk;
	public String RiskStatus;
	public boolean ChangedByPatient;
	public boolean DeadReadOnly;
	public static ArrayList<String> columnNames = fillColumnNames();
	
	public Patient() {
		new Patient(null, null, null, null, 0, null, null, 0, false, false, null, false, false);
	}
	
	public Patient(String PatientID, String Password, String Name, String Surname, int Phone, String Email,
			String Address, int NumOfIncidents, boolean SelfHarmRisk, boolean OthersHarmRisk, String RiskStatus,
			boolean ChangedByPatient, boolean DeadReadOnly) {
		this.PatientID = PatientID;
		this.Password = Password;
		this.Name = Name;
		this.Surname = Surname;
		this.Phone = Phone;
		this.Email = Email;
		this.Address = Address;
		this.NumOfIncidents = NumOfIncidents;
		this.SelfHarmRisk = SelfHarmRisk;
		this.OthersHarmRisk = OthersHarmRisk;
		this.RiskStatus = RiskStatus;
		this.ChangedByPatient = ChangedByPatient;
		this.DeadReadOnly = DeadReadOnly;
		this.fillColumnNames();
	}
	
	public List<Patient> convertRsToList(ResultSet rs) throws SQLException{
		List<Patient> Patients=new ArrayList<Patient>();
		while(rs.next()) {
			Patient patient=new Patient();
			patient.PatientID=rs.getString("PatientID");
		   patient.Password=rs.getString("Password");
		   patient.Name=rs.getString("Name");
		   patient.Surname=rs.getString("Surname");
		   patient.Phone=rs.getInt("Phone");
		   patient.Email=rs.getString("Email");
		   patient.Address=rs.getString("Address");
		   patient.NumOfIncidents=rs.getInt("NumOfIncidents");
		   patient.SelfHarmRisk=rs.getBoolean("SelfHarmRisk");
		   patient.OthersHarmRisk=rs.getBoolean("OthersHarmRisk");
		   patient.RiskStatus=rs.getString("RiskStatus");
		   patient.ChangedByPatient=rs.getBoolean("ChangedByPatient");
		   patient.DeadReadOnly=rs.getBoolean("DeadReadOnly");

		  Patients.add(patient);
		} 
		return Patients;
	}
	
	private static ArrayList<String> fillColumnNames(){
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("PatientID");
		columnNames.add("Password");
		columnNames.add("Name");
		columnNames.add("Surname");
		columnNames.add("Phone");
		columnNames.add("Email");
		columnNames.add("Address");
		columnNames.add("NumOfIncidents");
		columnNames.add("SelfHarmRisk");
		columnNames.add("OthersHarmRisk");
		columnNames.add("RiskStatus");
		columnNames.add("ChangedByPatient");
		columnNames.add("DeadReadOnly");
		return columnNames;
	}
}
