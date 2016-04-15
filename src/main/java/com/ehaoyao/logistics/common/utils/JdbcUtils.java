package com.ehaoyao.logistics.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

public class JdbcUtils {

	private static String db_driver;
	private static String db_url;
	private static String db_userName;
	private static String db_passWord;
	private static final Logger logger = Logger.getLogger(JdbcUtils.class);
	static{
		Properties pro = new Properties();
		try {    
			String resource = "jdbc.properties";
	        InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream(resource);
	        pro.load(is);
		} catch (FileNotFoundException e) {
			logger.info("Can't find the properties ! Error:  "+e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.info(e);
			e.printStackTrace();
		}
		db_driver = pro.getProperty("jdbc.driver");
		db_url = pro.getProperty("jdbc.url");
		db_userName = pro.getProperty("jdbc.username");
		db_passWord = pro.getProperty("jdbc.password");
	}
	/**
	 * 获得连接
	 * @return
	 */
	public static Connection getConnection(){
		Connection con=null;
		//1.加载oracle驱动
		try {
			Class.forName(db_driver);
		} catch (ClassNotFoundException e) {
			logger.info(e);
			e.printStackTrace();
			return null;
		}
		//2.获得数据库连接
		try {
			con=DriverManager.getConnection(db_url,db_userName,db_passWord);
		} catch (SQLException e) {
			logger.info(e);
			e.printStackTrace();
			return null;
		}
			return con;
	}
	
	/**
	 * 关闭数据库资源
	 * @param con
	 * @param stmt
	 * @param rs
	 */
	public static void close(Connection con,Statement stmt,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				logger.info(e);
				e.printStackTrace();
			}
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.info(e);
				e.printStackTrace();
			}
		}
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				logger.info(e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭数据库资源
	 * @param con
	 * @param pstmt
	 * @param rs
	 */
	public static void close(Connection con,PreparedStatement pstmt,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				logger.info(e);
				e.printStackTrace();
			}
		}
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				logger.info(e);
				e.printStackTrace();
			}
		}
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				logger.info(e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭数据库资源
	 * @param con
	 */
	public static void close(Connection con){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				logger.info(e);
				e.printStackTrace();
			}
		}
	}
}
