package testcode;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import entities.Patient;

public class PatientTest {
	Patient myPatient;
	ArrayList<String> columns;

	@Before
	public void setUp() throws Exception {
		myPatient = new Patient();
		myPatient.PatientID = "myID";
		myPatient.Password = "123456";
		myPatient.Name = "name";
		myPatient.Surname = "surname";
		myPatient.Phone = 99334455;
		myPatient.Email = "myEmail@one.com";
		myPatient.Address = "my Address";
		myPatient.NumOfIncidents = 3;
		myPatient.SelfHarmRisk = 0;
		myPatient.OthersHarmRisk = 1;
		myPatient.RiskStatus = "This patient can be harmful to others.";
		myPatient.ChangedByPatient = 0;
		myPatient.DeadReadOnly = 0;

		columns = new ArrayList<String>();
		columns.add("PatientID");
		columns.add("Password");
		columns.add("Name");
		columns.add("Surname");
		columns.add("Phone");
		columns.add("Email");
		columns.add("Address");
		columns.add("NumOfIncidents");
		columns.add("SelfHarmRisk");
		columns.add("OthersHarmRisk");
		columns.add("RiskStatus");
		columns.add("ChangedByPatient");
		columns.add("DeadReadOnly");
	}

	@Test
	public void testFieldValues() {
		assertEquals(myPatient.PatientID, "myID");
		assertEquals(myPatient.Password, "123456");
		assertEquals(myPatient.Name, "name");
		assertEquals(myPatient.Surname, "surname");
		assertEquals(myPatient.Phone, 99334455);
		assertEquals(myPatient.Email, "myEmail@one.com");
		assertEquals(myPatient.Address, "my Address");
		assertEquals(myPatient.NumOfIncidents, 3);
		assertEquals(myPatient.SelfHarmRisk, 0);
	}

	@Test
	public void testGetField() {
		assertEquals(myPatient.getfield(0), "myID");
		assertEquals(myPatient.getfield(1), "123456");
		assertEquals(myPatient.getfield(2), "name");
		assertEquals(myPatient.getfield(3), "surname");
		assertEquals(myPatient.getfield(4), "99334455");
		assertEquals(myPatient.getfield(5), "myEmail@one.com");
		assertEquals(myPatient.getfield(6), "my Address");
		assertEquals(myPatient.getfield(7), "3");
		assertEquals(myPatient.getfield(8), "0");
	}

	@Test
	public void testChange() {
		myPatient.Phone = 99123123;
		assertEquals(myPatient.getfield(4), "99123123");
	}

	@Test
	public void testFillColumnNames() {
		assertEquals(Patient.columnNames, columns);
	}


}
