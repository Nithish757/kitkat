package com.hassoft.spring;

import javax.servlet.jsp.jstl.core.Config;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.hassoft.api.utils.DBConfig;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.hassoft.api")
@Import(value = { CustomWebSecurityConfigurerAdapter.class })
public class AppConfig extends WebMvcConfigurerAdapter {
	final static Logger logger = LoggerFactory.getLogger(Config.class);

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	// @Bean
	// public InternalResourceViewResolver viewResolver() {
	// logger.info("view Resolver");
	// InternalResourceViewResolver viewResolver
	// = new InternalResourceViewResolver();
	// viewResolver.setViewClass(JstlView.class);
	// viewResolver.setPrefix("/WEB-INF/views/");
	// viewResolver.setSuffix(".jsp");
	// return viewResolver;
	// }
	@Bean
	public DataSource getDataSource() {
		
		//logger.info("database initialization" + DBConfig.get("db.driver.class.name"));
		DriverManagerDataSource dataSource = null;
		try {
			dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(DBConfig.get("db.driver.class.name"));
			dataSource.setUrl(DBConfig.get("db.url"));
			dataSource.setUsername(DBConfig.get("db.login.name"));
			dataSource.setPassword(DBConfig.get("db.login.password"));
			
		} catch (Exception e) {
			//logger.error("Error", e);
		}
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = null;
		try {
			jdbcTemplate = new JdbcTemplate();
			jdbcTemplate.setDataSource(getDataSource());
		} catch (Exception e) {
	//		logger.error("Error", e.getMessage());
		}
		return jdbcTemplate;

	}

}
