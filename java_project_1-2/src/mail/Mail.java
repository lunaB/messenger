package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Mail {
	
	private String name;
	private String mail;
	private String code;
	
	public void mailSet(String name,String mail,String code){
		this.mail = mail;
		this.code = code;
		this.name = name;
	}
	
	public void send(){
		try{
			// 메일 관련 정보
	        String host = "smtp.gmail.com";
	        String username = "projectMail161109@gmail.com";
	        String password = "project161109";
	         
	        // 메일 내용
	        String recipient = mail+"@gmail.com";//ex - luna20617;
	        String subject = "안녕하세요 '대화로'의 인증문자입니다.";
	        String body = name+"님의 회원가입 인증번호는 [ "+code+" ] 입니다.";
	        
	        //properties 설정
	        Properties props = new Properties();
	        props.put("mail.smtps.auth", "true");
	        // 메일 세션
	        Session session = Session.getDefaultInstance(props);
	        MimeMessage msg = new MimeMessage(session);
	 
	        // 메일 관련
	        msg.setSubject(subject);
	        msg.setText(body);
	        msg.setFrom(new InternetAddress(username));
	        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	 
	        // 발송 처리
	        Transport transport = session.getTransport("smtps");
	        transport.connect(host, username, password);
	        transport.sendMessage(msg, msg.getAllRecipients());
	        transport.close();
	        JOptionPane.showMessageDialog(null, "전송 완료");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "메일 전송 실패");
			e.printStackTrace();
		}
	}
}
