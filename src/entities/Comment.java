package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class Comment implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int CommentID;
	public String PatientID;
	public String StaffID;
	public String Subject;
	public String Comment;
	public static ArrayList<String> columnNames = fillColumnNames();

	public String getfield(int i) {
		switch (i) {
		case 0:
			return Integer.toString(CommentID);
		case 1:
			return PatientID;
		case 2:
			return StaffID;
		case 3:
			return Subject;
		default:
			return Comment;
		}
	}

	public static DefaultTableModel buildTableModel(List<Comment> list, ArrayList<String> columnNames)
			throws SQLException {
		int columnCount = columnNames.size();
		String[] columns = new String[columnCount];
		for (int column = 0; column < columnCount; column++) {
			columns[column] = columnNames.get(column);
		}
		int i = 0;
		String[][] data = new String[list.size()][columnCount];
		while (i < list.size()) {
			Comment t = list.get(i);
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				data[i][columnIndex] = t.getfield(columnIndex);
			}
			i++;
		}
		return new DefaultTableModel(data, columns);
	}

	public List<Comment> convertRsToList(ResultSet rs) throws SQLException {
		List<Comment> Comment = new ArrayList<Comment>();
		while (rs.next()) {
			Comment comment = new Comment();
			comment.CommentID = rs.getInt("CommentID");
			comment.PatientID = rs.getString("PatientID");
			comment.StaffID = rs.getString("StaffID");
			comment.Subject = rs.getString("Subject");
			comment.Comment = rs.getString("Comment");

			Comment.add(comment);
		}
		return Comment;
	}

	private static ArrayList<String> fillColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("CommentID");
		columnNames.add("PatientID");
		columnNames.add("StaffID");
		columnNames.add("Subject");
		columnNames.add("Comment");
		return columnNames;
	}
}
