# GoHealthCodingChallenge
This point of this challenge/code is to communicate with the Google Sheets API through a cli. Users of this code can update issues created on the GoHealthIssueTracker sheet. The application initilizes the creation of the sheet, and users worry about adding issues to it. Users can also close an issue, via issue id, which will update the status column of that issue id to ```Closed```.

This code can be run using docker, or locally using run configurations. \n

# Docker Commands:
In order to run docker make sure you first run ```mvn clean package``` to create the jar files. These jar files will get create in the target package on the root project. 

Once that is done, run these two commands. 
BUILD:
```docker build -t bugtracker-app .```

RUN:
```docker run -it --rm -p 8080:8080 --name bugtracker-app-instance bugtracker-app run```

# Local Run: 
Go to your run configurations of intellij, sts, etc... Make sure you select the BugtrackerApplication as the runner. Then you need to add ```run``` inside the program arguments. Run as a program argument is needed to distinguish between a build and an actual run. 
