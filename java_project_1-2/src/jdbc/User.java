package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

public class User {
	
	//Singleton 패턴을 채택함
	private static User user;
	
	private Connection _dbConn;
	
	static { //한번만로드
		final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			//static 이라 에러메세지 못띄움
			System.out.println("jdbc 로드실패 : "+e.getMessage()); 
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
			dbError("데이터베이스 연결에 실패하였습니다\nERROR : "+e.getErrorCode());
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
			dbError("데이터베이스 중지에서 에러가 발생하였습니다\nERROR : "+e.getErrorCode());
		}finally{
			_dbConn = null;
		}
	}
	
	//join
	public boolean isMailOverlap(String mail){
		String sql = "select * from member where email = ?";
		try {
			PreparedStatement mailSelect_ps = _dbConn.prepareStatement(sql);
			mailSelect_ps.setString(1, mail+"@gmail.com");
			ResultSet re = mailSelect_ps.executeQuery();
			if(re.getRow()==0){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
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
			dbError("데이터베이스 쿼리에 문제가 발생하였습니다\nError : "+e.getErrorCode());
			e.printStackTrace();
		}
		return false;
	}
	
	
	//login
	public String login(String id,String pw){
		String sql = "select * from member where email = ? and pw = ?";
		try {
			PreparedStatement loginOk_ps = _dbConn.prepareStatement(sql);
			loginOk_ps.setString(1, id);
			loginOk_ps.setString(2, pw);
			ResultSet res = loginOk_ps.executeQuery();
			
			if(res.next())
				return res.getString(2);
			else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
