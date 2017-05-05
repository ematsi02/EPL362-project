package entities;

public class Relative implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;
	public int RelativeID;
	public String PatientID;
	public String Name;
	public String Surname;
	public int Phone;
	public String Email;
	public String Address;
	public String Relationship;
	
	public Relative() {
		new Relative(0,null, null, null, 0, null, null, null);
	}

	public Relative(int relativeID,String patientID,String name,String surname,int phone,String email,String address,String relatioship){
		this.RelativeID = relativeID;
		this.PatientID = patientID;
		this.Name=name;
		this.Surname=surname;
		this.Phone=phone;
		this.Email=email;
		this.Address=address;
		this.Relationship=relatioship;	
	}

}
