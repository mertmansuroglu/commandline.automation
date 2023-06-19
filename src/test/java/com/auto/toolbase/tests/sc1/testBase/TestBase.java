package com.auto.toolbase.tests.sc1.testBase;

import com.auto.toolbase.dataproviders.Data;
import com.auto.toolbase.setUp.SetUp;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBase extends SetUp {


    @Test(dataProvider = "commandsFromJSON", dataProviderClass = Data.class, groups = {"regression"})
    public void testMethod(String testCaseID, String testCaseDescription, String cmdCommands, String ExpectedKeywordsInOutput, String logType) {
        ArrayList<String[]> commandList = cli.stringToArray(cmdCommands);
        ArrayList<String[]> expectedKeywordList = cli.stringToArray(ExpectedKeywordsInOutput);
        ArrayList<String[]> logTypeList = cli.stringToArray(logType);

        if (!(commandList.size() == expectedKeywordList.size() && expectedKeywordList.size() == logTypeList.size())) {
            throw new RuntimeException("the number of commands does not match the number of words and log types");
        }

        Process pro = null;

        for (int i = 0; i < commandList.size(); i++) {
            pro = cli.cli(tool, commandList.get(i)[0].trim());

            List<String> actualOutput = null;

            if (logTypeList.get(i)[0].trim().equalsIgnoreCase("error")) {
                actualOutput = cli.parseStream(pro.getErrorStream());
            } else {
                actualOutput = cli.parseStream(pro.getInputStream());
            }

            try {
                pro.waitFor(1, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for (String keyword : expectedKeywordList.get(i)) {
                assertThat(actualOutput).anyMatch(item -> item.contains(keyword.trim()));
            }
        }

    }

}
