package client;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Chat extends JPanel{
	Client client;
	ClientBack clientBack = new ClientBack(); // back-end
	
	public Chat(Client c){
		this.client = c;

		clientBack.setChat(this);
		clientBack.connect();
		setBackground(client.def_color[0]);
		
		
		
		setVisible(true);
	}
}
