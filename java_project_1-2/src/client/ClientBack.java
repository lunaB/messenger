package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ClientBack {
	
	private String mail = "";
	private String name = "";
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private Chat chat; //chat
	
	final private String host = "127.0.0.1";
	final private int port = 6767;
	
	public void conn(Client c){
		
		mail = c.getMail();
		name = c.getUserName();
		Wait w =new Wait();
		w.start();
	}
	
	class Wait extends Thread{
		@Override
		public void run() {
			try {
				socket = new Socket(host, port);
				//연결안되는거 따로 처리 나중에
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());
				//server - map
				//id-name server 85line
				out.writeUTF(mail+"-"+name);
				while(in!=null){
					String msg = in.readUTF();
					String msgI[] = msg.split("-");
					chat.appendMsg(msgI[1],msgI[0]);
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setChat(Chat chat){
		this.chat = chat;
	}
	
	public void sendMsg(String msg){// chat 에서사용
		try {
			out.writeUTF(msg);
		} catch (IOException e) {
			//전송실패 뭔가 보여줘야함
			e.printStackTrace(); 
		}
	}
	
}
