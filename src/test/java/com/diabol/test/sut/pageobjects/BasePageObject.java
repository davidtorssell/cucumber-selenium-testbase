package com.diabol.test.sut.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created by davidtorssell on 2017-03-02.
 *
 * Base page object, extend other page objects from this class
 */
public class BasePageObject {

    private static final Logger log = Logger.getLogger(BasePageObject.class.getName());

    protected WebDriver driver;
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public BasePageObject(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor)driver;
    }

    public void goToUrl(String url) {
        driver.navigate().to(url);
        log.log(Level.INFO, "Navigated to " + url);
    }


    public WebElement findElement(final By locator) {

        // need to ensure this is agnostic to target system or remove
        //waitForJSDocumentReadyState();

        final long startTime = System.currentTimeMillis();
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(4, TimeUnit.SECONDS)
                .pollingEvery(100, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class);
        WebElement we = null;

        while ((System.currentTimeMillis() - startTime) < 91000) {
            try {
                we = wait.until(presenceOfElementLocated(locator));
                log.log(Level.FINE, "Presence of element located");
                we = wait.until(visibilityOfElementLocated(locator));
                log.log(Level.FINE, "Visibility of element located");

                if (!driver.findElement(locator).isEnabled()) {
                    log.log(Level.INFO, "Element is not enabled");
                    wait.until(elementToBeClickable(locator));
                }
                break;
            } catch (StaleElementReferenceException e) {
                log.log(Level.INFO, "Stale element locator: " + locator.toString());
            } catch (InvalidSelectorException ise) {
                log.log(Level.INFO, "InvalidSelectorException: " + locator.toString());
                break;
            }
        }

        // need to ensure this is agnostic to target system or remove
        //waitForJSDocumentReadyState();
        return we;
    }


    public void click(By locator) {

        WebElement element = null;
        Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
                .withTimeout(5, TimeUnit.SECONDS)
                .pollingEvery(100, TimeUnit.MILLISECONDS)
                .ignoring(StaleElementReferenceException.class) ;


        int attempts = 0;
        log.log(Level.INFO, "Click element with locator: " + locator.toString());
        while(attempts < 2) {
            try {
                element = findElement(locator);
                wait.until(elementToBeClickable(locator));
                element.click();
                break;
            } catch (StaleElementReferenceException e) {
                log.log(Level.INFO, "Stale element with locator: " + locator);
            } catch (NoSuchElementException nse) {
                log.log(Level.INFO, "No such element with locator: " + locator);
            } catch (WebDriverException we) {
                if (we.getMessage().contains("Element is not clickable")) {
                    log.log(Level.INFO, "Webdriver exception: element is not clickable: " + locator.toString());
                    //try to click any other element found by that locator
                    List<WebElement> elements = driver.findElements(locator);
                    for (WebElement e : elements) {
                        try {
                            e.click();
                            return;
                        } catch (WebDriverException we2) {
                        }
                    }
                }
                log.log(Level.INFO, "Exception message: " + we.getMessage());
            }
            attempts++;
            log.log(Level.INFO, "Click attempt nr: " + attempts);
            sleep(1);
        }
        assertThat("Failed to click element with locator: " + locator.toString(), attempts < 2);

        // need to ensure this is agnostic to target system or remove
        //waitForJSDocumentReadyState();
    }


    public String getText(By locator) {

        WebElement element = null;
        String text = null;
        Wait<WebDriver> wait = new FluentWait<WebDriver>( driver )
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(StaleElementReferenceException.class) ;

        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(locator).getText().length() != 0;
            }
        });

        int attempts = 0;
        log.log(Level.INFO, "Get text from element with locator: " + locator.toString());
        while(attempts < 2) {
            try {
                element = findElement(locator);
                text = element.getText();
                break;
            } catch (StaleElementReferenceException e) {
                log.log(Level.INFO, "Stale element with locator: " + locator);
            } catch (NoSuchElementException nse) {
                log.log(Level.INFO, "No such element with locator: " + locator);
            } catch (WebDriverException we) {
                if (we.getMessage().contains("Element is not clickable")) {
                    log.log(Level.INFO, "Webdriver exception: element is not clickable: " + locator.toString());
                    wait.until(elementToBeClickable(locator));
                }
            }
            attempts++;
        }

        // need to ensure this is agnostic to target system or remove
        //waitForJSDocumentReadyState();
        return text;
    }

    public void sendKeys(By by, String keys) {

        WebElement element;

        int attempts = 0;
        log.log(Level.INFO, "Typing text: " + keys + " to " + by.toString());
        while(attempts < 3) {
            try {
                element = findElement(by);
                element.click();
                element.clear();
                element.sendKeys(keys);
                break;

            } catch (ElementNotVisibleException enve) {
                log.log(Level.INFO, "Element not visible, by: " + by.toString());
            } catch (InvalidElementStateException iese) {
                // hack to cover for some bad text box that doesn´t like clearing
                Actions actions = new Actions(driver);
                actions.moveToElement(findElement(by));
                actions.click();
                actions.sendKeys(keys);
                actions.build().perform();
                break;
            } catch (StaleElementReferenceException e) {
                log.log(Level.INFO, "Stale element, by: " + by.toString());
            } catch (WebDriverException wde) {
                log.log(Level.INFO, "Failed to send keys due to WebdriverException: " + wde.getMessage() + "\nWill sleep and retry");
                sleepMillis(500);
            }
        }
        attempts++;
    }

    public void waitForJSDocumentReadyState(){
        if (js.executeScript("return document.readyState").toString().equals("complete")){
            log.log( Level.FINE, "Page Is loaded.");
        } else {
            for (int i = 0; i < 25; i++) {
                try {
                    log.log( Level.INFO, "Document not ready, now I need to sleep...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                if (js.executeScript("return document.readyState").toString().equals("complete")) {
                    break;
                }
            }
        }

        waitForJQueryProcessing(driver, 30);
    }

    public static boolean waitForJQueryProcessing(WebDriver driver, int timeOutInSeconds) {
        boolean jQcondition = false;
        try {
            new WebDriverWait(driver, timeOutInSeconds) {
            }.until(new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(WebDriver driverObject) {
                    return (Boolean) ((JavascriptExecutor) driverObject)
                            .executeScript("return window.jQuery != undefined && jQuery.active === 0");
                }
            });
            jQcondition = (Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return window.jQuery != undefined && jQuery.active === 0");
            return jQcondition;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Got exception while waiting for jQuery processing");
            log.log(Level.FINE, e.getMessage());
        }
        return jQcondition;
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
