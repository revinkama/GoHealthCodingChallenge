package com.gohealth.bugtracker.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.client.json.JsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.auth.oauth2.Credential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SheetsServiceTests {

    @Mock
    private CredentialsService credentialsService;

    @Mock
    private SpreadSheetStorageService spreadSheetStorageService;

    @Mock
    private Sheets sheets;

    @Mock
    private Sheets.Spreadsheets spreadsheets;

    @Mock
    private Sheets.Spreadsheets.Values values;

    @Mock
    private Sheets.Spreadsheets.Create createRequest;

    @InjectMocks
    private SheetsService sheetsService;

    @BeforeEach
    void setUp() throws GeneralSecurityException, IOException {
        JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        // Mock Credential
        Credential credential = mock(Credential.class);
        when(credentialsService.getCredentials(any(NetHttpTransport.class))).thenReturn(credential);

        // Mock Sheets service and its sub-components
        sheets = mock(Sheets.class);
        spreadsheets = mock(Sheets.Spreadsheets.class);
        values = mock(Sheets.Spreadsheets.Values.class);

        // Set up Sheets service
        lenient().when(sheets.spreadsheets()).thenReturn(spreadsheets);
        lenient().when(spreadsheets.values()).thenReturn(values);
    }

    @Test
    void createSheet_whenSpreadsheetExists_shouldReturnExistingId() throws GeneralSecurityException, IOException {
        String sheetName = "GoHealthIssueTracker";
        String existingSheetId = "existing_sheet_id";

        when(spreadSheetStorageService.spreadsheetExists(sheetName)).thenReturn(true);
        when(spreadSheetStorageService.getSpreadsheetId(sheetName)).thenReturn(existingSheetId);

        String result = sheetsService.createSheet();

        verify(spreadSheetStorageService, times(1)).spreadsheetExists(sheetName);
        verify(spreadSheetStorageService, times(1)).getSpreadsheetId(sheetName);
        verify(spreadsheets, never()).create(any(Spreadsheet.class));
        assertEquals(existingSheetId, result);
    }


// TODO:
//    @Test
//    void createSheet_whenSpreadsheetDoesNotExist_shouldCreateNewSheet() throws GeneralSecurityException, IOException {
//        String sheetName = "GoHealthIssueTracker";
//        String newSheetId = "new_sheet_id";
//        Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(sheetName));
//        Spreadsheet response = new Spreadsheet().setSpreadsheetId(newSheetId);
//
//        // Mock the Create request
//        createRequest = mock(Sheets.Spreadsheets.Create.class);
//
//        when(spreadSheetStorageService.spreadsheetExists(sheetName)).thenReturn(false);
//        when(spreadsheets.create(any(Spreadsheet.class))).thenReturn(createRequest);
//        when(sheetsService.executeSpreadSheet(sheets, spreadsheet)).thenReturn(any(Spreadsheet.class));
//
//        String result = sheetsService.createSheet();
//
//        verify(spreadSheetStorageService, times(1)).spreadsheetExists(sheetName);
//        verify(sheetsService, times(1)).executeSpreadSheet(any(Sheets.class), any(Spreadsheet.class));
//        assertEquals(newSheetId, result);
//    }


//    @Test
//    void addColumnsToSheet_shouldUpdateValues() throws GeneralSecurityException, IOException {
//        String spreadSheetId = "spreadsheet_id";
//        String sheetName = "Sheet1";
//        List<List<Object>> valuesList = Arrays.asList(
//                Arrays.asList("ID", "ParentID", "Description", "Status", "CreationTimestamp", "Link")
//        );
//        ValueRange body = new ValueRange().setValues(valuesList);
//        UpdateValuesResponse updateValuesResponse = new UpdateValuesResponse().setUpdatedCells(6);
//
//        when(values.update(eq(spreadSheetId), eq(sheetName + "!A1:F1"), eq(body)))
//                .thenReturn(mock(Sheets.Spreadsheets.Values.Update.class));
//        when(values.update(eq(spreadSheetId), eq(sheetName + "!A1:F1"), eq(body)).setValueInputOption(eq("RAW")))
//                .thenReturn(mock(Sheets.Spreadsheets.Values.Update.class));
//        when(values.update(eq(spreadSheetId), eq(sheetName + "!A1:F1"), eq(body)).setValueInputOption(eq("RAW")).execute())
//                .thenReturn(updateValuesResponse);
//
//        sheetsService.addColumnsToSheet(spreadSheetId);
//
//        verify(values, times(1)).update(eq(spreadSheetId), eq(sheetName + "!A1:F1"), eq(body));
//        assertEquals(6, updateValuesResponse.getUpdatedCells().intValue());
//    }



}
