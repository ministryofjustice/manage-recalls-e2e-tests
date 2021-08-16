package uk.gov.justice.digital.managerecalls;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

    @RunWith(CucumberWithSerenity.class)
    @CucumberOptions(
            plugin = {"pretty"},
            features = "src/test/resources/features/manage-recalls"
    )
    public class TestRunner {
    }
