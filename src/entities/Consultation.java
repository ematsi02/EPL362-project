package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Consultation implements java.io.Serializable   {

	private static final long serialVersionUID = 1L;
	public int ConsultationID;
	public String PatientID;
	public String StaffID;
	public String Subject;
	public String DateBooked;
	public String Date;
	public String Time;
	public int Attended;
	public int MedicalRecordUpdated;
	public int TreatmentID;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return Integer.toString(ConsultationID);
		case 1:
			return PatientID;
		case 2:
			return StaffID;
		case 3:
			return Subject;
		case 4:
			return DateBooked;
		case 5:
			return Date;
		case 6:
			return Time;
		case 7:
			return Integer.toString(Attended);
		case 8:
			return Integer.toString(MedicalRecordUpdated);
		default:
			return Integer.toString(TreatmentID);
		}
	}

	public static DefaultTableModel buildTableModel(List<Consultation> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			Consultation t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}
	
	public List<Consultation> convertRsToList(ResultSet rs) throws SQLException{
		List<Consultation> Consultation=new ArrayList<Consultation>();
		while(rs.next()) {
			Consultation consultation=new Consultation();
			consultation.ConsultationID=rs.getInt("ConsultationID");
			consultation.PatientID=rs.getString("PatientID");
			consultation.StaffID=rs.getString("StaffID");
			consultation.Subject=rs.getString("Subject");
			consultation.DateBooked=rs.getString("DateBooked");
			consultation.Date=rs.getString("Date");
			consultation.Time=rs.getString("Time");
			consultation.Attended=rs.getInt("Attended");
			consultation.MedicalRecordUpdated=rs.getInt("MedicalRecordUpdated");
			consultation.TreatmentID=rs.getInt("TreatmentID");
			
			Consultation.add(consultation);
		} 
		return Consultation;
	}
	
	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("ConsultationID");
		columnNames.add("PatientID");
		columnNames.add("StaffID");
		columnNames.add("Subject");
		columnNames.add("DateBooked");
		columnNames.add("Date");
		columnNames.add("Time");
		columnNames.add("Attended");
		columnNames.add("MedicalRecordUpdated");
		columnNames.add("TreatmentID");
		return columnNames;
	}
}
