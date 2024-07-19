package com.gohealth.bugtracker;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import com.gohealth.bugtracker.implementations.BugTrackerFacadeImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class BugtrackerApplication implements CommandLineRunner {

	private static String spreadSheetId = "";

	@Autowired
	private final BugTrackerFacadeImpl bugTrackerFacade;

	@Autowired
	public BugtrackerApplication(BugTrackerFacadeImpl bugTrackerFacade) {
		this.bugTrackerFacade = bugTrackerFacade;
	}

	public static void main(String... args) {
		SpringApplication.run(BugtrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws GeneralSecurityException, IOException {
		if (args.length > 0 && "run".equals(args[0])) {
			this.spreadSheetId = bugTrackerFacade.createSheet();
			bugTrackerFacade.addColumnsToSheet(this.spreadSheetId);
			runApplication();
		} else {
			System.out.println("Application built successfully.");
		}
	}

	public void runApplication() throws IOException, GeneralSecurityException {
		while (true) {
			Integer id = this.bugTrackerFacade.getLatestID(this.spreadSheetId);

			Scanner scanner = new Scanner(System.in);
			System.out.println("What would you like to do today?");
			System.out.println("1. Create a new issue");
			System.out.println("2. Close an existing issue");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1:
					bugTrackerFacade.createIssue(id + 1, scanner, this.spreadSheetId);
					break;
				case 2:
					bugTrackerFacade.closeIssue(scanner, this.spreadSheetId);
					break;
				case 3:
					System.exit(0);
				default:
					System.out.println("Invalid choice, please try again.");
			}
		}
	}

}
