package entities;

import java.util.ArrayList;

public class Patient  implements java.io.Serializable {

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
