package com.diabol.test;

import org.openqa.selenium.WebDriver;
import java.io.IOException;

/**
 * Created by david on 2017-03-02.
 */
public interface Selenium {

    public void createWebDriver();
    public void quitWebDriver();
    public void cleanWebDriverCookies();
    public WebDriver getWebDriver();
    public void saveScreenshot(String path) throws IOException;
    public byte[] getScreenshot();

}
