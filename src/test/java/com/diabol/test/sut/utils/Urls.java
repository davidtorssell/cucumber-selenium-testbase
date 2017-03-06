package com.diabol.test.sut.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by davidtorssell on 2017-03-02.
 */
public class Urls {

    private String baseUrl;

    private static final Logger log = Logger.getLogger(Urls.class.getName());


    public Urls(String env) {

        if (env == null) {
            env = "default";
        }

        switch (env) {
            case "local":
                baseUrl = "http://localhost:8080";
                break;
            case "dev":
            case "qa":
            case "stage":
            case "prod":
            case "default":
            default:
                baseUrl = "http://www.google.se";
                break;
        }
        log.log(Level.INFO, "Will use base url: " + baseUrl + " as configured for environment: " + env);
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}

