package com.hassoft.api.services;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hassoft.api.daos.LoginDAO;
import com.hassoft.api.dtos.UserLoginDTO;

@Service(value = "loginService")
public class LoginService {

	final static Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private LoginDAO loginDAO;

	public UserLoginDTO getLoginValidate(UserLoginDTO login, HttpSession session) throws Exception {

		try {

			if (login.getUserName().equalsIgnoreCase("")) {
				throw new Exception("User Name should not be blank");
			}
			if (login.getUserPassword().equals("")) {
				throw new Exception("Password should not be blank");
			}
			//if (login.getUserPassword().equals("welcome2")) {
				// session.setAttribute("userId", login.getUserID());
				// response.sendRedirect(response.encodeURL("USProduct/configuration/changePassword.jsp"));
				//int version = loginDAO.getMobileVersion();
				UserLoginDTO val = loginDAO.validateLogin(login);
				//val=val+"|"+version;
				return val;
		} catch (Exception e) {
//			logger.debug("Error", e);
		}
		return null;
	}
}
