package com.osu.database.connection;

import java.io.FileNotFoundException; 
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionFactory {

	public static Connection getConnection() throws SQLException, FileNotFoundException, IOException, NamingException {
		Connection conn = null;

		// Get DataSource
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:/comp/env");
		DataSource dataSource = (DataSource) envContext.lookup("jdbc/dbConForM101FeedbackNew");
		if ((conn == null) || conn.isClosed()) {
			conn = dataSource.getConnection();
		}

		return conn;
	}


	public static void close(ResultSet resultSet, PreparedStatement statement, Connection connect) {
		try {

			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			System.out.println("ERROR in DBCONNECTION FACTORY--"+e.getMessage());
		}
	}

}
