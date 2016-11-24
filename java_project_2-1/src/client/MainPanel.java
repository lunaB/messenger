package client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	
	JButton join_btn = new JButton("회원가입"); //-> this
	JButton login_btn = new JButton("로그인"); //-> chat
	
	Client client;
	public MainPanel(Client c){
		this.client = c;
		client.setSize(client.dsizeW,client.dsizeH);
		client.card_con.setBackground(c.def_color[2]);
		setLayout(null);
	}
}
