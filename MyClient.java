import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;

public class MyClient extends JFrame implements ActionListener{
	Socket s;
	JTextArea conversation;
	JTextArea status;
	JTextField outgoing;
	
	public MyClient() {
		JFrame frame = new JFrame("Client");
		JPanel mainpanel = new JPanel();
		
		conversation = new JTextArea();
		status = new JTextArea();
		outgoing = new JTextField();
		JButton sendbtn = new JButton("Send");
		JLabel chat = new JLabel("Chat Window");
		JLabel online = new JLabel("Online Clients");
		
		conversation.setLineWrap(true);
		conversation.setWrapStyleWord(true);
		conversation.setEditable(false);
		
		status.setEditable(false);

		JScrollPane scrollbar = new JScrollPane(conversation);
		scrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		sendbtn.addActionListener(this);
		
		chat.setPreferredSize(new Dimension(465, 10));
		online.setPreferredSize(new Dimension(150, 10));
		conversation.setPreferredSize(new Dimension(450, 300));
		status.setPreferredSize(new Dimension(150, 300));
		outgoing.setPreferredSize(new Dimension(465, 25));
		sendbtn.setPreferredSize(new Dimension(150, 25));
		
		status.setBorder(LineBorder.createGrayLineBorder());
		status.setForeground(Color.orange);
		
		mainpanel.add(chat); mainpanel.add(online);
		mainpanel.add(scrollbar);
		mainpanel.add(status);
		mainpanel.add(outgoing);
		mainpanel.add(sendbtn);
		mainpanel.setBackground(new Color(0, 202, 202));
		
		frame.add(mainpanel);
		
		frame.setSize(650, 400);
		frame.setVisible(true);
		frame.setLocation(100, 100);
		frame.setResizable(false);
		frame.getRootPane().setDefaultButton(sendbtn);
		
		try {
			System.out.println("Client tries to connect to server...");
			s = new Socket("127.0.0.1", 8888);
			System.out.println("Client connected!");

		} catch (Exception e){ e.printStackTrace(); }
		new ListenerClient(s, conversation, status);
	}
	
	public void actionPerformed(ActionEvent e) {
        
		new SenderClient(s, outgoing);       
    }      
	
	public static void main(String args[]) {
		MyClient client = new MyClient();
	}
}

