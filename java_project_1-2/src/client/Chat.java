package client;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Chat extends JPanel {
	Client client;
	
	JPanel chat_pan = new JPanel();
	JTextArea chat_ar = new JTextArea(); //ar
	JTextField chat_fld = new JTextField();
	
	public Chat(Client c){
		this.client = c;
		
		//design
		setBackground(client.def_color[0]);
		setLayout(null);
		
		//chat_ar.setOpaque(false);
		chat_ar.setFont(new Font("¸¼Àº °íµñ", 0, 16));
		chat_pan.setLayout(new GridLayout(1, 1));
		chat_pan.add(chat_ar);
		//chat_pan.setOpaque(false);
		chat_pan.setSize(client.dsizeW,client.dcardH-80);
		chat_pan.setLocation(0,45);
		
		chat_fld.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == '\n'){
					client.clientBack.sendMsg(chat_fld.getText());
					chat_fld.setText("");
				}
			}
		});
		chat_fld.setSize(client.dsizeW,40);
		chat_fld.setLocation(0,client.dcardH-20);
		chat_fld.setFont(new Font("¸¼Àº °íµñ", 0, 16));
		
		add(chat_pan);
		add(chat_fld);
		
		setVisible(true);
	}
	
	public void appendMsg(String msg,String name){
		if(client.getUserName().equals(name)){
			chat_ar.append("³ª -> "+msg+"\n");
		}else{
			chat_ar.append(name+" -> "+msg+"\n");
		}
		
	}
}
