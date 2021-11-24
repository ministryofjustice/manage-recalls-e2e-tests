package cucumber.steps;

import cucumber.pages.*;
import cucumber.pages.AssessARecallPage.CreateDossierDetails;
import cucumber.pages.AssessARecallPage.RecallAssessmentDetails;
import cucumber.pages.RecallCheckAnswersPage.*;
import cucumber.questions.UserIsOn;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.questions.WebElementQuestion;
import net.serenitybdd.screenplay.waits.Wait;
import static cucumber.pages.FindAnOffenderPage.BOOK_RECALL_LINK;
import static cucumber.pages.TodoRecallsListPage.FIND_SOMEONE_LINK;
import static cucumber.questions.ReadTextContent.textContent;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.core.Serenity.sessionVariableCalled;
import static net.serenitybdd.core.Serenity.setSessionVariable;
import static net.serenitybdd.core.pages.PageObject.withParameters;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.awaitility.Awaitility.await;

public class NavigationSteps {

    public static final String BOOKING_NUMBER = "A12345";
    public static final String SENTENCING_COURT = "Aberdare County Court";
    private EnvironmentVariables environmentVariables;
    private static final String NOMS_NUMBER = "nomsNumber";

    @Before(order = 1)
    public void setUp() {
        environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        environmentVariables.setProperty("SERENITY_USERNAME", getEnvOrDefault("SERENITY_USERNAME", "PPUD_USER"));
        environmentVariables.setProperty("SERENITY_PASSWORD", getEnvOrDefault("SERENITY_PASSWORD", "password123456"));

        String nomsNumber = getEnvOrDefault("NOMS_NUMBER", "A1234AA");
        setSessionVariable(NOMS_NUMBER).to(nomsNumber);
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
                        Enter.theValue(environmentVariables.getProperty("SERENITY_USERNAME")).into(LoginPage.USERNAME),
                        EnterPassword.theValue(environmentVariables.getProperty("SERENITY_PASSWORD")).into(LoginPage.PASSWORD),
                        Click.on(LoginPage.SIGN_IN_BUTTON)
                ),
                Check.whether(UserIsOn.verifyEmailPage()).andIfSo(
                        Click.on(VerifyEmailPage.SKIP_FOR_NOW)
                )
        );
    }

    @When("{word} enters her user details")
    public void entersUserDetails(String customer) {
        setSessionVariable("loggedInUserDisplayName").to("Maria Badger");
        Actor actor = theActorCalled(customer);
        actor.attemptsTo(
                Open.browserOn().the(UserDetailsPage.class),
                Enter.theValue("Maria").into(UserDetailsPage.FIRST_NAME),
                Enter.theValue("Badger").into(UserDetailsPage.LAST_NAME),
                Enter.theValue("maria.badger@thebadgers.org").into(UserDetailsPage.EMAIL_ADDRESS),
                Enter.theValue("09876543210").into(UserDetailsPage.PHONE_NUMBER),
                Upload.theFile(Path.of("src/test/resources/files/signature.jpg")).to(UserDetailsPage.SIGNATURE),
                Click.on(UserDetailsPage.UPDATE_BUTTON)
        );
    }

    @When("{word} searches for the environment specific NOMS number")
    public void entersNOMSNumber(String customer) {
        String nomsNumber = sessionVariableCalled(NOMS_NUMBER);
        theActorCalled(customer).attemptsTo(
                Click.on(FIND_SOMEONE_LINK),
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(FindAnOffenderPage.TITLE),
                Enter.theValue(nomsNumber).into(FindAnOffenderPage.NOMS_NUMBER_INPUT),
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

    @Then("{word} opens the recall notification")
    public void clickOnRecallNotificationLink(String customer) {
        openDocumentInTab(customer, RecallNotificationDownloadPage.DOWNLOAD_RECALL_NOTIFICATION_LINK);
        theActorCalled(customer).attemptsTo(
                Click.on(RecallNotificationDownloadPage.CONTINUE_BUTTON)
        );
    }

    @When("{word} enters the pre-convictions name")
    public void enterPreConsMainName(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(RecallPreConsNamePage.HAS_OTHER_PRE_CONS_NAME_OPTION),
                Enter.theValue("Wayne Holt").into(RecallPreConsNamePage.OTHER_PRE_CONS_NAME),
                Click.on(RecallPreConsNamePage.CONTINUE_BUTTON)
        );
    }

    @When("{word} submits the date and email of the recall request received from probation service")
    public void submitDateTimeOfRecallRequestReceived(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(RecallReceivedPage.TITLE),
                Enter.theValue("05").into(RecallReceivedPage.DAY_FIELD),
                Enter.theValue("12").into(RecallReceivedPage.MONTH_FIELD),
                Enter.theValue("2020").into(RecallReceivedPage.YEAR_FIELD),
                Enter.theValue("15").into(RecallReceivedPage.HOUR_FIELD),
                Enter.theValue("33").into(RecallReceivedPage.MINUTE_FIELD),
                Upload.theFile(Path.of("src/test/resources/files/email.msg")).to(RecallReceivedPage.UPLOAD_FILE),
                Click.on(RecallReceivedPage.CONTINUE_BUTTON)
        );
    }

    @When("{word} submits the sentence, offence and release details")
    public void submitLatestReleaseDetails(String customer) {
        theActorCalled(customer).attemptsTo(
                Enter.theValue("03").into(LastReleaseDetailsPage.dateDayInput("sentenceDate")),
                Enter.theValue("08").into(LastReleaseDetailsPage.dateMonthInput("sentenceDate")),
                Enter.theValue("2020").into(LastReleaseDetailsPage.dateYearInput("sentenceDate")),
                Enter.theValue("12").into(LastReleaseDetailsPage.dateDayInput("licenceExpiryDate")),
                Enter.theValue("10").into(LastReleaseDetailsPage.dateMonthInput("licenceExpiryDate")),
                Enter.theValue("2024").into(LastReleaseDetailsPage.dateYearInput("licenceExpiryDate")),
                Enter.theValue("03").into(LastReleaseDetailsPage.dateDayInput("sentenceExpiryDate")),
                Enter.theValue("11").into(LastReleaseDetailsPage.dateMonthInput("sentenceExpiryDate")),
                Enter.theValue("2024").into(LastReleaseDetailsPage.dateYearInput("sentenceExpiryDate")),
                Enter.theValue("3").into(LastReleaseDetailsPage.getTargetByName("sentenceLengthYears")),
                Enter.theValue("2").into(LastReleaseDetailsPage.getTargetByName("sentenceLengthMonths")),
                Enter.theValue(SENTENCING_COURT.substring(0, 3)).into(LastReleaseDetailsPage.SENTENCING_COURT),
                Click.on(LastReleaseDetailsPage.getTargetForSentencingCourt(SENTENCING_COURT)),
                Enter.theValue(BOOKING_NUMBER).into(LastReleaseDetailsPage.getTargetByName("bookingNumber")),
                Enter.theValue("Burglary").into(LastReleaseDetailsPage.getTargetByName("indexOffence")),
                Enter.theValue("Ashw").into(LastReleaseDetailsPage.RELEASING_PRISON),
                Click.on(LastReleaseDetailsPage.getTargetForReleasingPrison("Ashwell (HMP)")),
                Enter.theValue("15").into(LastReleaseDetailsPage.dateDayInput("lastReleaseDate")),
                Enter.theValue("03").into(LastReleaseDetailsPage.dateMonthInput("lastReleaseDate")),
                Enter.theValue("2021").into(LastReleaseDetailsPage.dateYearInput("lastReleaseDate")),
                Enter.theValue("24").into(LastReleaseDetailsPage.dateDayInput("conditionalReleaseDate")),
                Enter.theValue("06").into(LastReleaseDetailsPage.dateMonthInput("conditionalReleaseDate")),
                Enter.theValue("2022").into(LastReleaseDetailsPage.dateYearInput("conditionalReleaseDate")),
                Click.on(LastReleaseDetailsPage.CONTINUE_BUTTON)
        );
    }

    @When("{word} submits the police contact details")
    public void submitPoliceContactDetails(String customer) {
        theActorCalled(customer).attemptsTo(
                Enter.theValue("Essex").into(PoliceContactDetailsPage.LOCAL_POLICE_FORCE),
                Click.on(RecallReceivedPage.CONTINUE_BUTTON)
        );
    }

    @When("{word} submits any vulnerability and contraband related details for the offender")
    public void submitVulnerabilityAndContrabandDetails(String customer) {
        setSessionVariable("vulnerabilityDiversityDetail").to("offender vulnerable to bullying");
        setSessionVariable("contrabandDetail").to("yes, the offender has smuggled drugs in the past");
        setSessionVariable("mappaLevel").to("LEVEL_1");
        theActorCalled(customer).attemptsTo(
                Click.on(VulnerabilityAndContrabandDetailsPage.YES_OPTION_FOR_VULNERABILITIES),
                Enter.theValue(Serenity.<String>sessionVariableCalled("vulnerabilityDiversityDetail")).into(VulnerabilityAndContrabandDetailsPage.TEXT_FIELD_FOR_VULNERABILITIES),
                Click.on(VulnerabilityAndContrabandDetailsPage.YES_OPTION_FOR_CONTRABAND),
                Enter.theValue(Serenity.<String>sessionVariableCalled("contrabandDetail")).into(VulnerabilityAndContrabandDetailsPage.TEXT_FIELD_FOR_CONTRABAND),
                SelectFromOptions.byValue(sessionVariableCalled("mappaLevel")).from(VulnerabilityAndContrabandDetailsPage.SELECT_MAPPA_LEVEL),
                Click.on(VulnerabilityAndContrabandDetailsPage.CONTINUE_BUTTON)
        );
    }

    @When("{word} submits the probation officer details")
    public void submitProbationOfficerDetails(String customer) {
        setSessionVariable("probationOfficerName").to("John Smith");
        setSessionVariable("probationOfficerPhoneNumber").to("07775825221");
        setSessionVariable("probationOfficerEmail").to("john.smith@digital.justice.gov.uk");
        setSessionVariable("localDeliveryUnit").to("PS - Derbyshire");
        setSessionVariable("asstChiefOfficerName").to("Jonny Thorn");
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(ProbationDetailsPage.TITLE),
                Enter.theValue("John Smith").into(ProbationDetailsPage.PROBATION_OFFICER_NAME_FIELD),
                Enter.theValue("07775825221").into(ProbationDetailsPage.PROBATION_OFFICER_PHONE_NO_FIELD),
                Enter.theValue("john.smith@digital.justice.gov.uk").into(ProbationDetailsPage.PROBATION_OFFICER_EMAIL_FIELD),
                Enter.theValue("PS - Der").into(ProbationDetailsPage.LOCAL_DELIVERY_UNIT_AUTOCOMPLETE),
                Click.on(ProbationDetailsPage.getTargetForLocalDeliveryUnit("PS - Derbyshire")),
                Enter.theValue("Jonny Thorn").into(ProbationDetailsPage.ASSISTANT_CHIEF_OFFICER_NAME_FIELD),
                Click.on(ProbationDetailsPage.CONTINUE_BUTTON)
        );
    }

    @Then("{word} sees confirmation that the new recall was booked")
    public void matchRecallCreatedText(String customer) {
        userIsOnPageWithTitle(customer, BookRecallConfirmationPage.TITLE);
        theActorCalled(customer).remember("RECALL_ID", textContent(RecallAuthorisationPage.RECALL_ID));
    }

    @When("{word} uploads some documents")
    public void addRecallDocument(String customer) {
        userIsOnPageWithTitle(customer, UploadRecallDocumentsPage.TITLE);
        Path testPdfPath = Path.of("src/test/resources/files/test.pdf");
        Path licencePdfPath = Path.of("src/test/resources/files/Licence.pdf");
        theActorCalled(customer).attemptsTo(
                Upload.theFile(licencePdfPath).to(UploadRecallDocumentsPage.DOCUMENT_UPLOAD),
                Wait.until(WebElementQuestion.the(UploadRecallDocumentsPage.getTargetForCategoryDropdown("LICENCE")), WebElementStateMatchers.isVisible()).forNoLongerThan(10).seconds(),
                Upload.theFile(testPdfPath).to(UploadRecallDocumentsPage.DOCUMENT_UPLOAD),
                Wait.until(WebElementQuestion.the(UploadRecallDocumentsPage.getTargetForCategoryDropdown("UNCATEGORISED")), WebElementStateMatchers.isVisible()).forNoLongerThan(10).seconds(),
                SelectFromOptions.byValue("PART_A_RECALL_REPORT").from(UploadRecallDocumentsPage.getTargetForCategoryDropdown("UNCATEGORISED")),
                Click.on(UploadRecallDocumentsPage.CONTINUE_BUTTON)
        );
    }

    @When("{word} submits the reason for missing documents")
    public void addReasonsForMissingDocuments(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(MissingDocumentsPage.TITLE),
                Enter.theValue("Document is missing as not received from probation yet").into(MissingDocumentsPage.TEXT_FIELD_TO_PROVIDE_MORE_DETAILS),
                Upload.theFile(Path.of("src/test/resources/files/email.msg")).to(MissingDocumentsPage.UPLOAD_FILE),
                Click.on(MissingDocumentsPage.CONTINUE_BUTTON)
        );
    }

    @When("{word} clicks on the Book a recall link")
    public void clickOnBookRecallLink(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(BOOK_RECALL_LINK)
        );
    }

    @When("{word} begins to assess the recall that they have just booked")
    public void assessRecall(String customer) {
        Actor actor = theActorCalled(customer);
        String recallId = actor.recall("RECALL_ID");

        theActorCalled(customer).attemptsTo(
                Click.on(TodoRecallsListPage.NAV_TODO_LINK),
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(TodoRecallsListPage.TITLE),
                Ensure.that(TodoRecallsListPage.dueDateForRecallId(recallId)).text().isEqualTo("6 Dec at 15:33"),
                Ensure.that(TodoRecallsListPage.getTargetByDataQa("continue-booking-" + recallId)).isNotDisplayed(),
                Click.on(TodoRecallsListPage.getTargetByDataQa("assess-recall-" + recallId))
        );
    }

    @When("{word} navigates to the recall to create a dossier")
    public void createDossier(String customer) {
        Actor actor = theActorCalled(customer);
        String recallId = actor.recall("RECALL_ID");

        theActorCalled(customer).attemptsTo(
                Click.on(TodoRecallsListPage.NAV_TODO_LINK),
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(TodoRecallsListPage.TITLE),
                Ensure.that(TodoRecallsListPage.dueDateForRecallId(recallId)).text().isEqualTo("7 Dec"),
                Ensure.that(TodoRecallsListPage.getTargetByDataQa("assess-recall-" + recallId)).isNotDisplayed(),
                Ensure.that(TodoRecallsListPage.getTargetByDataQa("continue-dossier-creation-" + recallId)).isNotDisplayed(),
                Click.on(TodoRecallsListPage.getTargetByDataQa("create-dossier-" + recallId))
        );
    }

    @Then("{word} can check their answers")
    public void bookingCheckAnswers(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(RecallCheckAnswersPage.TITLE),

                // Person details
                Ensure.that(PersonDetails.NAME).text().isNotBlank(),
                Ensure.that(PersonDetails.DATE_OF_BIRTH).text().isNotBlank(),
                Ensure.that(PersonDetails.NOMS_NUMBER).text().isEqualTo(sessionVariableCalled(NOMS_NUMBER)),
                Ensure.that(PersonDetails.PRE_CONS_NAME).text().isEqualTo("Wayne Holt"),
                // Recall
                Ensure.that(RecallDetails.DATE_RECALL_EMAIL_RECEIVED).text().isEqualTo("5 December 2020 at 15:33"),
                // Sentence, offence and release details
                Ensure.that(SentenceOffenceAndReleaseDetails.SENTENCE_TYPE).text().isEqualTo("Determinate"),
                Ensure.that(SentenceOffenceAndReleaseDetails.SENTENCE_DATE).text().isEqualTo("3 August 2020"),
                Ensure.that(SentenceOffenceAndReleaseDetails.LICENCE_EXPIRY_DATE).text().isEqualTo("12 October 2024"),
                Ensure.that(SentenceOffenceAndReleaseDetails.SENTENCE_EXPIRY_DATE).text().isEqualTo("3 November 2024"),
                Ensure.that(SentenceOffenceAndReleaseDetails.SENTENCE_LENGTH).text().isEqualTo("3 years 2 months"),
                Ensure.that(SentenceOffenceAndReleaseDetails.SENTENCING_COURT).text().isEqualTo(SENTENCING_COURT),
                Ensure.that(SentenceOffenceAndReleaseDetails.INDEX_OFFENCE).text().isEqualTo("Burglary"),
                Ensure.that(SentenceOffenceAndReleaseDetails.LAST_RELEASE_PRISON).text().isEqualTo("Ashwell (HMP)"),
                Ensure.that(SentenceOffenceAndReleaseDetails.BOOKING_NUMBER).text().isEqualTo(BOOKING_NUMBER),
                Ensure.that(SentenceOffenceAndReleaseDetails.LAST_RELEASE_DATE).text().isEqualTo("15 March 2021"),
                Ensure.that(SentenceOffenceAndReleaseDetails.CONDITIONAL_RELEASE_DATE).text().isEqualTo("24 June 2022"),
                // local police force
                Ensure.that(LocalPoliceForceDetails.LOCAL_POLICE_FORCE).text().isEqualTo("Essex"),
                // Issues or needs
                Ensure.that(IssuesAndNeedsDetails.VULNERABILITY_DIVERSITY).text().isEqualTo(sessionVariableCalled("vulnerabilityDiversityDetail")),
                Ensure.that(IssuesAndNeedsDetails.CONTRABAND).text().isEqualTo(sessionVariableCalled("contrabandDetail")),
                Ensure.that(IssuesAndNeedsDetails.MAPPA_LEVEL).text().isEqualTo("Level 1"),
                // Probation details
                Ensure.that(ProbationDetails.PROBATION_OFFICER_NAME).text().isEqualTo(sessionVariableCalled("probationOfficerName")),
                Ensure.that(ProbationDetails.PROBATION_OFFICER_PHONE_NO).text().isEqualTo(sessionVariableCalled("probationOfficerPhoneNumber")),
                Ensure.that(ProbationDetails.PROBATION_OFFICER_EMAIL).text().isEqualTo(sessionVariableCalled("probationOfficerEmail")),
                Ensure.that(ProbationDetails.LOCAL_DELIVERY_UNIT).text().isEqualTo(sessionVariableCalled("localDeliveryUnit")),
                Ensure.that(ProbationDetails.ASSISTANT_CHIEF_OFFICER_NAME).text().isEqualTo(sessionVariableCalled("asstChiefOfficerName")),
                //Upload documents
                Ensure.that(DocumentDetails.RECALL_DOCUMENT_LINK_PART_A).text().isEqualTo("Part A.pdf"),
                Ensure.that(DocumentDetails.RECALL_DOCUMENT_LINK_LICENCE).text().isEqualTo("Licence.pdf"),
                Click.on(MissingDocuments.ADD_LINK_FOR_PREVIOUS_CONVICTIONS_SHEET)
        );
    }

    @When("{word} uploads missing documents")
    public void uploadsMissingDocuments(String customer) {
        Path preConsPdfPath = Path.of("src/test/resources/files/Pre cons.pdf");
        Path OASysPdfPath = Path.of("src/test/resources/files/OASys.pdf");

        theActorCalled(customer).attemptsTo(
            Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(UploadRecallDocumentsPage.TITLE),
            Upload.theFile(preConsPdfPath).to(UploadRecallDocumentsPage.DOCUMENT_UPLOAD),
            Wait.until(WebElementQuestion.the(UploadRecallDocumentsPage.getTargetForCategoryDropdown("PREVIOUS_CONVICTIONS_SHEET")), WebElementStateMatchers.isVisible()).forNoLongerThan(10).seconds(),
            Upload.theFile(OASysPdfPath).to(UploadRecallDocumentsPage.DOCUMENT_UPLOAD),
            Wait.until(WebElementQuestion.the(UploadRecallDocumentsPage.getTargetForCategoryDropdown("OASYS_RISK_ASSESSMENT")), WebElementStateMatchers.isVisible()).forNoLongerThan(10).seconds(),
            Click.on(UploadRecallDocumentsPage.CONTINUE_BUTTON)
        );
    }

    @When("{word} starts the assessment process for the recall")
    public void clickOnAssessThisRecallButton(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().startsWith(AssessARecallPage.TITLE),
                Ensure.that(AssessARecallPage.RECALL_ASSESSMENT_DUE_TEXT).text().isEqualTo("Overdue: recall assessment was due on 6 December 2020 by 15:33"),
                Ensure.that(AssessARecallPage.RECALL_STATUS).text().isEqualTo("IN ASSESSMENT"),
                Click.on(AssessARecallPage.CONTINUE_BUTTON)
        );
    }

    @Then("{word} confirms the recall length of {int} days")
    public void confirmRecallLength(String customer, Integer numDays) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(DecisionOnRecallRecommendationPage.TITLE),
                Ensure.that(DecisionOnRecallRecommendationPage.QUESTION_AROUND_RECALL_LENGTH).text().isEqualTo("Do you agree with the fixed term " + numDays + " day recall recommendation?"),
                Click.on(DecisionOnRecallRecommendationPage.YES_RADIO),
                Enter.theValue("yes, agree with the fixed term recall").into(DecisionOnRecallRecommendationPage.MORE_DETAILS_FOR_YES_OPTION_TEXT_BOX),
                Click.on(DecisionOnRecallRecommendationPage.CONTINUE_BUTTON)
        );
    }

    @And("{word} submits the licence breach details")
    public void submitLicenceBreach(String customer) {
        theActorCalled(customer).attemptsTo(
                Enter.theValue("Licence condition 1(a) has been breached").into(AssessLicenceBreachPage.LICENCE_CONDITION_BREACHED_TEXT_BOX),
                Click.on(AssessLicenceBreachPage.REASON_FOR_RECALL_CHECKBOX_OPTION_ONE),
                Click.on(AssessLicenceBreachPage.REASON_FOR_RECALL_CHECKBOX_OPTION_OTHER),
                Enter.theValue("other reason for recall").into(AssessLicenceBreachPage.OTHER_REASON_FOR_RECALL_TEXT_BOX),
                Click.on(AssessLicenceBreachPage.CONTINUE_BUTTON)
        );
    }

    @And("{word} submits the current prison details")
    public void submitCurrentPrison(String customer) {
        theActorCalled(customer).attemptsTo(
                Enter.theValue("As").into(AssessCurrentPrisonPage.CURRENT_PRISON_AUTOCOMPLETE_FIELD),
                Click.on(AssessCurrentPrisonPage.getTargetForCurrentPrison("Ashfield (HMP)")),
                Click.on(AssessCurrentPrisonPage.CONTINUE_BUTTON)
        );
    }

    @And("{word} records the issuance of recall notification")
    public void recordsIssuanceOfRecallNotification(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(RecordIssuanceOfRecallNotificationPage.TITLE),
                Click.on(RecordIssuanceOfRecallNotificationPage.CONFIRM_RECALL_NOTIFICATION_EMAIL_SENT_CHECKBOX),
                Enter.theValue("05").into(RecordIssuanceOfRecallNotificationPage.DAY_FIELD),
                Enter.theValue("12").into(RecordIssuanceOfRecallNotificationPage.MONTH_FIELD),
                Enter.theValue("2020").into(RecordIssuanceOfRecallNotificationPage.YEAR_FIELD),
                Enter.theValue("15").into(RecordIssuanceOfRecallNotificationPage.HOUR_FIELD),
                Enter.theValue("33").into(RecordIssuanceOfRecallNotificationPage.MINUTE_FIELD),
                Upload.theFile(Path.of("src/test/resources/files/email.msg")).to(RecordIssuanceOfRecallNotificationPage.UPLOAD_FILE),
                Click.on(RecordIssuanceOfRecallNotificationPage.CONTINUE_BUTTON));
    }

    @Then("{word} can see that the recall is assessed")
    public void confirmRecallAuthorisation(String customer) {
        userIsOnPageWithTitle(customer, RecallAuthorisationPage.TITLE);
        theActorCalled(customer).remember("RECALL_ID", textContent(RecallAuthorisationPage.RECALL_ID));
    }

    @Then("{word} can see that they are unassigned from the recall")
    public void confirmUnassigned(String customer) {
        theActorCalled(customer).remember("RECALL_ID", textContent(RecallAuthorisationPage.RECALL_ID));
        String recallId = theActorCalled(customer).recall("RECALL_ID");
        theActorCalled(customer).attemptsTo(
            Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(RecallAuthorisationPage.TITLE),
            Click.on(RecallAuthorisationPage.NAV_TODO_LINK),
            Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(TodoRecallsListPage.TITLE),
            Ensure.that(TodoRecallsListPage.assignedToForRecallId(recallId)).text().isEqualTo("")
        );
    }

    @Then("{word} opens the documents")
    public void downloadRecallDocument(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(RecallCheckAnswersPage.TITLE)
        );
        openDocumentInTab(customer, DocumentDetails.RECALL_DOCUMENT_LINK_PART_A);
        openDocumentInTab(customer, DocumentDetails.RECALL_DOCUMENT_LINK_LICENCE);
        theActorCalled(customer).attemptsTo(
                Click.on(RecallCheckAnswersPage.CONTINUE_BUTTON)
        );
    }

    @Then("{word} navigates to view the details captured during assessment")
    public void navigateToViewRecallAssessmentDetails(String customer) {
        new AssessARecallPage().open("assess.recall", withParameters(sessionVariableCalled(NOMS_NUMBER), theActorCalled(customer).recall("RECALL_ID")));
    }

    @Then("{word} is able to see the details captured during assessment")
    public void confirmRecallDetailsCapturedDuringAssessment(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(AssessARecallPage.RECALL_STATUS).text().isEqualTo("RECALL NOTIFICATION ISSUED"),
                Ensure.that(RecallAssessmentDetails.ASSESSED_BY_USERNAME).text().isEqualTo(sessionVariableCalled("loggedInUserDisplayName")),
                Ensure.that(RecallAssessmentDetails.BOOKED_BY_USERNAME).text().isEqualTo(sessionVariableCalled("loggedInUserDisplayName")),
                Ensure.that(RecallAssessmentDetails.AGREE_WITH_RECALL_RECOMMENDATION_ADDITIONAL_TEXT).text().isEqualTo("yes, agree with the fixed term recall"),
                Ensure.that(RecallAssessmentDetails.LICENCE_CONDITIONS_BREACHED).text().isEqualTo("Licence condition 1(a) has been breached"),
                Ensure.that(RecallAssessmentDetails.REASON_FOR_RECALL_OPTION_ONE).text().isEqualTo("Breach of exclusion zone"),
                Ensure.that(RecallAssessmentDetails.REASON_FOR_RECALL_OPTION_OTHER).text().isEqualTo("Other - other reason for recall")
        );
    }

    @Then("{word} is able to see the documents uploaded during booking")
    public void confirmDocumentsUploadedDuringBooking(String customer) {
        theActorCalled(customer).attemptsTo(
                //Upload documents
                Ensure.that(DocumentDetails.RECALL_DOCUMENT_LINK_PART_A).text().isEqualTo("Part A.pdf"),
                Ensure.that(DocumentDetails.RECALL_DOCUMENT_LINK_LICENCE).text().isEqualTo("Licence.pdf")
        );
    }

    @Then("{word} can download the email")
    public void downloadEmail(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(RecallAssessmentDetails.DATETIME_RECALL_NOTIFICATION_EMAIL_SENT).text().isEqualTo("5 December 2020 at 15:33"),
                Ensure.that(RecallAssessmentDetails.UPLOADED_RECALL_NOTIFICATION_EMAIL_LINK).text().isEqualTo("email.msg"),
                Click.on(RecallAssessmentDetails.UPLOADED_RECALL_NOTIFICATION_EMAIL_LINK)
        );
        await().atMost(10, SECONDS).until(recallNotificationEmailIsDownloaded());
    }

    @And("{word} submits the information for the prison letter")
    public void submitInfoForPrisonLetter(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(CreateDossierAddInfoForPrisonLetterPage.ADDITIONAL_LICENCE_CONDITIONS_RADIO_BUTTON),
                Enter.theValue("Licence condition 14(a)").into(CreateDossierAddInfoForPrisonLetterPage.MORE_DETAILS_FOR_ADDITIONAL_LICENCE_CONDITIONS_TEXT_BOX),
                Click.on(CreateDossierAddInfoForPrisonLetterPage.DIFFERENT_NOMIS_NUMBER_RADIO_BUTTON),
                Enter.theValue("A4321AA").into(CreateDossierAddInfoForPrisonLetterPage.MORE_DETAILS_FOR_DIFFERENT_NOMIS_NUMBER_TEXT_BOX),
                Click.on(CreateDossierAddInfoForPrisonLetterPage.CONTINUE_BUTTON)
        );
    }

    @And("{word} has checked and created the reasons for recall document")
    public void checkCreateReasonsRecallDoc(String customer) {
        userIsOnPageWithTitle(customer, CreateDossierCheckReasonsDocPage.TITLE);
        theActorCalled(customer).attemptsTo(
                Ensure.that(CreateDossierCheckReasonsDocPage.NOMS_NUMBER).text().isEqualTo(sessionVariableCalled(NOMS_NUMBER)),
                Ensure.that(CreateDossierCheckReasonsDocPage.BOOKING_NUMBER).text().isEqualTo(BOOKING_NUMBER),
                Ensure.that(CreateDossierCheckReasonsDocPage.LICENCE_CONDITIONS_BREACHED).text().isEqualTo("Licence condition 1(a) has been breached"),
                Ensure.that(CreateDossierCheckReasonsDocPage.RECALL_TYPE).text().isEqualTo("Fixed term"),
                Ensure.that(CreateDossierCheckReasonsDocPage.RECALL_LENGTH).text().isEqualTo("28 days"),
                Click.on(CreateDossierCheckReasonsDocPage.CONTINUE_BUTTON)
        );
    }

    @Then("{word} is able to see the recall information before creating a dossier")
    public void confirmRecallDetailsBeforeDossier(String customer) {
        openDocumentInTab(customer, CreateDossierDetails.REVOCATION_ORDER_DOCUMENT_LINK);
        theActorCalled(customer).attemptsTo(
                Ensure.that(CreateDossierDetails.DOSSIER_DUE_DATE).text().isEqualTo("Overdue: Due on 7 December 2020"),
                Ensure.that(RecallAssessmentDetails.CURRENT_PRISON).text().isEqualTo("Ashfield (HMP)"),
                Ensure.that(AssessARecallPage.RECALL_STATUS).text().isEqualTo("DOSSIER IN PROGRESS"),
                Click.on(AssessARecallPage.CONTINUE_BUTTON)
        );
    }

    @Then("{word} can open the dossier and letter")
    public void canDownloadTheDossier(String customer) {
        userIsOnPageWithTitle(customer, CreateDossierDownloadDossierAndLetterPage.TITLE);
        openDocumentInTab(customer, CreateDossierDownloadDossierAndLetterPage.DOWNLOAD_DOSSIER_LINK);
        openDocumentInTab(customer, CreateDossierDownloadDossierAndLetterPage.DOWNLOAD_LETTER_LINK);
    }

    @When("{word} has reviewed the dossier")
    public void hasReviewedTheDossier(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(CreateDossierDownloadDossierAndLetterPage.TITLE),
                Click.on(CreateDossierDownloadDossierAndLetterPage.CONFIRM_DOSSIER_CHECKED),
                Click.on(CreateDossierDownloadDossierAndLetterPage.CONTINUE_BUTTON)
        );
    }

    @When("{word} records that the dossier was emailed")
    public void hasEmailedTheDossier(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(CreateDossierConfirmEmailedPage.TITLE),
                Click.on(CreateDossierConfirmEmailedPage.CONFIRM_DOSSIER_EMAIL_SENT_CHECKBOX),
                Enter.theValue("05").into(CreateDossierConfirmEmailedPage.DAY_FIELD),
                Enter.theValue("12").into(CreateDossierConfirmEmailedPage.MONTH_FIELD),
                Enter.theValue("2020").into(CreateDossierConfirmEmailedPage.YEAR_FIELD),
                Upload.theFile(Path.of("src/test/resources/files/email.msg")).to(CreateDossierConfirmEmailedPage.UPLOAD_FILE),
                Click.on(CreateDossierConfirmEmailedPage.CONTINUE_BUTTON)
        );
    }

    @Then("{word} gets a confirmation that the dossier creation is complete")
    public void confirmDossierCreation(String customer) {
        userIsOnPageWithTitle(customer, DossierCreationConfirmationPage.TITLE);
        theActorCalled(customer).remember("RECALL_ID", textContent(DossierCreationConfirmationPage.RECALL_ID));
    }

    @Then("{word} can see that they are unassigned from the recall after dossier creation is complete")
    public void confirmUnassignedAfterCompletionOfDossierCreation(String customer) {
        theActorCalled(customer).remember("RECALL_ID", textContent(DossierCreationConfirmationPage.RECALL_ID));
        String recallId = theActorCalled(customer).recall("RECALL_ID");
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(DossierCreationConfirmationPage.TITLE),
                Click.on(DossierCreationConfirmationPage.NAV_TODO_LINK),
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(TodoRecallsListPage.TITLE),
                Ensure.that(TodoRecallsListPage.assignedToForRecallId(recallId)).text().isEqualTo("")
        );
    }

    @Then("{word} is able to see the details captured during dossier creation")
    public void confirmDetailsCapturedDuringDossierCreation(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(AssessARecallPage.RECALL_STATUS).text().isEqualTo("DOSSIER ISSUED"),
                Ensure.that(CreateDossierDetails.DOSSIER_CREATED_BY_USERNAME).text().isEqualTo(sessionVariableCalled("loggedInUserDisplayName")),
                Ensure.that(CreateDossierDetails.ADDITIONAL_LICENCE_CONDITIONS).text().isEqualTo("Licence condition 14(a)"),
                Ensure.that(CreateDossierDetails.DIFFERENT_NOMIS_NUMBER).text().isEqualTo("A4321AA"),
                Ensure.that(CreateDossierDetails.DATE_DOSSIER_EMAIL_SENT).text().isEqualTo("5 December 2020"),
                Ensure.that(CreateDossierDetails.UPLOADED_DOSSIER_EMAIL_LINK).text().isEqualTo("email.msg")
        );
    }

    @Then("{word} can download the dossier email")
    public void downloadDossierEmail(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(CreateDossierDetails.UPLOADED_DOSSIER_EMAIL_LINK)
        );
        await().atMost(10, SECONDS).until(recallDossierEmailIsDownloaded());
    }

    @When("{word} navigates to view the details captured during dossier creation")
    public void viewDossierCreationDetails(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(TodoRecallsListPage.COMPLETED_TAB),
                Click.on(TodoRecallsListPage.viewLinkForRecallId(theActorCalled(customer).recall("RECALL_ID")))
        );
    }

    private Callable<Boolean> recallNotificationEmailIsDownloaded() {
        return () -> fileIsDownloaded("/tmp", "email.msg");
    }

    private Callable<Boolean> recallDossierEmailIsDownloaded() {
        return () -> fileIsDownloaded("/tmp", "email.msg");
    }

    private boolean fileIsDownloaded(String downloadPath, String fileName) {
        File file = new File(downloadPath + "/" + fileName);
        return file.exists() && file.delete();
    }

    private void openDocumentInTab(String customer, Target link) {
        Actor actor = theActorCalled(customer);
        actor.attemptsTo(
                Click.on(link)
        );
        String linkHref = link.resolveFor(actor).getAttribute("href");
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        String oldTab = driver.getWindowHandle();
        List<String> allTabs = new ArrayList<>(driver.getWindowHandles());
        allTabs.remove(oldTab);
        //This fails when running in headless mode. appears to be an issue with chromedriver
        actor.attemptsTo(
                Switch.toWindow(allTabs.get(0)),
                Ensure.thatTheCurrentPage().currentUrl().isEqualTo(linkHref),
                Ensure.thatTheCurrentPage().pageSource().doesNotContain("Internal Server Error")
        );
        driver.close();
        driver.switchTo().window(oldTab);
    }

    private void userIsOnPageWithTitle(String customer, String uniquePageTitle) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(uniquePageTitle)
        );
    }
}
