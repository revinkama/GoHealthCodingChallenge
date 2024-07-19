package com.gohealth.bugtracker.implementations;

import com.gohealth.bugtracker.services.SheetsService;
import com.google.api.services.sheets.v4.Sheets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BugTrackerFacadeImplTests {

    @Mock
    private SheetsService sheetsService;

    @Autowired
    private BugTrackerFacadeImpl bugTrackerFacade;

    @BeforeEach
    void init() {
        sheetsService = mock(SheetsService.class);
        bugTrackerFacade = new BugTrackerFacadeImpl(sheetsService);
    }

    @Test
    void createSheet_returnSheetId() throws GeneralSecurityException, IOException {
        when(sheetsService.createSheet()).thenReturn("new_sheet_id");

        String sheetId = bugTrackerFacade.createSheet();

        verify(sheetsService, times(1)).createSheet();
        assertNotNull(sheetId);
    }

    @Test
    void givenSpreadSheetId_addColumnToSheet() throws GeneralSecurityException, IOException {
        bugTrackerFacade.addColumnsToSheet("sheet_id");

        verify(sheetsService, times(1)).addColumnsToSheet("sheet_id");
    }

    @Test
    void givenSpreadSheetId_getTheLatestIssueId() throws GeneralSecurityException, IOException {
        int id = bugTrackerFacade.getLatestID("sheet_id");

        verify(sheetsService, times(1)).getLatestID("sheet_id");
        assertNotNull(id);
    }

    @Test
    void givenIdAndSpreadSheetIdAndScanner_createIssue() throws GeneralSecurityException, IOException {
        Integer id = 007;
        String spreadSheetId = "sheet_id";
        Scanner scanner = new Scanner(System.in);
        Sheets sheets = mock(Sheets.class);

        when(sheetsService.getSheetsService()).thenReturn(sheets);

        bugTrackerFacade.createIssue(id, scanner, spreadSheetId);

        verify(sheetsService, times(1)).getSheetsService();
        verify(sheetsService, times(1)).createIssue(id, scanner, sheets, spreadSheetId);
    }


}
