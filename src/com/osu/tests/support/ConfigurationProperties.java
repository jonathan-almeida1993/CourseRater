package com.osu.tests.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigurationProperties {

	private static Properties properties;

	static {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(new File(System.getProperty("user.dir")+"//test.properties")));
		} 
		//propertiesConfiguration.load(in);
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setPropertiesFile(String filePath) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(System.getProperty("user.dir")+filePath)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Object> getProperties(){
		List<Object> props = new ArrayList<Object>();
		
		while(properties.entrySet().iterator().hasNext()) {
			props.add(properties.entrySet().iterator().next());
		}
		
		return props;
	}
	
	public static String getProperty(String key) {
		return properties.getProperty(key).toString();
	}

}
