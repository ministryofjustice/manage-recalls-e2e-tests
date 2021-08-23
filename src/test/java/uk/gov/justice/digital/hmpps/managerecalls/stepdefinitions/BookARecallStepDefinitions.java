package uk.gov.justice.digital.hmpps.managerecalls.stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import uk.gov.justice.digital.hmpps.managerecalls.pages.tasks.*;

import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.hasSize;

public class BookARecallStepDefinitions {

    private EnvironmentVariables environmentVariables;

    @Before(order = 1)
    public void setUp() {
        environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        environmentVariables.setProperty("SERENITY_USERNAME", getEnvOrDefault("SERENITY_USERNAME", "PPUD_USER"));
        environmentVariables.setProperty("SERENITY_PASSWORD", getEnvOrDefault("SERENITY_PASSWORD", "password123456"));
    }

    private String getEnvOrDefault(String propertyName, String defaultValue) {
        String value = System.getenv(propertyName);
        return value == null ? defaultValue : value;
    }

    @Before(order = 2)
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^(.*) is logged in to the manage recalls service")
    public void login(String actor){
        theActorCalled(actor).attemptsTo(Navigation.toTheManageRecallsService()
                .then(Login.toTheManageRecallsService(environmentVariables.getProperty("SERENITY_USERNAME"), environmentVariables.getProperty("SERENITY_PASSWORD"))) );
    }

    @Given("^(.*) has received a recall recommendation for a prison offender with NOMIS ID \"(.*)\" from probation service")
    public void findOffender(String actor, String nomisId) {
        setSessionVariable("NOMIS_ID").to(nomisId);
    }

    @When("^s?he books a recall")
    public void bookARecall() {
        theActorInTheSpotlight().attemptsTo(ManageRecallsLandingPage.goToFindAnOffenderView()
                .then(FindAnOffender.byNomisId(sessionVariableCalled("NOMIS_ID"))
                        .then(OffenderSearchResult.navigateToOffenderProfileDetails())
                        .then(OnOffenderProfile.startBookingARecall())
                        .then(OnRecallRecommendationReceivedPage.submitTheDateAndTimeOfTheRecallRecommendationReceivedFromProbationService())
                        .then(OnSentenceOffenceAndReleaseDetailsPage.submitTheSentenceOffenceAndLatestReleaseDetails())
                        .then(OnPoliceDetailsPage.submitPoliceDetails())
                        .then(OnVulnerabilitiesAndContrabandDetailsPage.submitTheVulnerabilitiesAndContrabandDetails())
                        .then(OnProbationDetailsPage.submitProbationDetails())
                ));
    }


    @Then("^the recall is successfully booked")
    public void verifyTheSubmittedRecallDetailsAreDisplayedCorrectly() {
//        theActorInTheSpotlight().should(
//                seeThat("book a recall id",
//                       OffenderSearchResult.offenderList(), hasSize(greaterThan(0)))
//        );
    }
}
