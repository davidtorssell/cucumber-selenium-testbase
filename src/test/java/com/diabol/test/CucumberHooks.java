package com.diabol.test;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by davidtorssell on 2017-03-02.
 */
public class CucumberHooks {

    @Autowired
    Selenium selenium;

    @Before("@Selenium")
    public void beforeSelenium(){
        selenium.createWebDriver();
    }

    @After("@Selenium")
    public void afterSelenium(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {

            scenario.embed(selenium.getScreenshot(), "image/png");

            String filename = "screenshot_" + System.currentTimeMillis() + '_' + scenario.getName().replace(' ', '-').replaceAll("[^a-zA-Z-]","") + ".png";
            selenium.saveScreenshot(filename);
        }
        selenium.quitWebDriver();
    }
}
