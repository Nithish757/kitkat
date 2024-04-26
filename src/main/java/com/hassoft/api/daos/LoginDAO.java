package com.hassoft.api.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hassoft.api.dtos.UserLoginDTO;
import com.hassoft.api.utils.JdbcConnectionManager;

public interface LoginDAO {
	

	public UserLoginDTO validateLogin(UserLoginDTO login);
	public int getMobileVersion() throws Exception;
}
