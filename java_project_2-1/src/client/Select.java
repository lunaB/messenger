package client;

import javax.swing.JPanel;

public class Select extends JPanel{
	Client client;
	public Select(Client c){
		this.client = c;
		client.setSize(400,900);
	}
}
