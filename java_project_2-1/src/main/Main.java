package main;

import javax.swing.JOptionPane;

import client.Client;
import jdbc.DAO;
import server.Server;

public class Main {
	public static void main(String [] args){
		String options[] = {"������ �̿��ϰڽ��ϴ�.","Ŭ���̾�Ʈ�� �̿��ϰڽ��ϴ�.","�׳� �����ϰڽ��ϴ�."};
		int choose = JOptionPane.showOptionDialog(null, "����� �����Ͻðٽ��ϱ�?", "SELECT", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
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
