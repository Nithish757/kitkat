package com.hassoft.api.utils;

import java.util.Properties;

import javax.servlet.jsp.jstl.core.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConfig {
	private static String _stHasSoftPropFileName = "hassoft.properties";

	private static Properties _hassoftProps = new Properties();
	final static Logger logger = LoggerFactory.getLogger(DBConfig.class);

	static {
		//System.out.println("start read");
		
		read();
	}

	public static void setPropFile(Properties acurianProps) {

		_hassoftProps = acurianProps;
	}

	public void Config() {
	}

	public void Config(String fileName) {
		_stHasSoftPropFileName = fileName;
	}

	public static String get(String pName) {
		return _hassoftProps.getProperty(pName, "").trim();
	}

	public static Properties getAll() {
		return _hassoftProps;
	}

	private static void read() {

		try {
			_hassoftProps.load(Config.class.getClassLoader().getResourceAsStream(_stHasSoftPropFileName));
//			logger.info(_stHasSoftPropFileName);
	
		} catch (Exception e) {
	
	//		logger.error("could not find the properties file!! " + e.getMessage());
		}
	}
	public static void main(String...strings ){
		DBConfig conf = new DBConfig();
	}

}
