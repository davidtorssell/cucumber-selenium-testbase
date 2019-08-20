package com.diabol.test.stepdefinitions;

import com.diabol.test.sut.Sut;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

/**
 * Created by davidtorssell on 2017-03-02.
 */
public class HBFrontPageStepDefinitions {

    private static final Logger log = Logger.getLogger(HBFrontPageStepDefinitions.class.getName());

   @Autowired
    private Sut sut;

    @When("^clicking the \"([^\"]*)\"$")
    public void clicking_the(String arg1) {
        sut.handelsbankenFrontPage.clickMenuItem(arg1);
    }

    @Then("^I should see the equivalent page for \"([^\"]*)\"$")
    public void i_should_see_the_equivalent_page_for(String arg1) {
        sut.handelsbankenFrontPage.verifyUrlForMenuItem(arg1);
    }


    @Given("^I am at the Handelsbanken front page$")
    public void i_am_at_the_Handelsbanken_front_page() {
        sut.handelsbankenFrontPage.goToUrl("https://www.handelsbanken.se");

    }

    @Then("^there should be a big Handelsbanken logotype$")
    public void there_should_be_a_big_Handelsbanken_logotype() {
        sut.handelsbankenFrontPage.verifyHandelsbankenLogotype();
    }

}
