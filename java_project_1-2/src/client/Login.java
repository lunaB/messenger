package client;

import java.awt.Color;

import javax.swing.JPanel;

public class Login extends JPanel{
	Client client;
	
	public Login(Client c){
		this.client = c;
		setBackground(Color.red);
		setSize(client.dsizeW,client.dcardH);
		setBackground(client.def_color[2]);
		setVisible(true);
	}
}
