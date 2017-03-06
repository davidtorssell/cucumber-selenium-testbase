package com.diabol.test.sut.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by davidtorssell on 2017-03-02.
 */
public class GoogleFrontPage extends BasePageObject {

    private static final Logger log = Logger.getLogger(GoogleFrontPage.class.getName());

    By searchTextBox = By.id("gs_lc0");

    public GoogleFrontPage(WebDriver driver) {
        super(driver);
    }

    public void searchFor(String searchString) {
        sendKeys(searchTextBox, searchString + Keys.ENTER);
    }

    public void verifyUrlInSearchHits(String expextedUrl) {
        WebElement hit = findElement(By.xpath("//cite[text()='" + expextedUrl + "']"));
        assertThat("Did not find expected url: " + expextedUrl, hit != null);
    }
}
