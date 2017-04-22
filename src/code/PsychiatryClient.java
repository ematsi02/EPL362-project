package code;

import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class PsychiatryClient {
	
	private BufferedReader in;
    private PrintWriter out;
    
	
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
		
	}
	// Testing for github
	//new comment for test
	
}
