package client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import jdbc.User;

public class Login extends JPanel{
	Client client;
	
	User userDB = User.getUser();
	JPanel backOrOk = new JPanel();
	JButton back_btn = new JButton("뒤로");
	JButton ok_btn = new JButton("완료");
	
	public Login(Client c){
		this.client = c;
		setBackground(Color.red);
		setSize(client.dsizeW,client.dcardH);
		setBackground(client.def_color[4]);
		setLayout(null);
		
		JPanel iconPan = new JPanel();
		JPanel loginPan = new JPanel();
		
		JLabel reserved = new JLabel("(c)2016. By 나영채 All Rights Reserved.");
		JTextField id_fld = new JTextField("id를 입력해주세요 (메일형식 제외)",20); //-> login
		JPasswordField pw_fld = new JPasswordField(15); //-> join
		
		iconPan.setSize(client.dsizeW,client.dcardH-150);
		iconPan.setLocation(0, 0);
		iconPan.setOpaque(false);
		
		loginPan.setBorder(new EmptyBorder(25, 35, 25, 35));
		loginPan.setSize(client.dsizeW,150);
		loginPan.setLocation(0, client.dcardH-170);
		loginPan.setLayout(new GridLayout(2, 1,0,15));
		loginPan.setOpaque(false); //opaque 100%
		
		
		id_fld.setBackground(client.def_color[3]);
		id_fld.setFont(client.def_font(15));
		id_fld.setForeground(Color.WHITE);
		id_fld.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				id_fld.setText("");
			}
		});
		
		pw_fld.setBackground(client.def_color[3]);
		pw_fld.setForeground(Color.WHITE);
		pw_fld.setFont(client.def_font(15));
		pw_fld.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(new String(pw_fld.getPassword()).length()>=5){
					ok_btn.setEnabled(true);
				}else{
					ok_btn.setEnabled(false);
				}
			}
		});
		
		backOrOk.setLayout(new GridLayout(2, 2, 4, 4));
		back_btn.setBackground(client.def_color[3]);
		back_btn.setForeground(Color.WHITE);
		back_btn.addActionListener(e->{ // back
			client.changeCard("mainPanel");
		});
		ok_btn.setEnabled(false); //비활성
		ok_btn.setBackground(client.def_color[3]);
		ok_btn.setForeground(Color.WHITE);
		ok_btn.addActionListener(e->{
			if(!isMatches("[a-zA-Z0-9]{1,}",id_fld.getText())){
				client.warningMsg("메일주소가 틀렸습니다.");
			}else if(!isMatches("[a-zA-Z0-9]{5,}",new String(pw_fld.getPassword()))){
				client.warningMsg("비밀번호가 틀렸습니다.");
			}else{
				userDB.dbConnect();
				String name = userDB.login(id_fld.getText(), new String(pw_fld.getPassword()));
				if(name == null){
					client.warningMsg("로그인이 실패하였습니다.");
				}else{
					client.infoMsg("로그인이 완료되었습니다.");
					client.setUserName(name); //로그아웃할때 초기화 해야됨
					client.setMail(id_fld.getText());
					client.changeCard("chat");
				}
				userDB.dbClose();
			}
		});
		
		backOrOk.add(back_btn);
		backOrOk.add(ok_btn);
		backOrOk.add(new JLabel(""));
		backOrOk.setSize(190,50);
		backOrOk.setLocation(75, client.dsizeH-60);
		backOrOk.setOpaque(false);
		
		loginPan.add(id_fld);
		loginPan.add(pw_fld);
		
		reserved.setSize(400,20);
		reserved.setLocation(20, client.dcardH);
		
		add(backOrOk);
		add(reserved);
		add(iconPan);
		add(loginPan);
		
		setVisible(true);
	}
	
	private boolean isMatches(String regex,String input){
		Pattern pTmp = Pattern.compile(regex);
		Matcher mTmp = pTmp.matcher(input);
		return mTmp.matches();
	}
}
