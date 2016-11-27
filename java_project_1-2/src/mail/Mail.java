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
			// ���� ���� ����
	        String host = "smtp.gmail.com";
	        String username = "projectMail161109@gmail.com";
	        String password = "project161109";
	         
	        // ���� ����
	        String recipient = mail;//ex - "luna20617@gmail.com";
	        String subject = "�ȳ��ϼ��� ��ä�� ���������Դϴ�.";
	        String body = "������ȣ�� [ "+code+" ] �Դϴ�";
	        
	        //properties ����
	        Properties props = new Properties();
	        props.put("mail.smtps.auth", "true");
	        // ���� ����
	        Session session = Session.getDefaultInstance(props);
	        MimeMessage msg = new MimeMessage(session);
	 
	        // ���� ����
	        msg.setSubject(subject);
	        msg.setText(body);
	        msg.setFrom(new InternetAddress(username));
	        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	 
	        // �߼� ó��
	        Transport transport = session.getTransport("smtps");
	        transport.connect(host, username, password);
	        transport.sendMessage(msg, msg.getAllRecipients());
	        transport.close();
	        JOptionPane.showMessageDialog(null, "���� �Ϸ�");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "���� ���� ����");
		}
	}
}
