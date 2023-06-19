package com.auto.toolbase.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CLI {


    public Process cli(String script, String command) {
        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec(script + " " + command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }

    public List<String> parseStream(InputStream ins) {
        String line = null;
        List<String> lines = new ArrayList<>();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while (true) {
            try {
                if ((line = in.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            lines.add(line);
        }
        return lines;
    }

    public Object stringToArrayByPipeChar(String commands) {

        ArrayList<String> commandList = new ArrayList();
        for (String command : commands.split("\\|\\|")) {
            commandList.add(command.trim());
        }
        return commandList;
    }

    public ArrayList<String[]> stringToArray(String string) {

        ArrayList<String[]> arrayList = new ArrayList<>();
        for (String keywords : string.split("\\|\\|")) {

            String[] array = keywords.split("::");

            arrayList.add(array);
        }
        return arrayList;
    }

    public ArrayList<ArrayList<String>> StringTo2DArrayOfExpectedkeywordsInTestCase(String keywordsString) {

        ArrayList<ArrayList<String>> rootkeywordList = new ArrayList<>();

        for (String keywords : keywordsString.split("\\|\\|")) {

            ArrayList<String> childKeywordList = new ArrayList();

            for (String command : keywords.split("::")) {
                childKeywordList.add(command.trim());
            }

            rootkeywordList.add(childKeywordList);

        }
        return rootkeywordList;
    }


}