package client;

import java.net.Socket;

import javax.swing.JFrame;

import server.JDBC;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class PsychiatryClient {
	
	private BufferedReader in;
    private PrintWriter out;
    private static GUI g=new GUI();
    
	
	public void connectToServer() throws IOException {

        // Make connection and initialize streams
        Socket socket = new Socket("localhost", 8080);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        
        out.println("ime o client kai stelnw ston Server");
        String messageFromServer=null;
        if((messageFromServer = in.readLine())!=null){
	    System.out.println("Message from server : " + messageFromServer);
        }
        
	    socket.close();
	   
    }
	public static void main(String[] args) throws IOException {
		PsychiatryClient c= new PsychiatryClient();
		c.connectToServer();
		g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		g.contentPane.setLayout(null);
		g.setLocationByPlatform(true);
		g.setContentPane(g.contentPane);
		g.getContentPane().add(g.loginForm());
		g.getContentPane().add(g.signupForm());
		g.pack();
		g.setVisible(true);
		g.SADB = new JDBC();
		g.SADB.conn = g.SADB.getDBConnection();
		if (g.SADB.conn == null) {
			return;
		}
		System.out.println("WELCOME TO Regional Health Authority JDBC program ! \n\n");
		
	}
	// Testing for github
	//new comment for test
	
}
