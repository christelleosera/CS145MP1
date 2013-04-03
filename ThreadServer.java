import java.io.*;
import java.net.*;

public class ThreadServer extends Thread{
	int clientNum;
	Socket s;
	MyConnection connArray[];
	String usernames[];
	String stats[];
	
	
	public ThreadServer(Socket s, int clientNum, MyConnection connArray[], String usernames[], String stats[]){
		this.clientNum = clientNum;
		this.s = s;
		this.connArray = connArray;
		this.usernames = usernames;
		this.stats = stats;
		start();
	}
	
	public void run(){
		String clientName = "Client" + clientNum;
		String clientStatus = "";
		int i;
		
		connArray[clientNum] = new MyConnection(s);
		usernames[clientNum] = clientName;
		stats[clientNum] = clientStatus;
		
		
		String msgIn = "";
		String msgOut = "";
		int j;
		
		//new client connects
		System.out.println("Server message: "+ clientName + " has connected.");
		msgOut = "Server message: "+ clientName + " has connected.";
		String onlineUsers = "";
		String clrStats = "***CLEAR STATS***";
		boolean check = true;
		for(i=1;i<100;i++){
			
		
			if(connArray[i] != null && msgOut != null && i != clientNum){
					check = connArray[i].sendMessage(msgOut);
					if(!check) System.out.println("Error.");
			}	
			if(connArray[i] != null){
			check = connArray[i].sendMessage(clrStats);
			if(!check) System.out.println("Error.");
				for(j=1; j<100; j++){
						if(connArray[j] != null){
						onlineUsers = "***ONLINE USERS***";
						onlineUsers = onlineUsers + usernames[j];
						if(stats[j].equals("") == false){
						onlineUsers = onlineUsers + " - " + stats[j];
						}
						check = connArray[i].sendMessage(onlineUsers);
						if(!check) System.out.println("Error.");
					}
				}
			}
			
		}
		
		
		
		//get message
		msgIn = connArray[clientNum].getMessage();
		
			while (msgIn.equals("/quit") == false){

				if(msgIn.charAt(0) == '/'){

					if(msgIn.length() > 10 && msgIn.substring(0,11).equals("/changename") == true) {
					String name = ""; i = 12;
						while(i < msgIn.length() && msgIn.charAt(i) != ' ' && msgIn.charAt(i) != '\n' && msgIn.charAt(i) != '\0'){
							name = name + msgIn.charAt(i);
							i++;
						}
					msgOut = "Server message: " + clientName + " has changed name to " + name;
					//System.out.println("Server message: " + clientName + " has changed name to " + name);
					clientName = name;
					usernames[clientNum] = clientName;
					
						for(i=1;i<100;i++){
								if(connArray[i] != null){
									check = connArray[i].sendMessage(clrStats);
									if(!check) System.out.println("Error.");
									for(j=1; j<100; j++){
										if(connArray[j] != null){
											onlineUsers = "***ONLINE USERS***";
											onlineUsers = onlineUsers + usernames[j];
											if(stats[j].equals("") == false){
											onlineUsers = onlineUsers + " - " + stats[j];
										}
										check = connArray[i].sendMessage(onlineUsers);
										if(!check) System.out.println("Error.");
										}
									}
								}
						}

					}
				
				
					else if(msgIn.length() > 12 && msgIn.substring(0,13).equals("/changestatus") == true) {
					clientStatus = msgIn.substring(14);
					stats[clientNum] = clientStatus;
					
					msgOut = "Server message: " + clientName + " has changed status to " + "\"" + clientStatus + "\"";
					//System.out.println("Server message: " + clientName + " has changed status to " + "\"" + clientStatus + "\"");
					
						for(i=1;i<100;i++){
							if(connArray[i] != null){
								check = connArray[i].sendMessage(clrStats);
								if(!check) System.out.println("Error.");
								for(j=1; j<100; j++){
									if(connArray[j] != null){
										onlineUsers = "***ONLINE USERS***";
										onlineUsers = onlineUsers + usernames[j];
										if(stats[j].equals("") == false){
										onlineUsers = onlineUsers + " - " + stats[j];
									}
									check = connArray[i].sendMessage(onlineUsers);
									if(!check) System.out.println("Error.");
									}
								}
							}
						}
					
					}

					else if(msgIn.length() > 7 && msgIn.substring(0,8).equals("/whisper") == true) {
					String whisperTo = "", whisperMsg = ""; i = 9;
						while(i < msgIn.length() && msgIn.charAt(i) != ' ' && msgIn.charAt(i) != '\n' && msgIn.charAt(i) != '\0'){
							whisperTo = whisperTo + msgIn.charAt(i);
							i++;
						}
						i++;
						if(i < msgIn.length())whisperMsg = msgIn.substring(i);
						
						boolean whispered = false;
						for(i=1;i<100; i++){
							if(usernames[i] != null && usernames[i].equals(whisperTo) == true){
							check = connArray[i].sendMessage("[" + clientName + " whispers]: " + whisperMsg);
							whispered = true;
							if(!check) System.out.println("Error.");
							}
						}
					
						if(whispered == false)check = connArray[clientNum].sendMessage("Sorry, user \"" + whisperTo + "\" is not connected.");
						else{
						//send to self too
						check = connArray[clientNum].sendMessage("[You whispered to " + whisperTo + "]: " + whisperMsg);
						System.out.println("Server message: " + clientName + " whispered to " +  whisperTo);
						}
						
						//broadcast msg
						msgOut = null;
					}


					else {
					msgOut = "Server message: Invalid command "+ msgIn;
					check = connArray[clientNum].sendMessage(msgOut);
					if(!check) System.out.println("Error.");
					
					msgOut = null;
					}
					
					
				}
				
				else{
					msgOut = clientName + ": " + msgIn;
				}
				
				
				for(i=1;i<100;i++){
						if(connArray[i] != null && msgOut != null){
							check = connArray[i].sendMessage(msgOut);
							if(!check) System.out.println("Error.");
						}	
				}
				
				if(msgOut != null)
				System.out.println(msgOut);
				
				msgIn = connArray[clientNum].getMessage();
				
			}
			
			msgOut = "GOODBYE";
			check = connArray[clientNum].sendMessage(msgOut);
			if(!check) System.out.println("Error.");
			connArray[clientNum] = null;
			stats[clientNum] = "";
			usernames[clientNum] = "";
			
			
			for(i=1;i<100;i++){
				if(connArray[i] != null){
					check = connArray[i].sendMessage(clrStats);
					if(!check) System.out.println("Error.");
						for(j=1; j<100; j++){
							if(connArray[j] != null){
								onlineUsers = "***ONLINE USERS***";
								onlineUsers = onlineUsers + usernames[j];
								if(stats[j].equals("") == false){
								onlineUsers = onlineUsers + " - " + stats[j];
							}
							check = connArray[i].sendMessage(onlineUsers);
							if(!check) System.out.println("Error.");
							}
						}
				}
			}
			
			System.out.println("Server message: " + clientName + " has disconnected");
			msgOut = "Server message: " + clientName + " has disconnected";
			for(i=1;i<100;i++){
				if(connArray[i] != null && msgOut != null){
					check = connArray[i].sendMessage(msgOut);
					if(!check) System.out.println("Error.");
				}	
			}
			
			
			
	}
	
}