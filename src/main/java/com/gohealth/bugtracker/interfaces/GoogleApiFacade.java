package com.gohealth.bugtracker.interfaces;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

public interface GoogleApiFacade {
    String createSheet() throws GeneralSecurityException, IOException;
    void addColumnsToSheet(String spreadSheetId) throws IOException, GeneralSecurityException;
    int getLatestID(String spreadSheetId) throws GeneralSecurityException, IOException;
    void createIssue(Integer id, Scanner scanner, String spreadSheetId) throws IOException, GeneralSecurityException;
    void closeIssue(Scanner scanner, String spreadSheetId) throws IOException, GeneralSecurityException;
}
