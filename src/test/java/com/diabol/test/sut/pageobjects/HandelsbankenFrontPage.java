package com.diabol.test.sut.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by davidtorssell on 2017-03-02.
 */
public class HandelsbankenFrontPage extends BasePageObject {

    private static final Logger log = Logger.getLogger(HandelsbankenFrontPage.class.getName());

    By handelsbankenLogo = By.xpath("//img[@alt='Handelsbanken']");

    By startMenuItem = By.cssSelector("[data-spif-id='privat']");
    By lanMenuItem = By.cssSelector("[data-spif-id='privat/lan']");
    By sparaMenuItem = By.cssSelector("[data-spif-id='privat/spara']");
    By pensionMenuItem = By.cssSelector("[data-spif-id='privat/pension']");

    By lanSubMenuItem = By.id("LINKprivat/lan");
    By sparaSubMenuItem = By.id("LINKprivat/spara");
    By pensionSubMenuItem = By.id("LINKprivat/pension");


    public HandelsbankenFrontPage(WebDriver driver) {
        super(driver);
    }


    public void verifyHandelsbankenLogotype() {
        WebElement hit = findElement(handelsbankenLogo);
    }


    public void clickMenuItem(String menuItem) {
        switch (menuItem) {
            case "Start":
                click(startMenuItem);
                break;
            case "Lån":
                click(lanMenuItem);
                click(lanSubMenuItem);
                break;
            case "Spara":
                click(sparaMenuItem);
                click(sparaSubMenuItem);
                break;
            case "Pension":
                click(pensionMenuItem);
                click(pensionSubMenuItem);
                break;
            default:
                assertThat("Got some bogus input for menu items", false);
        }
    }


    public void verifyUrlForMenuItem(String menuItem) {
        String url = driver.getCurrentUrl();
        String pageUrl = null;
        switch (menuItem) {
            case "Start":
                pageUrl = "/sv/";
                break;
            case "Lån":
                pageUrl = "/sv/lan";
                break;
            case "Spara":
                pageUrl = "/sv/spara";
                break;
            case "Pension":
                pageUrl = "/sv/pension";
                break;
            default:
                assertThat("Ended up on some bogus page", url.contains(pageUrl));
        }
    }
}
