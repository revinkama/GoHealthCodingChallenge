# GoHealthCodingChallenge
This point of this challenge/code is to communicate with the Google Sheets API through a cli. Users of this code can update issues created on the GoHealthIssueTracker sheet. The application initilizes the creation of the sheet, and users worry about adding issues to it. Users can also close an issue, via issue id, which will update the status column of that issue id to ```Closed```.

This code can be run using docker, or locally using run configurations. *** This code needs the google-sheets-client-secrets.json copy/pasted into src/main/java/resources ***

# Docker Commands:
In order to run docker make sure you first run ```mvn clean package``` to create the jar files. These jar files will get create in the target package on the root project. 

Once that is done, run these two commands. 

BUILD:
```docker build -t bugtracker-app .```

RUN:
```docker run -it --rm -p 8080:8080 --name bugtracker-app-instance bugtracker-app run```

# Local Run: 
Go to your run configurations of intellij, sts, etc... Make sure you select the BugtrackerApplication as the runner. Then you need to add ```run``` inside the program arguments. Run as a program argument is needed to distinguish between a build and an actual run. 

# When running application:
- users will be prompted to access a link, in order to sign in via OAuth2.
  - once signing on is done, the application will receive the tokens package, this is used so that googles api knows the user, and will accept requests with that token. 
- users will be prompted with questions about what they would like to do today.
  1. Create a new issue.
  2. Close an existing issue.
  3. Exit.
 
# Pictures of code in action:
Docker Build:
![Screenshot 2024-07-19 at 3 30 27 PM](https://github.com/user-attachments/assets/56124ed4-4764-49fb-93c0-9874b3a98186)

Docker Run:
![Screenshot 2024-07-19 at 3 30 57 PM](https://github.com/user-attachments/assets/03cfacd6-4153-4249-b7aa-379102ebafb9)

OAuth Verification Via Link:
![Screenshot 2024-07-19 at 3 31 39 PM](https://github.com/user-attachments/assets/0dfdc44d-c345-4ba0-a390-fb6a9574a5c1)

Request input from code:
![Screenshot 2024-07-19 at 3 32 28 PM](https://github.com/user-attachments/assets/75ffca6d-0618-454f-a3b4-a7099d8f114c)

Issues created by me:
![Screenshot 2024-07-19 at 3 33 07 PM](https://github.com/user-attachments/assets/2f4fcda8-f9fd-4325-b246-3659f6f8becd)



