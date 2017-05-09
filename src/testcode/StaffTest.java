package testcode;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import entities.Staff;

public class StaffTest {
	Staff myStaff;
	ArrayList<String> columns;

	@Before
	public void setUp() throws Exception {
		myStaff = new Staff();
		myStaff.StaffID = "myID";
		myStaff.Password = "123456";
		myStaff.StaffType = "Reception";
		myStaff.Name = "name";
		myStaff.Surname = "surname";
		myStaff.Phone = 99334455;
		myStaff.Email = "myEmail@one.com";
		myStaff.Address = "my Address";

		columns = new ArrayList<String>();
		columns.add("StaffID");
		columns.add("Password");
		columns.add("StaffType");
		columns.add("Name");
		columns.add("Surname");
		columns.add("Phone");
		columns.add("Email");
		columns.add("Address");
	}

	@Test
	public void testFieldValues() {
		assertEquals(myStaff.StaffID, "myID");
		assertEquals(myStaff.Password, "123456");
		assertEquals(myStaff.StaffType, "Reception");
		assertEquals(myStaff.Name, "name");
		assertEquals(myStaff.Surname, "surname");
		assertEquals(myStaff.Phone, 99334455);
		assertEquals(myStaff.Email, "myEmail@one.com");
		assertEquals(myStaff.Address, "my Address");
	}

	@Test
	public void testGetField() {
		assertEquals(myStaff.getfield(0), "myID");
		assertEquals(myStaff.getfield(1), "123456");
		assertEquals(myStaff.getfield(2), "Reception");
		assertEquals(myStaff.getfield(3), "name");
		assertEquals(myStaff.getfield(4), "surname");
		assertEquals(myStaff.getfield(5), "99334455");
		assertEquals(myStaff.getfield(6), "myEmail@one.com");
		assertEquals(myStaff.getfield(7), "my Address");
	}

	@Test
	public void testChange() {
		myStaff.Phone = 99123123;
		assertEquals(myStaff.getfield(5), "99123123");
	}

	@Test
	public void testFillColumnNames() {
		assertEquals(Staff.columnNames, columns);
	}

}
