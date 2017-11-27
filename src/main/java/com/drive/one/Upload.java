package com.drive.one;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.tuberlin.onedrivesdk.OneDriveException;
import de.tuberlin.onedrivesdk.OneDriveSDK;
import de.tuberlin.onedrivesdk.folder.OneFolder;
import de.tuberlin.onedrivesdk.uploadFile.OneUploadFile;

public class Upload {
	
	private static ExecutorService executor = Executors.newFixedThreadPool(5);
	private static OneFolder currentFolder;
	
	protected static boolean uploadFile(File filename, OneDriveSDK sdk) throws IOException, OneDriveException {
		
		try {
			currentFolder = sdk.getRootFolder();
			OneUploadFile upload = currentFolder.uploadFile(filename);
			
			executor.submit(upload);			
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
}
