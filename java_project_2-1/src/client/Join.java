package client;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Join extends JPanel{
	
	Client client;
	public Join(Client c){
		this.client = c;
		client.setSize(400,900);
	}
}
