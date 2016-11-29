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
	private Socket socket;
	
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
		ServerMain_thr smt = new ServerMain_thr();
		smt.start();
	}
	class ServerMain_thr extends Thread{
		@Override
		public void run() {
			while(true){
				System.out.println("�����..");
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				} //�޾Ƽ�
				server.append_log(socket.getInetAddress()+" : ���� ������");
				//thread start �������� �ش�
				Read read = new Read(socket);
				read.start();
			}
		}
	}
	
	public void addClient(String id, DataOutputStream out,String name) throws IOException {
		//�ڽ��� ����ҽ��� ������������ Map �� �ֱ��� ����
		send_message(name+"-���� �����ϼ̽��ϴ�.");
		server.append_log(name+"-���� �����ϼ̽��ϴ�.");
		client_map.put(id, out);
        send_message("system-"+client_map.size()+"���� �����������Դϴ�.");
    }
 
    public void removeClient(String id,String name) {
    	send_message(name+"-���� �����ϼ̽��ϴ�.");
    	server.append_log(name+"-���� �����ϼ̽��ϴ�.");
    	client_map.remove(id);
        send_message("system-"+client_map.size()+"���� �����������Դϴ�.");
    }
	
	public void send_message(String msg){ //�Ѹ���
		Iterator<DataOutputStream> it = client_map.values().iterator();
		while(it.hasNext()){
			try {
				it.next().writeUTF(msg);
			} catch (IOException e) {
				System.out.println("out");
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
				addClient(id, out, name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				while(true){
					String msg;
					//�ð������� ��Ӿ� ���͸� Ŭ������ ������
					msg = name+"-"+in.readUTF(); //������ �޼����� �޾������
					server.append_log(msg);
					send_message(msg);
				}
			} catch (IOException e) {
				removeClient(id,name);
			}
		}
	}
}