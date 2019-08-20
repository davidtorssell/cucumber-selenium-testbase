package com.diabol.test;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by davidtorssell on 2017-03-02.
 */
@Component
public class WebDriverHelper {


    @Value("${grid.url}")
    private String gridUrl;

    @Value("${browser}")
    private String browser;

    @Value("${browser.firefox.marionette}")
    private boolean marionette;

    @Value("${browser.type}")
    private String browserType;

    @Value("${grid.chrome.language}")
    private String chromeLanguage;

    @Value("${grid.firefox.accept_languages}")
    private String firefoxLanguages;

    @Value("${screenshot.dir}")
    private String screenshotDir;

    @Value("${browser.implicitlyWaitSeconds}")
    private int implicitWait;

    @Value("${browser.pageLoadTimeoutSeconds}")
    private int pageLoadTimeout;

    @Value("${browser.scriptTimeoutSeconds}")
    private int scriptTimeout;

    public String getChromeLanguage(){
        return chromeLanguage;
    }

    public WebDriver createWebDriver() {

        WebDriver driver;

        if ( BrowserType.valueOf(browserType) == BrowserType.local && Browser.valueOf(browser) == Browser.chrome ){
            driver = getChromeDriver();

        } else if ( BrowserType.valueOf(browserType) == BrowserType.local && Browser.valueOf(browser) == Browser.firefox ){
            driver = getFirefoxDriver();

        } else if ( BrowserType.valueOf(browserType) == BrowserType.grid && Browser.valueOf(browser) == Browser.chrome ){
            driver = getRemoteChromeDriver();

        } else if ( BrowserType.valueOf(browserType) == BrowserType.grid && Browser.valueOf(browser) == Browser.firefox ){
            driver =  getRemoteFirefoxDriver();

        } else {
            throw new RuntimeException("Could not find combination of browser and type");
        }

        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(scriptTimeout, TimeUnit.SECONDS);

        return driver;
    }

    private FirefoxDriver getFirefoxDriver() {
        return new FirefoxDriver(getFirefoxDesiredCapabilities());
    }

    private ChromeDriver getChromeDriver() {
        return new ChromeDriver();
    }

    private WebDriver getRemoteFirefoxDriver() {
        return remoteDriver(getFirefoxDesiredCapabilities());
    }

    private WebDriver getRemoteChromeDriver() {
        return remoteDriver(getChromeDesiredCapabilities());
    }


    private WebDriver remoteDriver(Capabilities browser) {

        try {
            WebDriver webDriver = new RemoteWebDriver(new URL(gridUrl), browser);
            webDriver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
            return webDriver;
        }
        catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    private Capabilities getFirefoxDesiredCapabilities(){

        Capabilities dc = new FirefoxOptions();
        //      dc.setJavascriptEnabled(true);

        //       FirefoxProfile profile = new FirefoxProfile();
        //     profile.setPreference("intl.accept_languages", firefoxLanguages);

        return dc;
    }

    private DesiredCapabilities getChromeDesiredCapabilities(){

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName("chrome");
        ChromeOptions options = new ChromeOptions();
        options.merge(cap);
        //     dc.setCapability("language", chromeLanguage.toString());

        return cap;
    }

    public void saveScreenshot(WebDriver driver, String filename) throws IOException {

        WebDriver augmentedDriver = new Augmenter().augment(driver);
        File screenshot = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(screenshotDir.replaceAll("/$", "").concat("/").concat(filename)));
    }

    public byte[] getScreenshot(WebDriver driver){

        WebDriver augmentedDriver = new Augmenter().augment(driver);
        return ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.BYTES);
    }

    public void cleanCookies(WebDriver driver){
        driver.manage().deleteAllCookies();
    }

    private enum Browser {
        firefox,
        chrome
    }

    private enum BrowserType {
        grid,
        local
    }
}
