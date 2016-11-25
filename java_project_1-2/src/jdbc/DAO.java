package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

public class DAO {
	
	private Connection _dbConn;
	
	private void dbError(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public Connection dbConnect(){
		if(_dbConn != null){
			return _dbConn;
		}
		final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
		final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
		final String USER = "hr";
		final String PASS = "hr1234";
		try {
			Class.forName(JDBC_DRIVER);
			_dbConn=DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (ClassNotFoundException | SQLException e) {
			dbError("데이터베이스 연결에 실패하였습니다\nERROR : 경로에 데이터베이스가 없습니다.");
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
			dbError("데이터베이스 중지에서 에러가 발생하였습니다\nERROR : 관리자에게 연락해주세요");
		}finally{
			_dbConn = null;
		}
	}
	/*
	public Vector<Object> selectMail(String name){
		String sql = "select * from mail where name = ?";
		try {
			PreparedStatement select_ps = _dbConn.prepareStatement(sql);
			select_ps.setString(1, name);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<Object> vector = new Vector<Object>();
		return vector;
	}
	*/
	
	//mail----------
	
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
			dbError("데이터베이스 쿼리에 문제가 발생하였습니다\nError : 관리자에게 연락해주세요.");
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
			dbError("데이터베이스 쿼리에 문제가 발생하였습니다\nError : 관리자에게 연락해주세요.");
			e.printStackTrace();
		}
		return false;
	}
	
	//----------user
	
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
			dbError("데이터베이스 쿼리에 문제가 발생하였습니다\nError : 관리자에게 연락해주세요.");
			e.printStackTrace();
		}
		return false;
	}
}
