package main;

import javax.swing.JOptionPane;

import client.Client;
import jdbc.DAO;
import server.Server;

/*
 * ���� -> [��ȭ��]
 * 
 */

public class Main {
	public static void main(String [] args){
		String options[] = {"server","client","exit"};
		int choose = JOptionPane.showOptionDialog(null, "mode select", "[��ȭ��] ä�� ������", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		switch(choose){
		case 0: 
			Server server = new Server();
			break;
		case 1:
			Client client = new Client();
			break;
		}
	}
}
