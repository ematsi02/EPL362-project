package code;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class PsychiatryServer {

	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(8080);
		try {
			while (true) {
				Socket socket = listener.accept();//accept request
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);
				try {
					// edw prepei na grapsoume ton kwdika
				} finally {
					socket.close();
				}
			}
		} finally {
			listener.close();
		}

	}

}


