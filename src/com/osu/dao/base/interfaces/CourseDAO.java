package com.osu.dao.base.interfaces;

import java.util.ArrayList;

import com.osu.dao.base.GenericDAO;
import com.osu.database.pojo.CoursePojo;

public interface CourseDAO extends GenericDAO {
	
	public ArrayList<CoursePojo> fetchDepartments();
	
	public ArrayList<CoursePojo> fetchCourses(CoursePojo obj);
	
	public ArrayList<CoursePojo> fetchTerm(CoursePojo obj);
	
	public ArrayList<CoursePojo> fetchInstructors(CoursePojo obj);
	
	public ArrayList<CoursePojo> fetchCourseId(CoursePojo obj);
	
	public CoursePojo fetchCourseDetails(CoursePojo obj);
	
}
