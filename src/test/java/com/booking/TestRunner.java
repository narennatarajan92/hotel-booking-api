package com.booking;

import org.junit.platform.suite.api.*;
import static io.cucumber.core.options.Constants.*;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.booking")
@SelectClasspathResource("features/create_booking.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.booking.stepdefinitions")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/cucumber-reports.html, json:target/cucumber.json")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@booking_regression")

public class TestRunner {

}