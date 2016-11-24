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
	private Client client;
	private Chat chat;
	
	final private String host = "127.0.0.1";
	final private int port = 6767;
	
	
	public void setClient(Client client){
		this.client = client;
	}
	
	public void setChat(Chat chat){
		this.chat = chat;
	}
	
	public void connect(){
		try {
			socket = new Socket(host, port);
			//연결안되는거 따로 처리 나중에
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			//map에 넣을 메일보냄
			out.writeUTF(mail);
			read();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void read(){ //in connect()
		while(in!=null){
			try {
				String msg = in.readUTF();
				//여기다가 나중에 비속어 필터 추가해야함
				//chat.appendmsg(msg);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMsg(String msg){
		try {
			out.writeUTF(msg);
		} catch (IOException e) {
			//전송실패 뭔가 보여줘야함
			e.printStackTrace();
		}
	}
	/*
	public void setname(String name) {
        this.name = name;
    }
    */
	
	public boolean loginOK(){
		return (mail==""||name==""?false:true);
	}
	
	public void login(String id,String name){
		this.mail = id;
		this.name = name;
	}
	
	public void logout(){
		this.mail = "";
		this.name = "";
		JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
		client.changeCard("mainPanel");
	}
}
