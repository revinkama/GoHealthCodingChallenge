package com.gohealth.bugtracker.services;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SpreadSheetStorageService {

    private Map<String, String> spreadsheetStorage = new HashMap<>();

    public void storeSpreadsheetId(String name, String id) {
        spreadsheetStorage.put(name, id);
    }

    public String getSpreadsheetId(String name) {
        return spreadsheetStorage.get(name);
    }

    public boolean spreadsheetExists(String name) {
        return spreadsheetStorage.containsKey(name);
    }

}
