package com.auto.toolbase.tests.sc1.sampleTestBaseOld;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SampleTestOld {
    private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(cmd + " " + line);
        }
    }

    public static void execute(String command) throws InterruptedException, IOException, IOException {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        Process process = builder.inheritIO().start();
        process.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String readline;
        while ((readline = reader.readLine()) != null) {
            System.out.println(readline);
        }
    }

    private Process runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec("cmd.exe /c " + command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());


        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
        System.out.println("helloooo" + pro);
        return pro;
        //return the output

    }

    private List<String> runProcess2(String command) throws Exception {
        String line;
        List<String> lines = new ArrayList<>();
        Process pro = Runtime.getRuntime().exec("cmd.exe /c " + command);
        BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
        while ((line = input.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    @Test
    public void test1() {

        try {
            runProcess("cd ,");
            runProcess("dir");

            runProcess("cd ..");
            runProcess("cd ,");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {

        try {
            Assert.assertTrue(runProcess2("echo hi &  echo bye").get(0).contains("hi"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testS() throws IOException {

        String[] commands = {"echo hello", "echo hi", "java -version"};

        for (String command : commands) {
            try {
                execute(command);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}

