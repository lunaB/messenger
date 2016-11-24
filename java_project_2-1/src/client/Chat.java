package client;

import javax.swing.JPanel;

public class Chat extends JPanel{
	Client client;
	public Chat(Client c){
		this.client = c;
		client.setSize(400,900);
	}
}
