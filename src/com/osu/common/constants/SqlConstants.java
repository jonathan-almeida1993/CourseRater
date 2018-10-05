package com.osu.common.constants;

public interface SqlConstants {

	String AUTHENTICATE_USER = "SELECT onid, first_name, last_name, type, class_standing, major, created_date "+
			"FROM user_master_tbl where onid = ? and password = ?";

}
