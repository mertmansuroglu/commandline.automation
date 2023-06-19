package com.auto.toolbase.setUp;

import com.auto.toolbase.commands.CLI;
import com.taw.common.elements.Actions;
import org.testng.annotations.BeforeMethod;

import java.util.List;

public class SetUp extends Actions {

    protected static final String OS = $sys("os.name").toLowerCase();
    public CLI cli = new CLI();
    public String tool = null;
    public List<Object[]> sequencedAllTestCases = null;

    protected static String $sys(String systemProperty) {
        return System.getProperty(systemProperty) != null ? System.getProperty(systemProperty) : null;
    }

    @BeforeMethod(onlyForGroups = "regression", alwaysRun = true)
    public void set_up() {
        if (OS.contains("windows")) {
            tool = "cmd.exe /c";
        } else if (OS.contains("linux")) {
            tool = "/bin/bash -l -c";
        }
    }

}



