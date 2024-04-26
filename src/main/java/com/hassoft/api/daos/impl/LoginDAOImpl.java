package com.hassoft.api.daos.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hassoft.api.daos.LoginDAO;
import com.hassoft.api.dtos.UserLoginDTO;
import com.hassoft.api.utils.JdbcConnectionManager;



@Repository
@Qualifier("loginDAO")
public class LoginDAOImpl implements LoginDAO, Serializable {

	final static Logger logger = LoggerFactory.getLogger(LoginDAOImpl.class);
	private static final long serialVersionUID = -4944936972291467011L;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserLoginDTO validateLogin(UserLoginDTO login) {
	try {
		System.out.println("krishnamurtt"+login.getLongitude());
		System.out.println("krishnamurtt"+login.getLongitude());
		System.out.println("krishnamurtt"+login.getLatitude());
		
		
		
			String query = "SELECT IFNULL(hr.h_reg_code,'')AS h_reg_code,hu.h_usr_id AS h_usr_id,hu.h_usr_orgId AS h_usr_orgId,hu.h_usr_loginId,hr.h_reg_databasename  FROM hasdb.has_registrations AS hr INNER JOIN hasdb.has_users AS hu ON(hu.h_usr_orgId=hr.h_reg_id)  WHERE hu.h_usr_loginid='"+login.getUserName()+"' AND hu.h_usr_loginpwd=MD5('"+login.getUserPassword()+"') AND hr.h_reg_orgcat!=3";
			//,o_usr_fn,o_usr_ln LEFT OUTER JOIN support.org_users ON o_usr_id=h_usr_id
		 	Map<String, Object> row = jdbcTemplate.queryForMap(query);
		 	Connection conn = null;
		 	conn = JdbcConnectionManager.getConnection(jdbcTemplate);
		 	ResultSet result = null;
		 	PreparedStatement pstmt=null;
			if(row.size()>0){
				int orgid = Integer.parseInt(row.get("h_usr_orgId").toString());
				
				if (orgid == 1){
					pstmt = conn.prepareStatement("select o_usr_fn,o_usr_ln from support.org_users where o_usr_id= ?");
							pstmt.setString(1,row.get("h_usr_id").toString());
							result = pstmt.executeQuery();
							
							login.setOrgid(row.get("h_usr_orgId").toString());
							login.setUserId(row.get("h_usr_id").toString());
				if(result.next()){
						login.setDistrict(result.getString("o_usr_ln"));//it is last name stored as district name
						login.setFirstName(result.getString("o_usr_fn"));
						login.setType("7");
				}
				else{
					login.setDistrict("");//it is last name stored as district name
					login.setFirstName("");
					login.setType("7");
				}
				}
				else{
					pstmt = conn.prepareStatement("select o_usr_fn,o_usr_ln,o_usr_type from org_users where o_usr_id= ?");
					pstmt.setString(1,row.get("h_usr_id").toString());
					
					result = pstmt.executeQuery();
					
					login.setOrgid(row.get("h_usr_orgId").toString());
					login.setUserId(row.get("h_usr_id").toString());
					if(result.next()){
						login.setDistrict(result.getString("o_usr_ln"));//it is last name stored as district name
						login.setFirstName(result.getString("o_usr_fn"));
						login.setType(result.getString("o_usr_type"));
		}
		else{
			login.setDistrict("");//it is last name stored as district name
			login.setFirstName("");
			login.setType("");
		}	
				}
				login.setVersion(getMobileVersion());
				return login;
				
						//row.get("h_usr_orgId").toString()+"|"+row.get("h_usr_id").toString();
			
			}else{
				return null;
				}
			
		} catch (Exception e) {
		System.out.println("error");
			//logger.debug("Error ",e);
		}

		return null;
	}
	public int getMobileVersion() throws Exception {
		Connection conn = null;
		String versionQuery = "select ver_no from mobileversion where ver_status is true";
		conn = JdbcConnectionManager.getConnection(jdbcTemplate);
		PreparedStatement pst = conn.prepareStatement(versionQuery);
		ResultSet rs = pst.executeQuery();
	    rs.next();
	    int result = rs.getInt("ver_no");
	    try{
	    	rs.close();
	    conn.close();
	    }catch(Exception e){}
		return result;
	}
}
