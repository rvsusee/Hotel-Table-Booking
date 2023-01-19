package com.htb.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionPooling {

	private static BasicDataSource dataSource = null;

	@Value("${spring.datasource.url}")
	private String URL;
	@Value("${spring.datasource.username}")
	private String USERNAME;
	@Value("${spring.datasource.password}")
	private String PASSWORD;
	@Value("${spring.datasource.driverClassName}")
	private String DRIVER;

	public BasicDataSource getDataSource() {
		if (dataSource != null) {
			return dataSource;
		} else {
			dataSource = new BasicDataSource();
			dataSource.setUrl(URL);
			dataSource.setUsername(USERNAME);
			dataSource.setPassword(PASSWORD);
			dataSource.setDriverClassName(DRIVER);
			dataSource.setMinIdle(5);
			dataSource.setMaxIdle(10);
			dataSource.setMaxTotal(25);
			return dataSource;
		}
	}

	public Connection getConnection() {
		BasicDataSource basicDataSource;
		try {
			basicDataSource = getDataSource();
			Connection conn = basicDataSource.getConnection();
			return conn;
		} catch (SQLException e) {
			System.out.println("Exception Occured : " + e.getMessage());
			return null;
		}
		
		
		
		
	}

}
