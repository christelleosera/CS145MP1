import java.io.*;
import java.net.*;


public class MyServer {
	public static void main(String args[]) {
	int clientNum = 1;	
	MyConnection connArray[] = new MyConnection[100];	
	String usernames[] = new String[100];
	String stats[] = new String [100];
			
		try {
			System.out.println("Server: Starting...");
			ServerSocket ssocket = new ServerSocket(8888);

			System.out.println("Server: Waiting for connections...");
			
			while(clientNum <= 100){
				Socket socket = ssocket.accept(); // waiting
				System.out.print("Server: somebody connected!");
				System.out.println(socket.getInetAddress());
				new ThreadServer(socket, clientNum, connArray, usernames, stats);
				clientNum++;
			}
			
			

		} catch (Exception e){ e.printStackTrace(); }
	}
}

