package com.safelite.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class Property {

	static Properties properties;
	String strValue;
	public static final String FILE_NAME = "config.properties";

	static {
		File f = new File(FILE_NAME);
		properties = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(f);
			properties.load(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getProperty(String strKey) {
		try{
			File f = new File(FILE_NAME);
			if(f.exists()){

				strValue=properties.getProperty(strKey);

			}
			else
				System.out.println("File not found!");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return strValue;
	}


	// return environmental details
	public String getHostName() throws UnknownHostException{
		InetAddress addr = InetAddress.getLocalHost();
		String hostname = addr.getHostName();

		return hostname;
	}
}
