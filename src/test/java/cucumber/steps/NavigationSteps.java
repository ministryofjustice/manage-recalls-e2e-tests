package cucumber.steps;

import cucumber.pages.*;
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
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static cucumber.pages.FindAnOffenderPage.VIEW_PROFILE_LINK;
import static cucumber.pages.OffenderProfilePage.CREATE_RECALL_BUTTON;
import static cucumber.pages.TodoRecallsListPage.FIND_SOMEONE_LINK;
import static cucumber.pages.TodoRecallsListPage.RECALL_LIST_TODO_LINK;
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

    @And("{word} clicks Find someone")
    public void clickFindSomeoneLink(String customer) {
        userClicksOn(customer, FIND_SOMEONE_LINK);
    }

    @And("{word} clicks To Do")
    public void clickRecallListToDoLink(String customer) {
        userClicksOn(customer, RECALL_LIST_TODO_LINK);
    }

    @Then("{word} is on the Find An Offender page")
    public void onStartPage(String customer) {
        userIsOnPageWithTitle(customer, FindAnOffenderPage.TITLE);
    }

    @Then("{word} is on the Offender profile page")
    public void onOffenderProfilePage(String customer) {
        userIsOnPageWithTitle(customer, OffenderProfilePage.TITLE);
    }

    @Then("{word} is on the ToDo Recalls page")
    public void onToDoRecallsPage(String customer) {
        userIsOnPageWithTitle(customer, TodoRecallsListPage.TITLE);
    }

    @When("{word} enters the NOMIS number {word}")
    public void entersNOMISNumber(String customer, String nomsNumber) {
        theActorCalled(customer).attemptsTo(
                Enter.theValue(nomsNumber).into(FindAnOffenderPage.NOMS_NUMBER_INPUT)
        );
    }

    @And("{word} clicks Search")
    public void clickSearchButton(String customer) {
        userClicksOn(customer, FindAnOffenderPage.SEARCH_BUTTON);
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
        userClicksOn(customer, AssessRecallPage.DOWNLOAD_REVOCATION_ORDER_LINK);
    }

    @Then("a revocation order is downloaded")
    public void aRevocationOrderIsDownloaded() {
        await().atMost(10, SECONDS).until(revocationOrderIsDownloaded());
    }

    @When("{word} clicks on the create recall button")
    public void clickOnCreateRecallButton(String customer) {
        userClicksOn(customer, CREATE_RECALL_BUTTON);
    }

    @Then("{word} sees confirmation that the new recall was created")
    public void matchRecallCreatedText(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(OffenderProfilePage.RECALL_CONFIRMATION_MATCHES).text().startsWith("Recall ID:")
        );
    }

    @When("{word} clicks on the View profile link")
    public void clickOnViewProfileLink(String customer) {
        userClicksOn(customer, VIEW_PROFILE_LINK);
    }

    @Then("{word} sees at least one View link to assess a recall")
    public void anAssessRecallDetailsLinkIsVisible(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(TodoRecallsListPage.FIRST_ASSESS_RECALL_DETAILS_LINK).hasTextContent("View")
        );
    }

    @When("{word} clicks on the first View link to assess a recall")
    public void clickOnAssessRecallDetailsLink(String customer) {
        userClicksOn(customer, TodoRecallsListPage.FIRST_ASSESS_RECALL_DETAILS_LINK);
    }

    @Then("{word} is on the Assess Recall page")
    public void onAssessRecallPage(String customer) {
        userIsOnPageWithTitle(customer, AssessRecallPage.TITLE);
    }

    private Callable<Boolean> revocationOrderIsDownloaded() {
        return () -> fileIsDownloaded("/tmp", "revocation-order.pdf");
    }

    private boolean fileIsDownloaded(String downloadPath, String fileName) {
        File revocationOrder = new File(downloadPath + "/" + fileName);
        return revocationOrder.exists() && revocationOrder.delete();
    }

    private void userIsOnPageWithTitle(String customer, String uniquePageTitle) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(uniquePageTitle)
        );
    }

    private void userClicksOn(String customer, Target target) {
        theActorCalled(customer).attemptsTo(
                Click.on(target)
        );
    }
}
