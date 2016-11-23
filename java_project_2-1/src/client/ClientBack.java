package client;

import javax.swing.JOptionPane;

public class ClientBack {
	
	private String mail = "";
	private String name = "";
	
	Client client;
	
	public void setClient(Client c){
		client = c;
	}
	
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
	
	public ClientBack() {
		// TODO Auto-generated constructor stub
		
	}
	
}
