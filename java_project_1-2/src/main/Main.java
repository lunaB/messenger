package main;

import javax.swing.JOptionPane;

import client.Client;
import server.Server;

/*
 * 제목 -> [대화로]
 * 작성 시작 -> 11월 17일 ~
 */

public class Main {
	public static void main(String [] args){
		String options[] = {"server","client","exit"};
		int choose = JOptionPane.showOptionDialog(null, "mode select", "[대화로] 채팅 구동기", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
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
