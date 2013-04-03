import java.io.*;
import java.net.*;

public class MyConnection {
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	
		public MyConnection(Socket s){
			socket = s;
			try{
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			out = new PrintWriter(osw);
			} catch (Exception e){ e.printStackTrace();}
			
			try{
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is); 
			in = new BufferedReader(isr);
			} catch (Exception e){ e.printStackTrace();}
		}

		
		
		public boolean sendMessage(String msg){
			try{
			out.println(msg);
			out.flush();
			return true;
			} catch (Exception e){ e.printStackTrace(); return false; }
		}

		public String getMessage(){
			try{
			String msg = in.readLine();
			return msg;
			} catch (Exception e){ e.printStackTrace(); return "Error getting message.";}
		}

}
