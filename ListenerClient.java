import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ListenerClient extends Thread{
	Socket s;
	JTextArea conversation;
	JTextArea status;
	
	
	public ListenerClient(Socket s, JTextArea conversation, JTextArea status){
		this.s = s;
		this.conversation = conversation;
		this.status = status;
		start();
	}

	public void run(){
		try{
				MyConnection conn = new MyConnection(s);
				
				String msgIn = "", msgOut = "";		
				while(msgIn != null && msgIn.equals("GOODBYE") == false){
					msgIn = conn.getMessage();
					if(msgIn != null && msgIn.equals("***CLEAR STATS***") == true){
						status.setText("");
					}
					else if(msgIn != null && msgIn.length() > 17 && msgIn.substring(0,18).equals("***ONLINE USERS***") == true){
						status.append(msgIn.substring(18,msgIn.length()) + "\n");
					}
					
					else if(msgIn != null) conversation.append(msgIn + "\n");
				}
				if(msgIn.equals("GOODBYE") == true) System.exit(0);
				
				
		}catch(Exception e){e.printStackTrace();}
	}

}