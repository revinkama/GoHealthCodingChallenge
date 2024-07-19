package com.gohealth.bugtracker.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gohealth.bugtracker.services.SpreadSheetStorageService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SheetsService {

    private final CredentialsService credentialsService;
    private final SpreadSheetStorageService spreadsheetStorageService;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "Google Sheets Bug Tracker";

    @Autowired
    public SheetsService(CredentialsService credentialsService, SpreadSheetStorageService spreadsheetStorageService) {
        this.credentialsService = credentialsService;
        this.spreadsheetStorageService = spreadsheetStorageService;
    }
    public Sheets getSheetsService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentialsService.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String createSheet() throws GeneralSecurityException, IOException {
        Sheets service = getSheetsService();
        String sheetName = "GoHealthIssueTracker";

        // Check if the spreadsheet already exists
        if (spreadsheetStorageService.spreadsheetExists(sheetName)) {
            System.out.println("Spreadsheet with name " + sheetName + " already exists.");
            return spreadsheetStorageService.getSpreadsheetId(sheetName);
        }

        // Create a new spreadsheet
        Spreadsheet spreadsheet = new Spreadsheet()
                .setProperties(new SpreadsheetProperties()
                        .setTitle(sheetName));

        Spreadsheet response = executeSpreadSheet(service, spreadsheet);

        String spreadSheetId = response.getSpreadsheetId();

        spreadsheetStorageService.storeSpreadsheetId(sheetName, spreadSheetId);

        System.out.println("Spreadsheet ID: " + spreadSheetId);
        System.out.println("New sheet created in the spreadsheet.");

        return spreadSheetId;
    }

    public Spreadsheet executeSpreadSheet(Sheets service, Spreadsheet spreadsheet) throws IOException {
        return service.spreadsheets().create(spreadsheet)
                .execute();
    }

    public void addColumnsToSheet(String spreadSheetId) throws IOException, GeneralSecurityException {
        String sheetName = "Sheet1";
        List<List<Object>> values = Arrays.asList(
                Arrays.asList("ID", "ParentID", "Description", "Status", "CreationTimestamp", "Link")
        );

        ValueRange body = new ValueRange()
                .setValues(values);

        UpdateValuesResponse result = getSheetsService().spreadsheets().values()
                .update(spreadSheetId, sheetName + "!A1:F1", body)
                .setValueInputOption("RAW")
                .execute();

        System.out.printf("%d cells updated.\n", result.getUpdatedCells());
    }

    public int getLatestID(String spreadSheetId) throws GeneralSecurityException, IOException {
        final String range = "A:F";
        Sheets service = getSheetsService();
        ValueRange response = service.spreadsheets().values()
                .get(spreadSheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        Integer id = 0;
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                if (!row.get(0).equals("ID")) id = Integer.parseInt(row.get(0).toString().split("-")[1]);
            }
        }

        return id;
    }

    public void createIssue(Integer id, Scanner scanner, Sheets sheets, String spreadSheetId) throws IOException {
        System.out.print("Enter parent issue ID: ");
        String parentId = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter link (URL to the log): ");
        String link = scanner.nextLine();
        String issueId = "I-" + id;
        String creationTimestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(new Date());

        List<List<Object>> values = Collections.singletonList(
                Arrays.asList(issueId, parentId, description, "Open", creationTimestamp, link)
        );

        System.out.println(issueId);
        ValueRange body = new ValueRange().setValues(values);
        sheets
            .spreadsheets().values()
            .append(spreadSheetId, "A:F", body)
            .setValueInputOption("RAW")
            .execute();

        System.out.println("New issue created with ID: " + issueId);
    }

    public void closeIssue(Scanner scanner, Sheets sheets, String spreadSheetId) throws IOException {
        System.out.print("Enter issue ID to close: ");
        String issueId = scanner.nextLine();

        ValueRange response = sheets.spreadsheets().values()
                .get(spreadSheetId, "A:F")
                .execute();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No issues found.");
            return;
        }

        int rowIndex = -1;
        for (int i = 0; i < values.size(); i++) {
            List<Object> row = values.get(i);
            if (row.size() > 0 && row.get(0).equals(issueId)) {
                rowIndex = i;
                break;
            }
        }

        if (rowIndex == -1) {
            System.out.println("Issue ID not found.");
            return;
        }

        List<List<Object>> updateValues = Collections.singletonList(
                Arrays.asList("Closed")
        );
        ValueRange body = new ValueRange().setValues(updateValues);

        sheets.spreadsheets().values()
            .update(spreadSheetId, "!D" + (rowIndex + 1), body)
            .setValueInputOption("RAW")
            .execute();

        System.out.println("Issue " + issueId + " has been closed.");
    }
}
