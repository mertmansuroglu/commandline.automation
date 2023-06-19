package com.auto.toolbase.dataproviders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DataSources {

    public static Iterator<Object[]> json(String sourceFile) {
        ArrayList<Object[]> data = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(sourceFile)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray testCaseList = (JSONArray) obj;
            testCaseList.forEach(tCase -> {
                String[] testCaseItem = new String[5];
                JSONObject testCaseObject = (JSONObject) tCase;
                testCaseItem[0] = (String) testCaseObject.get("TestCaseID");
                testCaseItem[1] = (String) testCaseObject.get("Description");
                testCaseItem[2] = (String) testCaseObject.get("CMD_Command");
                testCaseItem[3] = (String) testCaseObject.get("Expected_Keywords_In_the_Outputs");
                testCaseItem[4] = (String) testCaseObject.get("Log_Type");
                data.add(testCaseItem);
                data.add(testCaseItem);
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data.iterator();
    }


}
