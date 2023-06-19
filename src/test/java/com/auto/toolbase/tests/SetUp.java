package com.auto.toolbase.tests;

import com.auto.toolbase.commands.CLI;
import com.auto.toolbase.dataproviders.Data;
import com.taw.common.elements.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;

public class SetUp extends Actions {

    public CLI cli = new CLI();
    public String tool = null;
    protected static final String OS = $sys("os.name").toLowerCase();
    public List<Object[]> sequencedAllTestCases = null;

    @BeforeMethod(onlyForGroups = "regression", alwaysRun = true)
    public void set_up() {
        if (OS.contains("windows")) {
            tool = "cmd.exe /c";
        } else if (OS.contains("linux")) {
            tool = "/bin/bash -l -c";
        }
    }

    @BeforeTest
    public void loadSequncedAllTestCasedCsvFile() {
        sequencedAllTestCases = new ArrayList<>();
        Data data= new Data();
//        data.getSequencedCaseItems().forEachRemaining(sequencedAllTestCases::add);

    }

    protected static String $sys(String systemProperty) {
        return System.getProperty(systemProperty) != null ? System.getProperty(systemProperty) : null;
    }

}



