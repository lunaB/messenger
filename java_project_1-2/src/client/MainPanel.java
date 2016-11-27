package client;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JPanel{
	
	JLabel reserved = new JLabel("2016. By 나영채 All Rights Reserved.");
	JButton login_btn = new JButton("로그인"); //-> login
	JButton join_btn = new JButton("회원가입"); //-> join
	
	Client client;
	public MainPanel(Client c){
		this.client = c;
		setBackground(client.def_color[2]);
		setLayout(null);
		
		JPanel iconPan = new JPanel();
		JPanel btnPan = new JPanel();
		
		iconPan.setSize(client.dsizeW,client.dcardH-150);
		iconPan.setLocation(0, 0);
		iconPan.setOpaque(false);
		
		btnPan.setBorder(new EmptyBorder(25, 35, 25, 35));
		btnPan.setSize(client.dsizeW,150);
		btnPan.setLocation(0, client.dcardH-150);
		btnPan.setLayout(new GridLayout(2, 1,0,15));
		btnPan.setOpaque(false); //opaque 100%
		
		login_btn.setBackground(client.def_color[3]);
		login_btn.setFont(client.def_font(15));
		login_btn.setForeground(Color.WHITE);
		login_btn.addActionListener(e->{
			client.changeCard("login_btn"); //->login
		});
		
		join_btn.setBackground(client.def_color[3]);
		join_btn.setForeground(Color.WHITE);
		join_btn.setFont(client.def_font(15));
		join_btn.addActionListener(e->{
			client.changeCard("join"); // ->join
		});
		
		btnPan.add(login_btn);
		btnPan.add(join_btn);
		
		reserved.setSize(400,20);
		reserved.setLocation(20, client.dcardH);
		
		add(reserved);
		add(iconPan);
		add(btnPan);
	}
}
