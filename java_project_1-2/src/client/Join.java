package client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import jdbc.DAO;
import mail.Mail;

public class Join extends JPanel{
	
	Client client;
	
	JPanel form_pan = new JPanel();
	
	JPanel email_pan = new JPanel();
	JTextField email_fld = new JTextField(20);
	
	JPanel code_pan = new JPanel();
	JButton codeSub_btn = new JButton("인증메일 전송");
	JTextField code_fld = new JTextField(15);
	
	JPanel pw_pan = new JPanel();
	JPasswordField pw_fld = new JPasswordField(15);
	JPasswordField pw_fld2 = new JPasswordField(15);
	
	JPanel backOrOk = new JPanel();
	JButton back_btn = new JButton("뒤로");
	JButton ok_btn = new JButton("완료");
	
	Mail codeMail = new Mail();
	//dao 할차례임 -----------------------------------------------
	String code = "";
	
	public Join(Client c){
		
		this.client = c;
		setBackground(client.def_color[1]);
		setLayout(null);
		
		form_pan.setSize(client.dsizeW,client.dcardH);
		form_pan.setLayout(new GridLayout(6, 1, 15, 15));
		form_pan.setBorder(new EmptyBorder(150, 40, 0, 40));
		form_pan.setOpaque(false);
		
		email_pan.setLayout(new GridLayout(2, 2, 4, 4));
		email_fld.setBackground(client.def_color[0]);
		email_pan.add(new JLabel("이메일 입력(id)"));
		email_pan.add(new JLabel(""));
		email_pan.add(email_fld);
		email_pan.add(new JLabel("@gmail.com"));
		email_pan.setOpaque(false);
		
		code_pan.setLayout(new GridLayout(2, 2, 4, 4));
		code_fld.setBackground(client.def_color[0]);
		codeSub_btn.setBackground(client.def_color[3]);
		codeSub_btn.setForeground(Color.WHITE);
		codeSub_btn.addActionListener(e->{
			if(isMatches("[0-9a-zA-Z]",email_fld.getText())){
				code = createCode();
				codeMail.sendMail(email_fld.getText(), code);
				codeMail.send();
				ok_btn.setEnabled(true);
			}else{
				warningMsg("이메일 입력형식에 어긋납니다.");
			}
		});
		code_pan.add(new JLabel("인증코드 입력"));
		code_pan.add(code_fld);
		code_pan.add(codeSub_btn);
		code_pan.setOpaque(false);
		
		pw_pan.setLayout(new GridLayout(2, 2, 4, 4));
		pw_fld.setBackground(client.def_color[0]);
		pw_fld2.setBackground(client.def_color[0]);
		pw_pan.add(new JLabel("비밀번호"));
		pw_pan.add(pw_fld);
		pw_pan.add(new JLabel("비밀번호 확인"));
		pw_pan.add(pw_fld2);
		pw_pan.setOpaque(false);
		
		backOrOk.setLayout(new GridLayout(2, 2, 4, 4));
		back_btn.setBackground(client.def_color[3]);
		back_btn.setForeground(Color.WHITE);
		back_btn.addActionListener(e->{ // back
			client.changeCard("mainPanel");
		});
		ok_btn.setEnabled(false); //비활성
		ok_btn.setBackground(client.def_color[3]);
		ok_btn.setForeground(Color.WHITE);
		ok_btn.addActionListener(e->{ //OK
			if(code_fld.getText() == code){//인증완료
				
			}
		});
		backOrOk.add(back_btn);
		backOrOk.add(ok_btn);
		backOrOk.add(new JLabel(""));
		backOrOk.setOpaque(false);
		
		form_pan.add(email_pan);
		form_pan.add(code_pan);
		form_pan.add(pw_pan);
		form_pan.add(backOrOk);
		
		add(form_pan);
		
		setVisible(true);
	}
	
	private String createCode(){  //[0-9a-zA-Z]{6}
		Random rand = new Random();
		String code = "";
		
		for(int i=0;i<6;i++){
			switch(rand.nextInt(3)){
			case 0: 
				code += String.valueOf(rand.nextInt(10));
				break;
			case 1:
				code += String.valueOf(((char)(rand.nextInt('Z'-'A')+'A')));
				break;
			case 2:
				code += String.valueOf(((char)(rand.nextInt('z'-'a')+'a')));
			}
		}
		return code;
	}
	
	private void warningMsg(String msg){
		JOptionPane.showMessageDialog(null, msg,"경고",JOptionPane.WARNING_MESSAGE);
	}
	
	private boolean isMatches(String regex,String input){
		Pattern pTmp = Pattern.compile(regex);
		Matcher mTmp = pTmp.matcher(input);
		return mTmp.matches();
	}
}
