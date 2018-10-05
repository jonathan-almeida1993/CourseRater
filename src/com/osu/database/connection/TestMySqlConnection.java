package com.osu.database.connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;

public class TestMySqlConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		java.sql.Connection connection = null;
		try {
			connection = DBConnectionFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		if (connection != null) {
			System.out.println("@--------------- Connection Achieved !!! ------------@");
		} else {
			System.out.println("@--------------- Connection Failed !!! ------------@");
		}

	}
}
