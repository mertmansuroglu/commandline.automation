package com.auto.toolbase.tests.sc1.keytoolPromptSupport;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import static net.sf.expectit.matcher.Matchers.contains;

public class KeytoolTestExpectIt {

    @Test(groups = {"regression"})
    public void test() throws IOException {
        KeytoolElements keytoolElements = new KeytoolElements();


        Process process = Runtime.getRuntime().exec("cmd.exe");
        InputStream inputStream = process.getErrorStream();
        OutputStream outputStream = process.getOutputStream();

        StringBuilder wholeBuffer = new StringBuilder();

        Expect expect = new ExpectBuilder()

                .withInputs(inputStream)
                .withOutput(outputStream)
                .withTimeout(2, TimeUnit.SECONDS)
                .withEchoOutput(wholeBuffer)
                .withEchoInput(wholeBuffer)
                .withExceptionOnFailure()
                .build();
        try {
            Field[] fields = keytoolElements.getClass().getFields();
            String sentValue;
            String expectedValue;
            expect.sendLine("keytool -genkeypair -keyalg DSA -alias aa93fe5fe57");

            for (int i = 0; i < fields.length; i++) {
                sentValue = ((String[]) fields[i].get(keytoolElements))[0];
                expectedValue = ((String[]) fields[i].get(keytoolElements))[1];
                expect.expect(contains(expectedValue));
                expect.sendLine(sentValue);
            }

            expect.expect(contains("Generating"));
            expect.sendLine("exit");
            String response = wholeBuffer.toString();
            System.out.println(response);
            process.waitFor();
        } catch (Exception e) {
            System.out.println("Error::" + e.getMessage());


            process.destroy();
        }

        expect.close();

    }


}