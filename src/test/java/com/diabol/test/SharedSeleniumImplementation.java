package com.diabol.test;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by davidtorssell on 2017-03-02.
 */
@Component
@Scope("cucumber-glue")
public abstract class SharedSeleniumImplementation implements  Selenium, DisposableBean {

    private static WebDriver driver;

    @Autowired
    WebDriverHelper helper;

    public void destroy() throws Exception {
        driver.quit();
    };

    public void createWebDriver(){
        if (driver == null) {
            driver = helper.createWebDriver();
        }
    }

    public void cleanWebDriverCookies(){
        helper.cleanCookies(driver);
    }

    public void quitWebDriver(){
        // This is handled by the spring lifecycle in this implementation
    }

    public WebDriver getWebDriver(){
        return driver;
    }

    public void saveScreenshot(String filename) throws IOException {
        helper.saveScreenshot(driver, filename);
    }

    public byte[] getScreenshot(){
        return helper.getScreenshot(driver);
    }
}
