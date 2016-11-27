package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

public class User {
	
	//Singleton ������ ä����
	private static User user;
	
	
	private Connection _dbConn;
	
	
	static { //�ѹ����ε�
		final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			//static �̶� �����޼��� �����
			System.out.println("jdbc �ε���� : "+e.getMessage()); 
		}
	}
	
	public static User getUser(){
		if(user == null){
			user = new User();
		}
		return user;
	}
	
	private void dbError(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public Connection dbConnect(){
		if(_dbConn != null){
			return _dbConn;
		}
		
		final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		final String USER = "hr";
		final String PASS = "hr1234";
		try {
			_dbConn=DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (SQLException e) {
			dbError("�����ͺ��̽� ���ῡ �����Ͽ����ϴ�\nERROR : "+e.getErrorCode());
			e.printStackTrace();
		}
		return _dbConn;
	}
	
	public void dbClose(){
		try{
			if(_dbConn != null){
				if(!_dbConn.isClosed()){
					_dbConn.close();
				}
			}
		}catch(SQLException e){
			dbError("�����ͺ��̽� �������� ������ �߻��Ͽ����ϴ�\nERROR : "+e.getErrorCode());
		}finally{
			_dbConn = null;
		}
	}
	
	//----------user
	
	public boolean isMailOverlap(String mail){
		String sql = "select * from member where mail = ?";
		try {
			PreparedStatement mailSelect_ps = _dbConn.prepareStatement(sql);
			mailSelect_ps.setString(1, mail);
			ResultSet re = mailSelect_ps.executeQuery();
			if(re.getRow()>0){
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	public boolean insertUser(String name,String email,String pw){
		String sql = "insert into member values(seq_member.nextval,?,?,?,sysdate)";
		PreparedStatement user_ps = null;
		try {
			user_ps = _dbConn.prepareStatement(sql);
			user_ps.setString(1, name);
			user_ps.setString(2, email);
			user_ps.setString(3, pw);
			int cRow = user_ps.executeUpdate();
			if(cRow == 1){
				return true;
			}
		} catch (SQLException e) {
			dbError("�����ͺ��̽� ������ ������ �߻��Ͽ����ϴ�\nError : "+e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	
	/*
	public boolean insertMail(String code,String name,String email){
		String sql = "insert into mail values(?,?,?)";
		try {
			PreparedStatement mail_ps = _dbConn.prepareStatement(sql);
			mail_ps.setString(1, code);
			mail_ps.setString(2, name);
			mail_ps.setString(3, code);
			int cRow = mail_ps.executeUpdate();
			if(cRow == 1){
				return true;
			}
		} catch (SQLException e) {
			dbError("�����ͺ��̽� ������ ������ �߻��Ͽ����ϴ�\nError : "+e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean selectMail(String mail,String code){
		String sql = "select * from mail where mail = ?";
		PreparedStatement mail_ps = null;
		try {
			mail_ps = _dbConn.prepareStatement(sql);
			mail_ps.setString(1, mail);
			ResultSet re = mail_ps.executeQuery();
			if(re.getRow() == 1){
				return true;
			}
		} catch (SQLException e) {
			dbError("�����ͺ��̽� ������ ������ �߻��Ͽ����ϴ�\nError : "+e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}
	*/
}
