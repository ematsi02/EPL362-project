package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Comment implements java.io.Serializable   {
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

	public List<Comment> convertRsToList(ResultSet rs) throws SQLException{
		List<Comment> Comment=new ArrayList<Comment>();
		while(rs.next()) {
			Comment comment=new Comment();
			comment.CommentID=rs.getInt("CommentID");
			comment.PatientID=rs.getString("PatientID");
			comment.StaffID=rs.getString("StaffID");
			comment.Subject=rs.getString("Subject");
			comment.Comment=rs.getString("Comment");
			
			Comment.add(comment);
		} 
		return Comment;
	}
	
	private static ArrayList<String> fillColumnNames(){
		ArrayList<String> columnNames = new ArrayList<String>();
		columnNames.add("CommentID");
		columnNames.add("PatientID");
		columnNames.add("StaffID");
		columnNames.add("Subject");
		columnNames.add("Comment");		
		return columnNames;
	}
}
