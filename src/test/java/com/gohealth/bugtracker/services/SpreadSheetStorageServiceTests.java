package com.gohealth.bugtracker.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpreadSheetStorageServiceTests {

    @Mock
    private SpreadSheetStorageService spreadSheetStorageService;

    @Mock
    private Map<String, String> spreadsheetStorage;

    @Test
    void givenSpreadSheetNameAndId_storeSpreadSheet() {
        spreadSheetStorageService = mock(SpreadSheetStorageService.class);
        spreadSheetStorageService.storeSpreadsheetId("GOOGLE SHEETS ISSUE TRACKER", "RANDOM_UID");
        assertNotNull(spreadsheetStorage);
    }

    @Test
    void givenSpreadSheetId_getSpreadSheetId() {
        spreadSheetStorageService = mock(SpreadSheetStorageService.class);
        when(spreadSheetStorageService.getSpreadsheetId("GOOGLE SHEETS ISSUE TRACKER")).thenReturn("RANDOM_UID");
        String spreadSheetId = spreadSheetStorageService.getSpreadsheetId("GOOGLE SHEETS ISSUE TRACKER");

        assertNotNull(spreadSheetId);
    }

    @Test
    void givenName_SpreadSheetExistsInMap() {
        spreadSheetStorageService = mock(SpreadSheetStorageService.class);
        when(spreadSheetStorageService.spreadsheetExists("GOOGLE SHEETS ISSUE TRACKER")).thenReturn(true);

        assertTrue(spreadSheetStorageService.spreadsheetExists("GOOGLE SHEETS ISSUE TRACKER"));
    }

    @Test
    void givenName_SpreadSheetDoesNotExistInMap() {
        spreadSheetStorageService = mock(SpreadSheetStorageService.class);
        when(spreadSheetStorageService.spreadsheetExists("GOOGLE SHEETS ISSUE TRACKER")).thenReturn(false);

        assertFalse(spreadSheetStorageService.spreadsheetExists("GOOGLE SHEETS ISSUE TRACKER"));
    }
}
