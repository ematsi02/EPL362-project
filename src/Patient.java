package entities;

public class Patient {

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
	}
}
