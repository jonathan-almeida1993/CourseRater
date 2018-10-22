package com.osu.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.osu.database.pojo.UserPojo;

public class DatabaseTests {
	Connection connection;
	UserPojo userPojo;
	
	//@BeforeTest(groups="database")
	public void setUserPojo() {
		System.out.println("Inside beforeTestMethod for database group!");
		userPojo = new UserPojo();
		
		userPojo.setFirstName("Kevin");
		userPojo.setLastName("Rhodes");
		userPojo.setType("");
		userPojo.setMajor("CS");
		//userPojo.setCreatedDate(new Date());
	}
	
	//@Test(groups="database")
	public void testDatabaseConnection() throws FileNotFoundException, SQLException, IOException, NamingException {
		/*Connection connection = DBConnectionFactory.getConnection();
		//Begin transaction
		connection.setAutoCommit(false);
		
		LoginDAO loginDAO = new LoginDAOImpl();
		loginDAO.validateUserLogin(userPojo);
		
		//if(loginDAO.get)*/
		
		System.out.println("Inside testDatabaseConnectionTest!");
		
		}
	
}
