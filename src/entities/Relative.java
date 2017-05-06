package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Relative> convertRsToRelatList(ResultSet rs) throws SQLException{
		List<Relative> Relatives=new ArrayList<Relative>();
		while(rs.next()) {
			Relative relative=new Relative();
			relative.RelativeID=rs.getInt("RelativeID");
			relative.PatientID=rs.getString("PatientID");
		   relative.Name=rs.getString("Name");
		   relative.Surname=rs.getString("Surname");
		   relative.Phone=rs.getInt("Phone");
		   relative.Email=rs.getString("Email");
		   relative.Address=rs.getString("Address");
		   relative.Relationship=rs.getString("Relationship");


		  Relatives.add(relative);
		} 
		return Relatives;
	}

}
