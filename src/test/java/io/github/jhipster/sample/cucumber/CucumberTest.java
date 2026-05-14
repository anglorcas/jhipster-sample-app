package io.github.jhipster.sample.cucumber;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("cucumber/features")
@ConfigurationParameter(key = "cucumber.glue", value = "io.github.jhipster.sample.cucumber.steps")
public class CucumberTest {}
