package com.lnt.framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LayersM {

	String sourcepath;
	String targetpath;
	String tardel;
	String sourcedel;
	String arrayDel;
	String source_table_name;
	String target_table_name;
	InputStream inputStream;
	String sp;

	public void getPropValues() {

		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";
			
			inputStream = new FileInputStream("./resources/config.properties");

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			// get the property value and print it out
			sourcepath = prop.getProperty("sourcepath");
			targetpath = prop.getProperty("targetpath");
			tardel = prop.getProperty("tardel");
			sourcedel = prop.getProperty("sourcedel");
			arrayDel = prop.getProperty("arrayDel");
			source_table_name = prop.getProperty("source_table_name");
			target_table_name = prop.getProperty("target_table_name");
			sp = prop.getProperty("sp");
			

		}

		catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}

	}

}
