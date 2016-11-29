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
		//thread 사용으로 인한 충돌 방지
		Collections.synchronizedMap(client_map);
		serverSocket = new ServerSocket(port);
		server.append_log("서버 준비완료");
		ServerMain_thr smt = new ServerMain_thr();
		smt.start();
	}
	class ServerMain_thr extends Thread{
		@Override
		public void run() {
			while(true){
				System.out.println("대기중..");
				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				} //받아서
				server.append_log(socket.getInetAddress()+" : 에서 접속함");
				//thread start 쓰래도르 준다
				Read read = new Read(socket);
				read.start();
			}
		}
	}
	
	public void addClient(String id, DataOutputStream out,String name) throws IOException {
		//자신의 입장소식을 보지않음으로 Map 에 넣기전 전송
		send_message(name+"-님이 접속하셨습니다.");
		server.append_log(name+"-님이 접속하셨습니다.");
		client_map.put(id, out);
        send_message("system-"+client_map.size()+"명이 동시접속중입니다.");
    }
 
    public void removeClient(String id,String name) {
    	send_message(name+"-님이 퇴장하셨습니다.");
    	server.append_log(name+"-님이 퇴장하셨습니다.");
    	client_map.remove(id);
        send_message("system-"+client_map.size()+"명이 동시접속중입니다.");
    }
	
	public void send_message(String msg){ //뿌리기
		Iterator<DataOutputStream> it = client_map.values().iterator();
		while(it.hasNext()){
			try {
				it.next().writeUTF(msg);
			} catch (IOException e) {
				System.out.println("out");
			}
		}
	}
	//접속한 사람수 thread가 돌게 한다.
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
				 * 접속하자마자 id-name 형식으로 보내게 한다
				 * 자르기 앞으로 나올 명령어에서 자르기 위해서는 
				 * id 나 name 에 - 를 사용못하게 해야됨. 물론 공백도안됨
				 * 두번보내는거에서 시간차로 인한 오류가 행여나 발생할수 있기에
				 * 미리보내서 나눔
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
					//시간남으면 비속어 필터링 클래스도 만들자
					msg = name+"-"+in.readUTF(); //유저의 메세지를 받았을경우
					server.append_log(msg);
					send_message(msg);
				}
			} catch (IOException e) {
				removeClient(id,name);
			}
		}
	}
}