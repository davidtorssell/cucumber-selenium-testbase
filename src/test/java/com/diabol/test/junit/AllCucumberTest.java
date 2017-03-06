package com.diabol.test.junit;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


/**
 * Created by david on 16-05-09.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "json:build/cucumber/test-reports/all.json",
                "html:build/cucumber/html/"
        },
        features = "src/test/resources/",
        glue = "com.diabol.test"
)
public class AllCucumberTest {
}