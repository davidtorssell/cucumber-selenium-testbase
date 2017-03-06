package com.diabol.test.sut;

import com.diabol.test.SharedSeleniumImplementation;
import com.diabol.test.sut.pageobjects.GoogleFrontPage;
import com.diabol.test.sut.utils.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Created by davidtorssell on 2017-03-02.
 *
 * Helper class representing the system under test, rename appropriately to make more intuitive
 */
@Component
public class Sut extends SharedSeleniumImplementation {

    private static final Logger log = Logger.getLogger(Sut.class.getName());

    public Urls urls;

    public GoogleFrontPage googleFrontPage;



    @Autowired
    public Sut(@Value("${environment}") String env) {
        urls = new Urls(env);
    }


    @Override
    public void createWebDriver() {
        super.createWebDriver();

        googleFrontPage = new GoogleFrontPage(getWebDriver());

    }

}
