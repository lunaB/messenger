package main;

import javax.swing.JOptionPane;

import client.Client;
import jdbc.DAO;
import server.Server;

public class Main {
	public static void main(String [] args){
		String options[] = {"서버를 이용하겠습니다.","클라이언트를 이용하겠습니다.","그냥 종료하겠습니다."};
		int choose = JOptionPane.showOptionDialog(null, "어떤모드로 실행하시겟습니까?", "SELECT", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
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
