package com.htb.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionPooling {

	Logger logger = LogManager.getLogger("HotelTableBooking");

	private static BasicDataSource dataSource = null;

	@Value("${spring.datasource.url}")
	private String URL;
	@Value("${spring.datasource.username}")
	private String USERNAME;
	@Value("${spring.datasource.password}")
	private String PASSWORD;
	@Value("${spring.datasource.driverClassName}")
	private String DRIVER;

	private BasicDataSource getDataSource() {
		if (dataSource != null) {
			logger.info("Datasource is Already Established");
			return dataSource;
		} else {
			logger.info("Datasource is Not Established");
			logger.info("New Datasource is Creating...");
			dataSource = new BasicDataSource();
			dataSource.setUrl(URL);
			dataSource.setUsername(USERNAME);
			dataSource.setPassword(PASSWORD);
			dataSource.setDriverClassName(DRIVER);
			dataSource.setMinIdle(5);
			dataSource.setMaxIdle(10);
			dataSource.setMaxTotal(25);
			logger.info("Datasource created Successfully");
			return dataSource;
		}
	}

	public Connection getConnection() {
		BasicDataSource basicDataSource;
		try {
			basicDataSource = getDataSource();
			logger.info("Datasoruce Access - Success");
			Connection conn = basicDataSource.getConnection();
			logger.info("Connection Object Access - Success");
			return conn;
		} catch (SQLException e) {
			logger.fatal("Database Error : " + e.getLocalizedMessage());
			return null;
		} catch (Exception e) {
			logger.fatal("Exception Occured : " + e.getLocalizedMessage());
			return null;			
		}
	}
}
