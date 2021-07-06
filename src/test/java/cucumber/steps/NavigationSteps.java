package cucumber.steps;

import cucumber.pages.FindAnOffenderPage;
import cucumber.pages.OffenderProfilePage;
import cucumber.pages.LoginPage;
import cucumber.pages.VerifyEmailPage;
import cucumber.questions.UserIsOn;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.EnterPassword;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static cucumber.pages.FindAnOffenderPage.VIEW_PROFILE_LINK;
import static cucumber.pages.OffenderProfilePage.DOWNLOAD_REVOCATION_ORDER_LINK;
import static cucumber.pages.OffenderProfilePage.CREATE_RECALL_BUTTON;
import static cucumber.pages.OffenderProfilePage.RECALL_CONFIRMATION_MATCHES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.awaitility.Awaitility.await;

public class NavigationSteps {

    private EnvironmentVariables environmentVariables;

    @Before(order = 1)
    public void setUp() throws IOException {
        environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        String credentialsLocation = environmentVariables.getProperty("serenity.credentials");

        credentialsLocation = credentialsLocation.replaceFirst("~", System.getenv("HOME"));

        Path pathToCreds = Paths.get(credentialsLocation);

        if (!Files.exists(pathToCreds)) {
            environmentVariables.setProperty("SERENITY_USERNAME", System.getenv("SERENITY_USERNAME"));
            environmentVariables.setProperty("SERENITY_PASSWORD", System.getenv("SERENITY_PASSWORD"));
        } else {
            Files.readAllLines(pathToCreds).forEach(row -> {
                String[] keyVal = row.split("=");
                if (keyVal.length==2) {
                    environmentVariables.setProperty("SERENITY_" + keyVal[0].toUpperCase(), keyVal[1]);
                }
            });
        }

    }

    @Before(order = 100)
    public void prepareTests() {
        setTheStage(new OnlineCast());
    }

    @Given("{word} navigates to manage recall service")
    public void goToManageRecallService(String customer) {
        theActorCalled(customer).attemptsTo(
                Open.browserOn().the(FindAnOffenderPage.class)
        );
    }

    @When("{word} logs in")
    public void logIn(String customer) {
        theActorCalled(customer).attemptsTo(
                Check.whether(UserIsOn.loginPage()).andIfSo(
                        Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(LoginPage.TITLE),
                        Enter.theValue(environmentVariables.getProperty("SERENITY_USERNAME"))
                                .into(LoginPage.USERNAME),
                        EnterPassword.theValue(environmentVariables.getProperty("SERENITY_PASSWORD")).into(LoginPage.PASSWORD),
                        Click.on(LoginPage.SIGN_IN_BUTTON)
                ),
                Check.whether(UserIsOn.verifyEmailPage()).andIfSo(
                        Click.on(VerifyEmailPage.SKIP_FOR_NOW)
                )
        );
    }

    @Then("{word} is on the Find An Offender page")
    public void onStartPage(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(FindAnOffenderPage.TITLE)
        );
    }

    @When("{word} enters the NOMIS number {word}")
    public void entersNOMISNumber(String customer, String nomsNumber) {
        theActorCalled(customer).attemptsTo(
                Enter.theValue(nomsNumber).into(FindAnOffenderPage.NOMS_NUMBER_INPUT)
        );
    }

    @And("{word} clicks Search")
    public void clickSearchButton(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(FindAnOffenderPage.SEARCH_BUTTON)
        );
    }

    @Then("{word} sees a search result of {string}")
    public void searchResultText(String customer, String searchResultsText) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(FindAnOffenderPage.SEARCH_RESULT_TEXT).hasTextContent(searchResultsText)
        );
    }

    @And("{word} sees person entries matching {string}")
    public void matchedPersonResultsText(String customer, String personText) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(FindAnOffenderPage.PERSON_MATCHES).hasTextContent(personText)
        );
    }

    @And("{word} sees a person entry with nomsNumber {string} and non-empty name and DoB fields")
    public void matchedPersonNomsNumberHasValueAndNameAndDoBFieldsAreNonEmpty(String customer, String nomsNumber) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(FindAnOffenderPage.NOMS_NUMBER_MATCHES).hasTextContent(nomsNumber),
                Ensure.that(FindAnOffenderPage.FIRST_NAME_MATCHES).text().isNotBlank(),
                Ensure.that(FindAnOffenderPage.LAST_NAME_MATCHES).text().isNotBlank(),
                Ensure.that(FindAnOffenderPage.DATE_OF_BIRTH_MATCHES).text().isNotBlank()
        );
    }

    @When("{word} clicks on the download revocation order link")
    public void clickOnRevocationOrderLink(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(DOWNLOAD_REVOCATION_ORDER_LINK)
        );

        await().atMost(10, SECONDS).until(revocationOrderIsDownloaded());
    }

    @When("{word} clicks on the create recall button")
    public void clickOnCreateRecallButton(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(CREATE_RECALL_BUTTON)
        );
    }

    @Then("{word} sees confirmation that the new recall was created")
    public void matchedRecallCreatedText(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(OffenderProfilePage.RECALL_CONFIRMATION_MATCHES).text().startsWith("Recall ID:")
        );
    }

    @When("{word} clicks on the View profile link")
    public void clickOnViewProfileLink(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(VIEW_PROFILE_LINK)
        );
    }

    @Then("{word} is on the Offender profile page")
    public void onOffenderProfilePage(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(OffenderProfilePage.CREATE_RECALL_BUTTON).hasTextContent("Create recall")
        );
    }

    private Callable<Boolean> revocationOrderIsDownloaded() {
        return () -> fileIsDownloaded("/tmp", "revocation-order.pdf");
    }

    public boolean fileIsDownloaded(String downloadPath, String fileName) {
        File revocationOrder = new File(downloadPath + "/" + fileName);
        if (revocationOrder.exists() && revocationOrder.isFile()) {
            revocationOrder.delete();
            return true;
        } else {
            return false;
        }
    }
}
