package com.auto.toolbase.tests.sc1.keytoolPromptSupport;

import com.auto.toolbase.setUp.SetUp;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyToolTest extends SetUp {


    String ScriptPath = System.getProperty("user.dir") + "\\src\\test\\resources\\BashBatchScript";


    public void createTestDataItemFile(String data) throws IOException {
        FileWriter myWriter = new FileWriter(ScriptPath + "\\Data.txt");
        myWriter.write(data);
        myWriter.close();
    }

    @Test(dataProvider = "DataContainer", groups = {"regression"})
    public void KeytoolTest1(String data) {
        try {
            createTestDataItemFile(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String command = "cd " + ScriptPath + " && keytool -genkeypair -keyalg RSA -alias arrffghm45ds < Data.txt";


        Process pro = null;
        pro = cli.cli(tool, command);
        List<String> errorResultText = null;
        List<String> inputResultText = null;
        errorResultText = cli.parseStream(pro.getErrorStream());
        inputResultText = cli.parseStream(pro.getInputStream());
        try {
            pro.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Error Stream:");
        errorResultText.forEach(x -> System.out.println(x));
        System.out.println("İnput Stream:");
        inputResultText.forEach(x -> System.out.println(x));

        assertThat(errorResultText).anyMatch(item -> item.contains("Generating"));
        assertThat(errorResultText).anyMatch(item -> item.contains("RSA key pair and self-signed certificate"));
    }

    @DataProvider(name = "DataContainer")
    public Iterator<Object[]> myDataProvider() {
        ArrayList<Object[]> data = new ArrayList<>();
        ;
        data.add(new Object[]{"123abc\nmert\nqa\nseavus\nmersin\nmezit\n33\ny"});
        return data.iterator();
    }
}