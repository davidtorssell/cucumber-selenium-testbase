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
public class SandboxStepDefinitions {

    private static final Logger log = Logger.getLogger(SandboxStepDefinitions.class.getName());

   @Autowired
    private Sut sut;


    @Given("^I am at the google front page$")
    public void i_am_at_the_google_front_page() {
        sut.googleFrontPage.goToUrl(sut.urls.getBaseUrl());
    }

    @When("^I search for \"([^\"]*)\"$")
    public void i_search_for(String arg1) {
        sut.googleFrontPage.searchFor(arg1);
    }

    @Then("^there should be a hit with the address to Davids profile$")
    public void there_should_be_a_hit_with_the_address_to_Davids_profile() {
        sut.googleFrontPage.verifyLinkInSearchHits("https://se.linkedin.com/in/davidtorssell");
    }


    @Then("there should be a picture of italian spiderman")
    public void there_should_be_a_picture_of_italian_spiderman() {
    }

    @Then("it should be awesome")
    public void it_should_be_awesome() {
    }


}
