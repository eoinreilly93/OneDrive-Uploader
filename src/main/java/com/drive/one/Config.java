package com.drive.one;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import de.tuberlin.onedrivesdk.OneDriveException;
import de.tuberlin.onedrivesdk.OneDriveSDK;

public class Config {
	
	private static Properties p = new Properties(System.getProperties());
	
	protected static void loadProperties() throws FileNotFoundException, IOException {

		try {
			FileInputStream propFile = new FileInputStream("src/main/resources/credentials.properties");
			p.load(propFile);		
			System.setProperties(p);
		}
		catch (FileNotFoundException e) {
			System.out.println("Properties file could not be found");
			throw e;
		}		
	}
	
	protected static void updateProperties(OneDriveSDK sdk) throws OneDriveException, IOException {
		
		try {
			FileOutputStream out = new FileOutputStream("credentials.properties");
			p.setProperty("refreshToken", sdk.getRefreshToken());
			p.store(out, null);
			out.close();
		}
		catch (IOException e) {
			System.out.println("Properties file could not be found");
			throw e;
		}		
	}
}
