package com.railinc.totoro.cucumber;

import org.junit.runner.RunWith;

import cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(
        
        dryRun = false
        , monochrome = true
        , format = {"pretty", "html:target/results", "json:target/cucumber.json" })
public class RunCukesTest {
	
	
}