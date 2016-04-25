package com.estsoft.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

//DAO가 아니니깐 repository는 아님. 그리고 service도 아니지 그러니깐 component
//@Component 자동생성하지 않을 때 지움
public class MySQLWebDBConnection implements DBConnection {
@SuppressWarnings("finally")
public Connection getConnection() throws SQLException{
		Connection conn = null;	
		try{
			
		Class.forName( "com.mysql.jdbc.Driver" );

		//2. Connection ���
		String url = "jdbc:mysql://localhost/webdb";
		conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException ex) {
			System.out.println( "드라이버를 찾을 수 없습니다:" + ex );
		}finally{
		}
		return conn;
	}
}