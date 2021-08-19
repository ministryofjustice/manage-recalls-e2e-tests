package cucumber.steps;

import cucumber.pages.*;
import cucumber.questions.UserIsOn;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.pages.components.HtmlTable;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.concurrent.Callable;

import static cucumber.pages.FindAnOffenderPage.VIEW_PROFILE_LINK;
import static cucumber.pages.OffenderProfilePage.CREATE_RECALL_BUTTON;
import static cucumber.pages.TodoRecallsListPage.*;
import static cucumber.questions.ReadTextContent.textContent;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;
import static net.serenitybdd.screenplay.actors.OnStage.*;
import static net.thucydides.core.matchers.BeanMatchers.the;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.is;

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

    @And("{word} navigates to the 'To do' list")
    public void clickRecallListToDoLink(String customer) {
        userClicksOn(customer, RECALL_LIST_TODO_LINK);
        userIsOnPageWithTitle(customer, TodoRecallsListPage.TITLE);
    }

    @Then("{word} is on the Find a person page")
    public void onStartPage(String customer) {
        userIsOnPageWithTitle(customer, FindAnOffenderPage.TITLE);
    }

    @Then("{word} is on the Person profile page")
    public void onOffenderProfilePage(String customer) {
        userIsOnPageWithTitle(customer, OffenderProfilePage.TITLE);
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

    @Then("{word} downloads revocation order")
    public void clickOnRevocationOrderLink(String customer) {
        userClicksOn(customer, RecallAuthorisationPage.DOWNLOAD_REVOCATION_ORDER_LINK);
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

    @When("{word} submits the sentence, offence and release details")
    public void submitLatestReleaseDetails(String customer) {
        userEnters(customer, LastReleaseDetailsPage.dateDayInput("sentenceDate"), "03");
        userEnters(customer, LastReleaseDetailsPage.dateMonthInput("sentenceDate"), "08");
        userEnters(customer, LastReleaseDetailsPage.dateYearInput("sentenceDate"), "2020");
        userEnters(customer, LastReleaseDetailsPage.dateDayInput("licenceExpiryDate"), "12");
        userEnters(customer, LastReleaseDetailsPage.dateMonthInput("licenceExpiryDate"), "10");
        userEnters(customer, LastReleaseDetailsPage.dateYearInput("licenceExpiryDate"), "2021");
        userEnters(customer, LastReleaseDetailsPage.dateDayInput("sentenceExpiryDate"), "03");
        userEnters(customer, LastReleaseDetailsPage.dateMonthInput("sentenceExpiryDate"), "11");
        userEnters(customer, LastReleaseDetailsPage.dateYearInput("sentenceExpiryDate"), "2021");
        userEnters(customer, LastReleaseDetailsPage.getTargetByName("sentenceLengthYears"), "3");
        userEnters(customer, LastReleaseDetailsPage.getTargetByName("sentenceLengthMonths"), "2");
        userEnters(customer, LastReleaseDetailsPage.getTargetByName("sentencingCourt"), "Manchester Crown Court");
        userEnters(customer, LastReleaseDetailsPage.getTargetByName("bookingNumber"), "A123456");
        userEnters(customer, LastReleaseDetailsPage.getTargetByName("indexOffence"), "Burglary");
        userEnters(customer, LastReleaseDetailsPage.getTargetByName("lastReleasePrison"), "Belmarsh");
        userEnters(customer, LastReleaseDetailsPage.dateDayInput("lastReleaseDate"), "15");
        userEnters(customer, LastReleaseDetailsPage.dateMonthInput("lastReleaseDate"), "03");
        userEnters(customer, LastReleaseDetailsPage.dateYearInput("lastReleaseDate"), "2021");
        userEnters(customer, LastReleaseDetailsPage.dateDayInput("conditionalReleaseDate"), "24");
        userEnters(customer, LastReleaseDetailsPage.dateMonthInput("conditionalReleaseDate"), "06");
        userEnters(customer, LastReleaseDetailsPage.dateYearInput("conditionalReleaseDate"), "2022");
        userClicksOn(customer, LastReleaseDetailsPage.CONTINUE_BUTTON);
    }

    @When("{word} submits the police contact details")
    public void submitPoliceContactDetails(String customer) {
        userEnters(customer, PoliceContactDetailsPage.LOCAL_POLICE_SERVICE, "Brentwood, Essex");
        userClicksOn(customer, RecallReceivedPage.CONTINUE_BUTTON);
    }

    @When("{word} submits any vulnerability and contraband related details for the offender")
    public void submitVulnerabilityAndContrabandDetails(String customer) {
        setSessionVariable("vulnerabilityDiversityDetail").to("offender vulnerable to bullying");
        setSessionVariable("contrabandDetail").to("yes, the offender has smuggled drugs in the past");
        setSessionVariable("mappaLevel").to("LEVEL_1");
        userClicksOn(customer, VulnerabilityAndContrabandDetailsPage.YES_OPTION_FOR_VULNERABILITIES);
        userEnters(customer, VulnerabilityAndContrabandDetailsPage.TEXT_FIELD_FOR_VULNERABILITIES, sessionVariableCalled("vulnerabilityDiversityDetail"));
        userClicksOn(customer, VulnerabilityAndContrabandDetailsPage.YES_OPTION_FOR_CONTRABAND);
        userEnters(customer, VulnerabilityAndContrabandDetailsPage.TEXT_FIELD_FOR_CONTRABAND, sessionVariableCalled("contrabandDetail"));
        theActorCalled(customer).attemptsTo(
                SelectFromOptions.byValue(sessionVariableCalled("mappaLevel")).from(VulnerabilityAndContrabandDetailsPage.SELECT_MAPPA_LEVEL)
        );
        userClicksOn(customer, VulnerabilityAndContrabandDetailsPage.CONTINUE_BUTTON);
    }

    @Then("{word} continues from the Book a recall page")
    public void onBookRecallPage(String customer) {
        userIsOnPageWithTitle(customer, BookRecallPage.TITLE);
        userClicksOn(customer, BookRecallPage.CONTINUE_BUTTON);
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

    @When("{word} clicks on the View link for the recall that they have just booked")
    public void clickOnViewLink(String customer) {
        Actor actor = theActorCalled(customer);
        String recallId = actor.recall("RECALL_ID");
        WebElement firstRow = HtmlTable.inTable(RECALLS_TABLE.resolveFor(actor))
                .findFirstRowWhere(the("Recall ID", is(recallId)));

        WebElement viewRecallLink = firstRow.findElement(By.cssSelector("[data-qa='viewRecallDetails']"));

        viewRecallLink.click();
    }

    @Then("{word} is on the Recall details page")
    public void onRecallDetailsPage(String customer) {
        userIsOnPageWithTitle(customer, RecallDetailsPage.TITLE);
    }

    @Then("{word} is able to see the details submitted earlier")
    public void confirmRecallDetails(String customer) {
        theActorCalled(customer).attemptsTo(
                // Recall
                Ensure.that(RecallDetailsPage.DATE_RECALL_EMAIL_RECEIVED).text().isEqualTo("5 Dec 2020 at 15:33"),
                Ensure.that(RecallDetailsPage.LOCAL_POLICE_STATION).text().isEqualTo("Brentwood, Essex"),
                // Issues and needs
                Ensure.that(RecallDetailsPage.VULNERABILITY_DIVERSITY_DETAIL).text().isEqualTo(sessionVariableCalled("vulnerabilityDiversityDetail")),
                Ensure.that(RecallDetailsPage.CONTRABAND_DETAIL).text().isEqualTo(sessionVariableCalled("contrabandDetail")),
                Ensure.that(RecallDetailsPage.MAPPA_LEVEL).text().isEqualTo("Level 1"),
                // Sentence, offence and release details
                Ensure.that(RecallDetailsPage.getTargetByDataQa("sentenceDate")).text().isEqualTo("3 Aug 2020"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("sentenceExpiryDate")).text().isEqualTo("3 Nov 2021"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("licenceExpiryDate")).text().isEqualTo("12 Oct 2021"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("conditionalReleaseDate")).text().isEqualTo("24 Jun 2022"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("lastReleasePrison")).text().isEqualTo("Belmarsh"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("lastReleaseDate")).text().isEqualTo("15 Mar 2021"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("sentenceLength")).text().isEqualTo("3 years 2 months")
                Ensure.that(RecallDetailsPage.getTargetByDataQa("bookingNumber")).text().isEqualTo("A123456")
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

    @Then("{word} can see that the recall is authorised")
    public void confirmRecallAuthorisation(String customer) {
        userIsOnPageWithTitle(customer, RecallAuthorisationPage.TITLE);
        theActorCalled(customer).remember("RECALL_ID", textContent(RecallAuthorisationPage.RECALL_ID));
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
