package com.osu.dao.base.impl;

import java.io.FileNotFoundException; 
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.osu.common.constants.CommonConstants;
import com.osu.common.constants.SqlConstants;
import com.osu.dao.base.interfaces.LoginDAO;
import com.osu.database.connection.DBConnectionFactory;
import com.osu.database.pojo.UserPojo;

public class LoginDAOImpl implements LoginDAO {

	Connection connection = null;

	@Override
	public Connection getConnection() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException, NamingException {
		if ((connection == null) || (connection.isClosed())) {
			connection = DBConnectionFactory.getConnection();
		}
		return connection;
	}
	
	public UserPojo validateUserLogin(UserPojo loginDetails){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UserPojo userDetails = new UserPojo();
		userDetails.setStatus(CommonConstants.STATUS_JDBC_ERROR);
		
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SqlConstants.AUTHENTICATE_USER);
			preparedStatement.setString(1, loginDetails.getOnid());
			preparedStatement.setString(2, loginDetails.getPassword());
			resultSet = preparedStatement.executeQuery();
			int count = 0;
		
			while(resultSet.next()){
				userDetails.setFirstName(resultSet.getString("first_name"));
				userDetails.setLastName(resultSet.getString("last_name"));
				userDetails.setType(resultSet.getString("type"));
				userDetails.setClassStanding(resultSet.getString("class_standing"));
				userDetails.setMajor(resultSet.getString("major"));
				count++;
			}
			
			if(count == 0){
				userDetails.setStatus(CommonConstants.STATUS_AUTH_FAILED);
			}else if(count == 1){
				userDetails.setStatus(CommonConstants.STATUS_AUTH_SUCCESS);
			}
			
		} catch (Exception e) {
			userDetails.setStatus(CommonConstants.STATUS_JDBC_ERROR);
			e.printStackTrace();
		} finally {
			DBConnectionFactory.close(resultSet, preparedStatement, connection);
		}

		return userDetails;
	}
}
