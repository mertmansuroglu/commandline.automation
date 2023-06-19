package com.auto.toolbase.tests.sc1.keytoolPromptSupport;


import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import static net.sf.expectit.matcher.Matchers.contains;
import static net.sf.expectit.matcher.Matchers.regexp;

public class PromptTestExpectIt {


    @Test(groups = {"regression"})
    public void test() throws IOException, InterruptedException {


        Process process = Runtime.getRuntime().exec("cmd.exe");
        InputStream inputStream = process.getInputStream();
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


        expect.sendLine("cd C:\\Users\\Mert.Mansuroglu\\Desktop\\ExpectIt\\BashTest");
        expect.sendLine("name.bat");
        expect.expect(contains("name"));
        expect.sendLine("Ahmet");
        expect.expect(contains("language"));
        expect.sendLine("Java");
        expect.expect(contains("Year"));
        expect.sendLine("1985");

        String list = expect.expect(regexp(">")).getBefore();
        // System.out.println("List: " + list);
        expect.sendLine("exit");
        String response = wholeBuffer.toString();
        System.out.println(response);


        process.waitFor();

        expect.close();

    }
}