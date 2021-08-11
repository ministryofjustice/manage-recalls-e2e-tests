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
    public void setUp() {
        environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        environmentVariables.setProperty("SERENITY_USERNAME", getEnvOrDefault("SERENITY_USERNAME", "PPUD_USER"));
        environmentVariables.setProperty("SERENITY_PASSWORD", getEnvOrDefault("SERENITY_PASSWORD", "password123456"));
    }

    private String getEnvOrDefault(String propertyName, String defaultValue) {
        String value = System.getenv(propertyName);
        return value == null ? defaultValue : value;
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

    @And("{word} clicks Find a person")
    public void clickFindSomeoneLink(String customer) {
        userClicksOn(customer, FIND_SOMEONE_LINK);
    }

    @And("{word} clicks To Do")
    public void clickRecallListToDoLink(String customer) {
        userClicksOn(customer, RECALL_LIST_TODO_LINK);
    }

    @Then("{word} is on the Find a person page")
    public void onStartPage(String customer) {
        userIsOnPageWithTitle(customer, FindAnOffenderPage.TITLE);
    }

    @Then("{word} is on the Person profile page")
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

    @When("{word} downloads revocation order which was generated")
    public void clickOnRevocationOrderLink(String customer) {
        userClicksOn(customer, RecallAuthorisationPage.DOWNLOAD_REVOCATION_ORDER_LINK);
    }

    @Then("a revocation order is downloaded")
    public void aRevocationOrderIsDownloaded() {
        await().atMost(10, SECONDS).until(revocationOrderIsDownloaded());
    }

    @When("{word} clicks on the Create recall button")
    public void clickOnCreateRecallButton(String customer) {
        userClicksOn(customer, CREATE_RECALL_BUTTON);
    }

    @Then("{word} is on the recall request received page")
    public void onRecallReceivedPage(String customer) {
        userIsOnPageWithTitle(customer, RecallReceivedPage.TITLE);
    }

    @When("{word} submits the date and time of the recall request received from probation service")
    public void submitDateTimeOfRecallRequestReceived(String customer) {
        userEnters(customer, RecallReceivedPage.DAY_FIELD, "05");
        userEnters(customer, RecallReceivedPage.MONTH_FIELD, "12");
        userEnters(customer, RecallReceivedPage.YEAR_FIELD, "2020");
        userEnters(customer, RecallReceivedPage.HOUR_FIELD, "15");
        userEnters(customer, RecallReceivedPage.MINUTE_FIELD, "33");
        userClicksOn(customer, RecallReceivedPage.CONTINUE_BUTTON);
    }

    @When("{word} submits the latest release date and releasing prison details")
    public void submitLatestReleaseDetails(String customer) {
        userEnters(customer, LastReleaseDetailsPage.RELEASING_PRISON, "Belmarsh");
        userEnters(customer, RecallReceivedPage.DAY_FIELD, "03");
        userEnters(customer, RecallReceivedPage.MONTH_FIELD, "08");
        userEnters(customer, RecallReceivedPage.YEAR_FIELD, "2020");
        userClicksOn(customer, RecallReceivedPage.CONTINUE_BUTTON);
    }

    @When("{word} submits the police contact details")
    public void submitPoliceContactDetails(String customer) {
        userEnters(customer, PoliceContactDetailsPage.LOCAL_POLICE_STATION, "Brentwood, Essex");
        userClicksOn(customer, RecallReceivedPage.CONTINUE_BUTTON);
    }

    @Then("{word} continues from the Book a recall page")
    public void onBookRecallPage(String customer) {
        userIsOnPageWithTitle(customer, BookRecallPage.TITLE);
        userClicksOn(customer, BookRecallPage.CONTINUE_BUTTON);
    }

    @When("{word} recommends a 14 day recall")
    public void recommend14DayRecall(String customer) {
        userIsOnPageWithTitle(customer, RecallRecommendationPage.TITLE);
        userClicksOn(customer, RecallRecommendationPage.RECALL_LENGTH_14_DAYS);
        userClicksOn(customer, RecallRecommendationPage.CONTINUE_BUTTON);
    }

    @Then("{word} sees confirmation that the new recall was created")
    public void matchRecallCreatedText(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(BookRecallPage.RECALL_CONFIRMATION_MATCHES).text().startsWith("Recall ID:")
        );
    }

    @When("{word} uploads two documents")
    public void addRecallDocument(String customer) {
        userIsOnPageWithTitle(customer, UploadRecallDocumentsPage.TITLE);
        UploadRecallDocumentsPage page = new UploadRecallDocumentsPage();
        page.uploadFile("src/test/resources/files/test.pdf", "PART_A_RECALL_REPORT");
        page.uploadFile("src/test/resources/files/test.pdf", "LICENCE");
        userClicksOn(customer, UploadRecallDocumentsPage.CONTINUE_BUTTON);
    }

    @When("{word} clicks on the View link")
    public void clickOnViewProfileLink(String customer) {
        userClicksOn(customer, VIEW_PROFILE_LINK);
    }

    @When("{word} clicks on the first View link to view a recall")
    public void clickOnViewLink(String customer) {
        userClicksOn(customer, TodoRecallsListPage.FIRST_ASSESS_RECALL_DETAILS_LINK);
    }

    @Then("{word} is on the Recall details page")
    public void onRecallDetailsPage(String customer) {
        userIsOnPageWithTitle(customer, RecallDetailsPage.TITLE);
    }

    @Then("{word} is able to see the details submitted earlier")
    public void confirmRecallDetails(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(RecallDetailsPage.DATE_RECALL_EMAIL_RECEIVED).text().isEqualTo("5 Dec 2020 at 15:33"),
                Ensure.that(RecallDetailsPage.RELEASING_PRISON).text().isEqualTo("Belmarsh"),
                Ensure.that(RecallDetailsPage.LAST_RELEASE_DATE).text().isEqualTo("3 Aug 2020"),
                Ensure.that(RecallDetailsPage.LOCAL_POLICE_STATION).text().isEqualTo("Brentwood, Essex")
        );
    }

    @When("{word} starts the assessment process for the recall")
    public void clickOnAssessThisRecallButton(String customer) {
        userClicksOn(customer, RecallDetailsPage.CONTINUE_BUTTON);
    }

    @Then("{word} is on the decision on recall recommendation page")
    public void onDecisionOnRecallRecommendationPage(String customer) {
        userIsOnPageWithTitle(customer, DecisionOnRecallRecommendationPage.TITLE);
        //  FIXME - The following validation is temp disabled due to the UI Bug https://dsdmoj.atlassian.net/browse/PUD-379
        //theActorCalled(customer).attemptsTo(
        //        Ensure.that(DecisionOnRecallRecommendationPage.QUESTION_AROUND_RECALL_LENGTH).text().isEqualTo("Do you agree with the recommended recall length of 14 days")
        //);
    }

    @Then("{word} confirms the recall length as 14 days")
    public void confirmRecallLength(String customer) {
        userIsOnPageWithTitle(customer, DecisionOnRecallRecommendationPage.TITLE);
        userClicksOn(customer, DecisionOnRecallRecommendationPage.RECALL_LENGTH_14_DAYS);
        userClicksOn(customer, DecisionOnRecallRecommendationPage.CONTINUE_BUTTON);
    }

    @Then("the recall is authorised")
    public void confirmRecallAuthorisation() {
        isOnPageWithTitle(RecallAuthorisationPage.TITLE);
    }

    @Then("{word} downloads the documents")
    public void downloadRecallDocument(String customer) {
        userClicksOn(customer, RecallDetailsPage.RECALL_DOCUMENT_LINK_PART_A);
        await().atMost(10, SECONDS).until(partAIsDownloaded());
        userClicksOn(customer, RecallDetailsPage.RECALL_DOCUMENT_LINK_LICENCE);
        await().atMost(10, SECONDS).until(licenceIsDownloaded());
    }


    private Callable<Boolean> partAIsDownloaded() {
        return () -> fileIsDownloaded("/tmp", "part_a_recall_report.pdf");
    }

    private Callable<Boolean> licenceIsDownloaded() {
        return () -> fileIsDownloaded("/tmp", "licence.pdf");
    }

    private Callable<Boolean> revocationOrderIsDownloaded() {
        return () -> fileIsDownloaded("/tmp", "revocation-order.pdf");
    }

    private boolean fileIsDownloaded(String downloadPath, String fileName) {
        File file = new File(downloadPath + "/" + fileName);
        return file.exists() && file.delete();
    }

    private void userIsOnPageWithTitle(String customer, String uniquePageTitle) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(uniquePageTitle)
        );
    }

    private void isOnPageWithTitle(String uniquePageTitle) {
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(uniquePageTitle);
    }

    private void userClicksOn(String customer, Target target) {
        theActorCalled(customer).attemptsTo(
                Click.on(target)
        );
    }

    private void userEnters(String customer, Target target, String value) {
        theActorCalled(customer).attemptsTo(
                Enter.theValue(value).into(target)
        );
    }
}
