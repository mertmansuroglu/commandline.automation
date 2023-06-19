package com.auto.toolbase.tests.sc1.keytoolPromptSupport;

import com.auto.toolbase.setUp.SetUp;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PromptTest extends SetUp {


    String ScriptPath = "C:\\Users\\Mert.Mansuroglu\\Desktop\\BashTest";

    public void createTestDataItemFile(String name, String fav, int birthDate) throws IOException {

        List<String> lines = new ArrayList<>();
        lines.add(name);
        lines.add(fav);
        lines.add(String.valueOf(birthDate));
        Path textFile = Paths.get(ScriptPath + "\\Data.txt");
        Files.write(textFile, lines, StandardCharsets.UTF_8);
    }

    @Test(dataProvider = "DataContainer", groups = {"regression"})
    public void test1(String name, String fav, int birthDate, String LogType) {
        try {
            createTestDataItemFile(name, fav, birthDate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> expectedKeywordList = new ArrayList<>();
        expectedKeywordList.add("Your name:" + name);
        expectedKeywordList.add("Your Favorite prog. lang. :" + fav);
        expectedKeywordList.add("Your age:" + (2022 - birthDate));


        String bashCommand = "cd " + ScriptPath + " && bash -c \" printf '" + name + "\\n" + fav + "\\n" + birthDate + "' | ./questions.sh\"";
        String batchCommand = "cd " + ScriptPath + " && name < Data.txt";

        Process pro = null;
        pro = cli.cli(tool, batchCommand);
        List<String> actualOutput = null;

        if (LogType.equalsIgnoreCase("error")) {
            actualOutput = cli.parseStream(pro.getErrorStream());
        } else {
            actualOutput = cli.parseStream(pro.getInputStream());
        }

        try {
            pro.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actualOutput.forEach(x -> System.out.println(x));

        for (String keyword : expectedKeywordList) {
            assertThat(actualOutput).anyMatch(item -> item.contains(keyword.trim()));
        }


    }

    @DataProvider(name = "DataContainer")
    public Iterator<Object[]> myDataProvider() {
        ArrayList<Object[]> data = new ArrayList<>();
        ;
        data.add(new Object[]{"Ahmet", "Java", 1980, "standard"});
        data.add(new Object[]{"Mehmet", "Python", 1985, "standard"});
        data.add(new Object[]{"Sezgin", "Perl", 1990, "standard"});
        data.add(new Object[]{"Aydın", "C#", 1994, "standard"});

        return data.iterator();

    }
}
