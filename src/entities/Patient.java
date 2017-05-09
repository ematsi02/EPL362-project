package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Patient implements java.io.Serializable {

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
	public int SelfHarmRisk;
	public int OthersHarmRisk;
	public String RiskStatus;
	public int ChangedByPatient;
	public int DeadReadOnly;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return PatientID;
		case 1:
			return Password;
		case 2:
			return Name;
		case 3:
			return Surname;
		case 4:
			return Integer.toString(Phone);
		case 5:
			return Email;
		case 6:
			return Address;
		case 7:
			return Integer.toString(SelfHarmRisk);
		case 8:
			return Integer.toString(OthersHarmRisk);
		case 9:
			return RiskStatus;
		case 10:
			return Integer.toString(ChangedByPatient);
		default:
			return Integer.toString(DeadReadOnly);
		}
	}

	public static DefaultTableModel buildTableModel(List<Patient> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			Patient t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}

	public List<Patient> convertRsToList(ResultSet rs) throws SQLException {
		List<Patient> Patients = new ArrayList<Patient>();
		while (rs.next()) {
			Patient patient = new Patient();
			patient.PatientID = rs.getString("PatientID");
			patient.Password = rs.getString("Password");
			patient.Name = rs.getString("Name");
			patient.Surname = rs.getString("Surname");
			patient.Phone = rs.getInt("Phone");
			patient.Email = rs.getString("Email");
			patient.Address = rs.getString("Address");
			patient.NumOfIncidents = rs.getInt("NumOfIncidents");
			patient.SelfHarmRisk = rs.getInt("SelfHarmRisk");
			patient.OthersHarmRisk = rs.getInt("OthersHarmRisk");
			patient.RiskStatus = rs.getString("RiskStatus");
			patient.ChangedByPatient = rs.getInt("ChangedByPatient");
			patient.DeadReadOnly = rs.getInt("DeadReadOnly");

			Patients.add(patient);
		}
		return Patients;
	}

	private static ArrayList<String> fillColumnNames() {
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
