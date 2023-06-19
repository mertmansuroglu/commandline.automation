package com.auto.toolbase.dataproviders;

import com.taw.common.elements.Actions;
import org.testng.annotations.DataProvider;

import java.util.Iterator;

public class Data extends Actions {
    String projectDir = System.getProperty("user.dir");
    String jsonFileName = projectDir + "\\src\\test\\resources\\data\\scenarios.json";
    @DataProvider(name = "commands")
    public Object[][] commands() {
        return dataSources.excel("src/test/resources/data/scenarios.xlsx", "sc1");
    }

    @DataProvider(name = "commandsFromJSON")
    public Iterator<Object[]> commandsFromJSON() {
        return DataSources.json(jsonFileName);
    }


}
