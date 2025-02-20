package com.fh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

//JDBC 과정에서 필요한 공통 코드 작업용 클래스
public class JDBCTemplate {
	
	// 1. Connection 객체 생성 후 해당 Connection 객체를 반환해주는 메소드
	public static Connection getConnection() {
		
		// driver.properties 파일로부터 접속 관련 정보들 읽어들이기
		Properties prop = new Properties();
		
		// 읽어들일 파일 경로
		String path = "/sql/driver/driver.properties";
		
		String fileName = JDBCTemplate.class
							.getResource(path).getPath();
		
		try {
			prop.load(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		
		try {
			// 1) JDBC Driver 등록
			Class.forName(prop.getProperty("driver"));
			
			// 2) DB 접속정보를 제시하면서 Connection 객체 생성
			conn = DriverManager.getConnection(prop.getProperty("url"), 
											   prop.getProperty("username"), 
											   prop.getProperty("password"));
			
			// 3) 자동커밋 해제
			conn.setAutoCommit(false);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 최종적으로 만들어진 Connection 반환
		return conn;
		
	}
	
	// 2_1. 전달받은 Connection 객체를 가지고 commit 해주는 메소드
	public static void commit(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 2_2. 전달받은 Connection 객체를 가지고 rollback 해주는 메소드
	public static void rollback(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3_1. 전달받은 Connection 객체를 반납시켜주는 메소드
	public static void close(Connection conn) {
		
		try {
			
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3_2. 전달받은 (Prepared)Statement 객체를 반납시켜주는 메소드
	public static void close(Statement stmt) {
		
		try {
			
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3_3. 전달받은 ResultSet 객체를 반납시켜주는 메소드
	public static void close(ResultSet rset) {
		
		try {
			
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
