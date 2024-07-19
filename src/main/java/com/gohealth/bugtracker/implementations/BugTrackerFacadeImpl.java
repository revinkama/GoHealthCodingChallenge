package com.gohealth.bugtracker.implementations;

import com.google.api.services.sheets.v4.Sheets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gohealth.bugtracker.interfaces.GoogleApiFacade;
import com.gohealth.bugtracker.services.SheetsService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

@Service
public class BugTrackerFacadeImpl implements GoogleApiFacade {

    private final SheetsService sheetsService;

    @Autowired
    public BugTrackerFacadeImpl(SheetsService sheetsService) {
        this.sheetsService = sheetsService;
    }

    @Override
    public String createSheet() throws GeneralSecurityException, IOException {
        return sheetsService.createSheet();
    }

    @Override
    public void addColumnsToSheet(String spreadSheetId) throws IOException, GeneralSecurityException {
        sheetsService.addColumnsToSheet(spreadSheetId);
    }

    @Override
    public int getLatestID(String spreadSheetId) throws GeneralSecurityException, IOException {
        return sheetsService.getLatestID(spreadSheetId);
    }

    @Override
    public void createIssue(Integer id, Scanner scanner, String spreadSheetId) throws IOException, GeneralSecurityException {
        Sheets sheets = sheetsService.getSheetsService();
        sheetsService.createIssue(id, scanner, sheets, spreadSheetId);
    }

    @Override
    public void closeIssue(Scanner scanner, String spreadSheetId) throws IOException, GeneralSecurityException {
        Sheets sheets = sheetsService.getSheetsService();
        sheetsService.closeIssue(scanner, sheets, spreadSheetId);
    }
}
