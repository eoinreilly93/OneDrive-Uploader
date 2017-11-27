package com.drive.one;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import de.tuberlin.onedrivesdk.OneDriveFactory;
import de.tuberlin.onedrivesdk.OneDriveSDK;
import de.tuberlin.onedrivesdk.common.OneDriveScope;

public class Main {
	
	private static Scanner in = new Scanner(System.in);
	private static OneDriveSDK sdk;
	
	public static void main(String[] args) throws Exception,FileNotFoundException {

		Config.loadProperties();
		final String refreshToken = System.getProperty("refreshToken");
		final String clientId = System.getProperty("clientId");
		final String clientSecret = System.getProperty("clientSecret");
		final String filename = args[0];
		final File f = new File(filename);
		
		if(!f.isFile()) {
			System.out.println("The chosen file to upload does not exist");
			System.exit(0);
		}
	
		try {
			sdk = OneDriveFactory.createOneDriveSDK(clientId, clientSecret, OneDriveScope.READWRITE, OneDriveScope.OFFLINE_ACCESS);
			System.out.println("Attempting to authenticate with refresh token...");
			sdk.authenticateWithRefreshToken(refreshToken);
			
			if(Upload.uploadFile(f, sdk)) {
				System.out.println("The file has been uploaded to OneDrive");
			}
			else {
				System.out.println("There was an error uploading the file");
			}
		}		
		catch (Exception e) {
			System.out.println("Failed to authenticate with refresh token, please create another access token");
			System.out.println("Please go to the link below to get a new access token: \n" + sdk.getAuthenticationURL());
			System.out.println("\nEnter the new access token: ");
			in = new Scanner(System.in);
			String authCode = in.nextLine();
			
			try {
				System.out.println("Authenticating code...");
				sdk.authenticate(authCode);				
				
				if(sdk.isAuthenticated()) {
					System.out.println("Authentication complete. Saving refresh token to properties file");					
					Config.updateProperties(sdk);
				}
			}
			catch (Exception ex) {
				System.out.println("Error authenticating code");
				throw ex;
			}			
		}
		finally {
			sdk.disconnect();
			System.out.println("Disconnected");
			System.exit(0);
		}	
	}
}
