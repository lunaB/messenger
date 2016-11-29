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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import jdbc.User;
import mail.Mail;

public class Join extends JPanel{

	Client client;
	
	JPanel form_pan = new JPanel();
	
	JPanel mail_pan = new JPanel();
	JTextField mail_fld = new JTextField(20);
	
	JPanel userName_pan = new JPanel();
	JTextField name_fld = new JTextField(20);
	
	JPanel code_pan = new JPanel();
	JButton codeSub_btn = new JButton("ÀÎÁõ¸ŞÀÏ ¹ß¼Û");
	JTextField code_fld = new JTextField(15);
	
	JPanel pw_pan = new JPanel();
	JPasswordField pw_fld = new JPasswordField(15);
	JPasswordField pw_fld2 = new JPasswordField(15);
	
	JPanel backOrOk = new JPanel();
	JButton back_btn = new JButton("µÚ·Î");
	JButton ok_btn = new JButton("¿Ï·á");
	
	Mail codeMail = new Mail();
	final private int codeLength = 6;
	String code = "";
	
	User userDB = User.getUser();
	//User userDB = new User();
	
	public Join(Client c){
		
		this.client = c;
		setBackground(client.def_color[1]);
		setLayout(null);
		
		JLabel reserved = new JLabel("(c)2016. By ³ª¿µÃ¤ All Rights Reserved.");
		
		form_pan.setSize(client.dsizeW,client.dcardH);
		form_pan.setLayout(new GridLayout(6, 1, 15, 15));
		form_pan.setBorder(new EmptyBorder(100, 40, 0, 40));
		form_pan.setOpaque(false);
		
		userName_pan.setLayout(new GridLayout(2, 2, 4, 4));
		name_fld.setBackground(client.def_color[0]);
		userName_pan.add(new JLabel());
		userName_pan.add(new JLabel());
		userName_pan.add(new JLabel("ÀÌ¸§ ÀÔ·Â"));
		userName_pan.add(name_fld);
		userName_pan.setOpaque(false);
		
		mail_pan.setLayout(new GridLayout(2, 2, 4, 4));
		mail_fld.setBackground(client.def_color[0]);
		mail_pan.add(new JLabel("ÀÌ¸ŞÀÏ ÀÔ·Â(id)"));
		mail_pan.add(new JLabel());
		mail_pan.add(mail_fld);
		mail_pan.add(new JLabel("@gmail.com"));
		mail_pan.setOpaque(false);
		
		code_pan.setLayout(new GridLayout(2, 2, 4, 4));
		code_fld.setBackground(client.def_color[0]);
		codeSub_btn.setBackground(client.def_color[3]);
		codeSub_btn.setForeground(Color.WHITE);
		codeSub_btn.addActionListener(e->{
			userDB.dbConnect();
			boolean mailOverlap = userDB.isMailOverlap(mail_fld.getText());
			
			if(!isMatches("[°¡-ÆRa-zA-Z0-9]{1,}", name_fld.getText())){
				client.warningMsg("ÀÌ¸§ ÀÔ·Â Çü½Ä¿¡ ¾î±ß³³´Ï´Ù.");
			}else if(!isMatches("[a-zA-Z0-9]{1,}",mail_fld.getText())){
				client.warningMsg("¸ŞÀÏ ÀÔ·Â Çü½Ä¿¡ ¾î±ß³³´Ï´Ù.");
			}else if(!mailOverlap){
				client.warningMsg("ÀÌ¹Ì °¡ÀÔ µÇ¾îÀÖ´Â ¸ŞÀÏÀÔ´Ï´Ù.");
			}else{
				code = createCode();
				codeMail.mailSet(name_fld.getText(), mail_fld.getText(), code);
				codeMail.send();
				ok_btn.setEnabled(true);
			}
		});
		code_pan.add(new JLabel("ÀÎÁõÄÚµå ÀÔ·Â"));
		code_pan.add(code_fld);
		code_pan.add(codeSub_btn);
		code_pan.setOpaque(false);
		
		pw_pan.setLayout(new GridLayout(2, 2, 4, 4));
		pw_fld.setBackground(client.def_color[0]);
		pw_fld2.setBackground(client.def_color[0]);
		pw_pan.add(new JLabel("ºñ¹Ğ¹øÈ£"));
		pw_pan.add(pw_fld);
		pw_pan.add(new JLabel("ºñ¹Ğ¹øÈ£ È®ÀÎ"));
		pw_pan.add(pw_fld2);
		pw_pan.setOpaque(false);
		
		backOrOk.setLayout(new GridLayout(2, 2, 4, 4));
		back_btn.setBackground(client.def_color[3]);
		back_btn.setForeground(Color.WHITE);
		back_btn.addActionListener(e->{ // back
			client.changeCard("mainPanel");
		});
		ok_btn.setEnabled(false); //ºñÈ°¼º
		ok_btn.setBackground(client.def_color[3]);
		ok_btn.setForeground(Color.WHITE);
		ok_btn.addActionListener(e->{ //OK debug 11-28
			//Á¶°Ç¾È¸ÂÀ¸¸é ºüÁö°Ô
			if(!code_fld.getText().equals(code)){
				client.warningMsg("¸ŞÀÏ ÀÎÁõÄÚµå°¡ ¸ÂÁö ¾Ê½À´Ï´Ù.(´Ù½Ã Àü¼ÛÇÏ°í È®ÀÎÇÏ¼¼¿ä)");
				code = createCode();
				ok_btn.setEnabled(false);
			}else if(!isMatches("[a-zA-Z0-9]{"+codeLength+"}", code_fld.getText())){
				client.warningMsg("ÄÚµå Çü½ÄÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù");
			}else if(!isMatches("[a-zA-Z0-9]{5,}",new String(pw_fld.getPassword()))){
				client.warningMsg("ºñ¹Ğ¹øÈ£°¡ ¾ç½Ä¿¡ ¸ÂÁö¾Ê½À´Ï´Ù.");
			}else if(!(new String(pw_fld.getPassword()).equals(
				new String(pw_fld2.getPassword())))){
				client.warningMsg("ºñ¹Ğ¹øÈ£°¡ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù.");
			}else{//ok
				boolean ok = userDB.insertUser(name_fld.getText(), mail_fld.getText(), new String(pw_fld.getPassword()));
				if(ok){
					userDB.dbClose();
					name_fld.setText("");
					mail_fld.setText("");
					code_fld.setText("");
					pw_fld.setText("");
					pw_fld2.setText("");
					client.infoMsg("°¡ÀÔÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù.");
					code = null;
					client.changeCard("mainPanel");
				}else{
					client.warningMsg("°¡ÀÔ ¿¡·¯");
					name_fld.setText("");
					mail_fld.setText("");
					code_fld.setText("");
					pw_fld.setText("");
					pw_fld2.setText("");
					client.changeCard("mainPanel");
				}
			}
		});
		backOrOk.add(back_btn);
		backOrOk.add(ok_btn);
		backOrOk.add(new JLabel(""));
		backOrOk.setOpaque(false);
		
		form_pan.add(userName_pan);
		form_pan.add(mail_pan);
		form_pan.add(code_pan);
		form_pan.add(pw_pan);
		form_pan.add(backOrOk);
		
		reserved.setSize(400,20);
		reserved.setLocation(20, client.dcardH);
		
		add(reserved);
		add(form_pan);
		
		
		setVisible(true);
	}
	
	private String createCode(){  //[0-9a-zA-Z]{6}
		Random rand = new Random();
		//+debug stringBuilder
		StringBuilder sTmp = new StringBuilder();
		
		for(int i=0;i<codeLength;i++){
			switch(rand.nextInt(3)){
			case 0: 
				sTmp.append(rand.nextInt(10));
				break;
			case 1:
				sTmp.append(((char)(rand.nextInt('Z'-'A')+'A')));
				break;
			case 2:
				sTmp.append(((char)(rand.nextInt('z'-'a')+'a')));
			}
		}
		return sTmp.toString();
	}
	
	private boolean isMatches(String regex,String input){
		Pattern pTmp = Pattern.compile(regex);
		Matcher mTmp = pTmp.matcher(input);
		return mTmp.matches();
	}
}
