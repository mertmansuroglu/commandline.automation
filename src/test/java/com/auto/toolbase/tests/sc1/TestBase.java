package com.auto.toolbase.tests.sc1;

import com.auto.toolbase.dataproviders.Data;
import com.auto.toolbase.tests.SetUp;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBase extends SetUp {

/*
    @Test(dataProvider = "commands", dataProviderClass = Data.class, groups = {"regression"})
    public void test1(String command, String response, String responseType) {
        Process pro = cli.cli(tool, command);
        if (responseType.equalsIgnoreCase("error")) {
            assertThat(cli.parseStream(pro.getErrorStream())).anyMatch(item -> item.contains(response));
        } else {
            assertThat(cli.parseStream(pro.getInputStream())).anyMatch(item -> item.contains(response));
        }
    }
*/

    // TODO: 8/3/2022 I could not update csv data source class because its read only.Can you update the csv file to readline firstly?
    @Test(dataProvider = "commands", dataProviderClass = Data.class, groups = {"regression"})
    public void test3(String testCaseDescription, String cmdCommands, String ExpectedKeywordsInOutput) {

        boolean haveSequencedCases = cmdCommands.indexOf("SequencedCases") == -1 ? false : true;

        if (haveSequencedCases) {

            sequencedAllTestCases.forEach(eachCase -> {

                String sequencedCaseDescription = eachCase[0].toString().trim();

                if (sequencedCaseDescription.equals(testCaseDescription)) {

                    String sequencedCaseCommand = eachCase[1].toString().trim();
                    String sequencedCaseExpectedKeywordsInOutput = eachCase[2].toString().trim();

                    Process pro = cli.cli(tool, sequencedCaseCommand);
                    String[] keywords = sequencedCaseExpectedKeywordsInOutput.split("::");
                    List<String> actualOutput = cli.parseStream(pro.getInputStream());

                    for (String keyword : keywords) {
                        assertThat(actualOutput).anyMatch(item -> item.contains(keyword.trim()));
                    }
                }
            });

        } else {
            Process pro = cli.cli(tool, cmdCommands);
            String[] keywords = ExpectedKeywordsInOutput.split("::");
            List<String> actualOutput = cli.parseStream(pro.getInputStream());

            for (String keyword : keywords) {
                assertThat(actualOutput).anyMatch(item -> item.contains(keyword.trim()));
            }
        }

    }

}
