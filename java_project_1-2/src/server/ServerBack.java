package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ServerBack {

	//key = id(email), values = dataOutputStream
	private Map<String , DataOutputStream> client_map = new HashMap<String, DataOutputStream>();
	private ServerSocket serverSocket;
	
	final private int port = 6767;
	
	Server server; //GUI
	public final void setGUI(Server server){
		this.server = server;
	}
	
	public void start_back() throws Exception{
		//thread ������� ���� �浹 ����
		Collections.synchronizedMap(client_map);
		
		serverSocket = new ServerSocket(port);
		server.append_log("���� �غ�Ϸ�");
		ServerStart serverStart = new ServerStart();
		serverStart.start();
	}
	
	public void send_message(String msg){ //�Ѹ���
		Iterator<DataOutputStream> it = client_map.values().iterator();
		while(it.hasNext()){
			try {
				it.next().writeUTF(msg);
			} catch (IOException e) {
				//�Ƹ� ���۵��� ��밡 �����ų� �ϴ� ����ϰ����� ����
				System.out.println("Error :: send_message()");
				e.printStackTrace();
			}
		}
	}
	class ServerStart extends Thread{
		@Override
		public void run() {
			while(true){
				try{
					System.out.println("�����..");
					Socket socket = serverSocket.accept(); //�޾Ƽ�
					System.out.println(socket.getInetAddress()+" : ���� ������");
					//thread start �������� �ش�
					Read read = new Read(socket);
					read.start();
				}catch(Exception e){
					
				}
			}
		}
	}
	//������ ����� thread�� ���� �Ѵ�.
	class Read extends Thread{ 
		
		private DataInputStream in;
		private DataOutputStream out;
		
		private String id;
		private String name;
		
		public Read(Socket soc){
			try {
				this.in = new DataInputStream(soc.getInputStream());
				this.out = new DataOutputStream(soc.getOutputStream());
				/*
				 * �������ڸ��� id-name �������� ������ �Ѵ�
				 * �ڸ��� ������ ���� ��ɾ�� �ڸ��� ���ؼ��� 
				 * id �� name �� - �� �����ϰ� �ؾߵ�. ���� ���鵵�ȵ�
				 * �ι������°ſ��� �ð����� ���� ������ �࿩�� �߻��Ҽ� �ֱ⿡
				 * �̸������� ����
				*/
				String sTmp[] = in.readUTF().split("-");
				id = sTmp[0];
				name = sTmp[1];
				//�ڽ��� ����ҽ��� ������������ Map �� �ֱ��� ����
				send_message(name+" ���� �����ϼ̽��ϴ�.");
				client_map.put(id,out);
			} catch (IOException e) {
				System.out.println("�ӱ⹫��");
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				while(true){
					String msg;
					//�ð������� ��Ӿ� ���͸� Ŭ������ ������
					msg = name+" : "+in.readUTF(); //������ �޼����� �޾������
					send_message(msg);
				}
			} catch (IOException e) {
				//������ �Ƹ� readUTF �� ���������Ű��� ���߿� üũ
				send_message(name+" ���� �����ϼ̽��ϴ�.");
			}
		}
	}
}
