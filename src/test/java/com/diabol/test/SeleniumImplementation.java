package com.diabol.test;

/**
 * Created by davidtorssell on 2017-03-02.
 */

import org.openqa.selenium.WebDriver;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class SeleniumImplementation implements Selenium {

    private WebDriver driver;

    @Autowired
    WebDriverHelper helper;

    public void createWebDriver(){
        driver = helper.createWebDriver();
    }

    public void quitWebDriver(){
        driver.quit();
    }

    public WebDriver getWebDriver(){
        return driver;
    }

    public void saveScreenshot(String filename) throws IOException {
        helper.saveScreenshot(driver, filename);
    }

    public void cleanWebDriverCookies(){
        helper.cleanCookies(driver);
    }

    public byte[] getScreenshot(){
        return helper.getScreenshot(driver);
    }
}