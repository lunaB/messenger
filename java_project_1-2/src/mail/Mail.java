package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Mail {
	
	private String mail;
	private String code;
	
	public void sendMail(String mail,String code){
		this.mail = mail;
		this.code = code;
	}
	
	public void send(){
		try{
			// 메일 관련 정보
	        String host = "smtp.gmail.com";
	        String username = "projectMail161109@gmail.com";
	        String password = "project161109";
	         
	        // 메일 내용
	        String recipient = mail;//ex - "luna20617@gmail.com";
	        String subject = "안녕하세요 영채톡 인증문자입니다.";
	        String body = "인증번호는 [ "+code+" ] 입니다";
	        
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
		}
	}
}
