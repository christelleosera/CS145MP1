import java.io.*;
import java.net.*;
import javax.swing.*;


public class SenderClient extends Thread{
	Socket s;
	JTextField outgoing;
	
	
	public SenderClient(Socket s, JTextField outgoing){
		this.s = s;
		this.outgoing = outgoing;
		start();
	}
	
	public void run(){
		try{
		MyConnection conn = new MyConnection(s);
		String serverReply = "";
		boolean check = true;
		String msg = outgoing.getText();
			
			check = true;
			check = conn.sendMessage(msg);
			if(!check) System.out.println("Error.");
			
			outgoing.setText("");
			outgoing.requestFocus();
			
		
		}catch(Exception e){e.printStackTrace();}
	}
	
}