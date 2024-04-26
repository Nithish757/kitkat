package com.hassoft.api.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcConnectionManager {
	final static Logger logger = LoggerFactory.getLogger(JdbcConnectionManager.class);
	
	public static Connection getConnection(JdbcTemplate jdbcTemplate) throws Exception{
		return jdbcTemplate.getDataSource().getConnection();
	}
	public static void Close(Connection connection){
		try{
			connection.close();
		}catch(Exception e){
//			logger.debug("Connection close error in JdbcConnectionManager",e);
		}
	}
	public static void commit(Connection connection){
		try{
			connection.commit();
		}catch(Exception e){
	//		logger.debug("Connection close error in JdbcConnectionManager",e);
		}
	}
	
	public static void rollback(Connection connection){
		try{
			connection.rollback();
		}catch(Exception e){
		//	logger.debug("Connection close error in JdbcConnectionManager",e);
		}
	}
	
	public static void ClosePreparedStatement(PreparedStatement pstm){
		if(null != pstm)
			try{
				pstm.close();
			}catch(Exception e){}
	}
	public static void CloseResultSet(ResultSet rs){
		if(null != rs)
			try{
				rs.close();
			}catch(Exception e){}
	}
	
}
