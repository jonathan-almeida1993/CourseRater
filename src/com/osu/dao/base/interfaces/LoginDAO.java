package com.osu.dao.base.interfaces;

import com.osu.dao.base.GenericDAO; 
import com.osu.database.pojo.UserPojo;

public interface LoginDAO extends GenericDAO {
	
	public UserPojo validateUserLogin(UserPojo loginDetails);
	
}
