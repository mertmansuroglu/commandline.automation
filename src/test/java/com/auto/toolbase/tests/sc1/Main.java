package com.auto.toolbase.tests.sc1;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello world!");
        Main main = new Main();
        String commandString = "dir || hostname || hostmane";
        String expectedKeywordList = "type :: HELP :: command-name :: For more || not recognized :: batch file :: operable program || Version :: Windows ||  Wireless ::  LAN :: Gateway";
        System.out.println("*** Command List ***");
        for (String command : main.stringToArrayByPipeChar(commandString)) {
            System.out.println(command);
        }

        System.out.println("*** Keyword List ***");
        for (ArrayList<String> keywordList : main.StringTo2DArrayOfExpectedkeywordsInTestCase(expectedKeywordList)) {
            System.out.println(keywordList);
        }
    }

    public ArrayList<String> stringToArrayByPipeChar(String commands) {

        ArrayList<String> commandList = new ArrayList();
        for (String command : commands.split("\\|\\|")) {
            commandList.add(command.trim());
        }
        return commandList;
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

