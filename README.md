# OneDrive-Upload
This project is a simple implementation of Tawalaya's Java SDK for the OneDrive API (https://github.com/tawalaya/OneDriveJavaSDK)
It should be compiled to a runnable jar file which allows a server to access a OneDrive account without prompting for authentication. It takes one argument (the file being uploaded), and uploads it to the root directory of the OneDrive.
Preconidtions to it running successfully:
	1) You need to have created a microsoft app at https://apps.dev.microsoft.com
	2) Provide the client id and client secret of this app in the credentials.properties file
	3) The jar must be run once before being deployed to the server
		3.1) This will prompt the user to go to a link to retrieve an access token. 
		3.2) Once the token is retrieved, enter it in the command line and the jar will automatically store the refresh token in the properties file
	4) The jar can now be deployed to the server and files can be provided to it which will be uploaded to OneDrive without requesting user authorization
Note: The refresh token expires after 1 year, at which point the credentials.properties file will need to be updated with the new refresh token 
