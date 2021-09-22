package cucumber.steps;

import cucumber.pages.AssessARecallPage;
import cucumber.pages.AssessARecallPage.CreateDossierDetails;
import cucumber.pages.AssessARecallPage.RecallAssessmentDetails;
import cucumber.pages.AssessCurrentPrisonPage;
import cucumber.pages.AssessLicenceBreachPage;
import cucumber.pages.BookRecallConfirmationPage;
import cucumber.pages.CreateDossierAddInfoForPrisonLetterPage;
import cucumber.pages.CreateDossierCheckReasonsDocPage;
import cucumber.pages.CreateDossierConfirmEmailedPage;
import cucumber.pages.CreateDossierDownloadDossierAndLetterPage;
import cucumber.pages.DecisionOnRecallRecommendationPage;
import cucumber.pages.DossierCreationConfirmationPage;
import cucumber.pages.FindAnOffenderPage;
import cucumber.pages.LastReleaseDetailsPage;
import cucumber.pages.LoginPage;
import cucumber.pages.PoliceContactDetailsPage;
import cucumber.pages.ProbationDetailsPage;
import cucumber.pages.RecallAuthorisationPage;
import cucumber.pages.RecallCheckAnswersPage;
import cucumber.pages.RecallCheckAnswersPage.DocumentDetails;
import cucumber.pages.RecallCheckAnswersPage.IssuesAndNeedsDetails;
import cucumber.pages.RecallCheckAnswersPage.LocalPoliceForceDetails;
import cucumber.pages.RecallCheckAnswersPage.PersonDetails;
import cucumber.pages.RecallCheckAnswersPage.ProbationDetails;
import cucumber.pages.RecallCheckAnswersPage.RecallDetails;
import cucumber.pages.RecallCheckAnswersPage.SentenceOffenceAndReleaseDetails;
import cucumber.pages.RecallNotificationDownloadPage;
import cucumber.pages.RecallPreConsNamePage;
import cucumber.pages.RecallReceivedPage;
import cucumber.pages.RecordIssuanceOfRecallNotificationPage;
import cucumber.pages.TodoRecallsListPage;
import cucumber.pages.UploadRecallDocumentsPage;
import cucumber.pages.UserDetailsPage;
import cucumber.pages.VerifyEmailPage;
import cucumber.pages.VulnerabilityAndContrabandDetailsPage;
import cucumber.questions.UserIsOn;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.*;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.conditions.Check;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.ArrayList;

import static cucumber.pages.FindAnOffenderPage.BOOK_RECALL_LINK;
import static cucumber.pages.TodoRecallsListPage.FIND_SOMEONE_LINK;
import static cucumber.questions.ReadTextContent.textContent;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.core.Serenity.*;
import static net.serenitybdd.core.pages.PageObject.withParameters;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.awaitility.Awaitility.await;
import org.openqa.selenium.WebDriver;

public class NavigationSteps {

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
        theActorCalled(customer).attemptsTo(
                Open.browserOn().the(UserDetailsPage.class),
                Enter.theValue("Maria").into(UserDetailsPage.FIRST_NAME_TEXT_BOX),
                Enter.theValue("Badger").into(UserDetailsPage.LAST_NAME_TEXT_BOX),
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

    @When("{word} submits the date and time of the recall request received from probation service")
    public void submitDateTimeOfRecallRequestReceived(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(RecallReceivedPage.TITLE),
                Enter.theValue("05").into(RecallReceivedPage.DAY_FIELD),
                Enter.theValue("12").into(RecallReceivedPage.MONTH_FIELD),
                Enter.theValue("2020").into(RecallReceivedPage.YEAR_FIELD),
                Enter.theValue("15").into(RecallReceivedPage.HOUR_FIELD),
                Enter.theValue("33").into(RecallReceivedPage.MINUTE_FIELD),
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
                Enter.theValue("Manchester Crown Court").into(LastReleaseDetailsPage.getTargetByName("sentencingCourt")),
                Enter.theValue("A123456").into(LastReleaseDetailsPage.getTargetByName("bookingNumber")),
                Enter.theValue("Burglary").into(LastReleaseDetailsPage.getTargetByName("indexOffence")),
                Enter.theValue("Ashw").into(LastReleaseDetailsPage.RELEASING_PRISON_AUTOCOMPLETE_FIELD),
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

    @When("{word} uploads two documents")
    public void addRecallDocument(String customer) {
        userIsOnPageWithTitle(customer, UploadRecallDocumentsPage.TITLE);
        UploadRecallDocumentsPage page = new UploadRecallDocumentsPage();
        page.uploadFile("src/test/resources/files/test.pdf", "PART_A_RECALL_REPORT");
        page.uploadFile("src/test/resources/files/test.pdf", "LICENCE");
        page.uploadFile("src/test/resources/files/test.pdf", "PREVIOUS_CONVICTIONS_SHEET");
        page.uploadFile("src/test/resources/files/test.pdf", "PRE_SENTENCING_REPORT");
        theActorCalled(customer).attemptsTo(
                Click.on(UploadRecallDocumentsPage.CONTINUE_BUTTON)
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
                Click.on(TodoRecallsListPage.RECALL_LIST_TODO_LINK),
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(TodoRecallsListPage.TITLE),
                Ensure.that(TodoRecallsListPage.getTargetByDataQa("continue-booking-" + recallId)).isNotDisplayed(),
                Click.on(TodoRecallsListPage.getTargetByDataQa("assess-recall-" + recallId))
        );
    }

    @When("{word} navigates to the recall to create a dossier")
    public void createDossier(String customer) {
        Actor actor = theActorCalled(customer);
        String recallId = actor.recall("RECALL_ID");

        theActorCalled(customer).attemptsTo(
                Click.on(TodoRecallsListPage.RECALL_LIST_TODO_LINK),
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(TodoRecallsListPage.TITLE),
                Ensure.that(TodoRecallsListPage.getTargetByDataQa("assess-recall-" + recallId)).isNotDisplayed(),
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
                Ensure.that(PersonDetails.GENDER).text().isNotBlank(),
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
                Ensure.that(SentenceOffenceAndReleaseDetails.SENTENCING_COURT).text().isEqualTo("Manchester Crown Court"),
                Ensure.that(SentenceOffenceAndReleaseDetails.INDEX_OFFENCE).text().isEqualTo("Burglary"),
                Ensure.that(SentenceOffenceAndReleaseDetails.LAST_RELEASE_PRISON).text().isEqualTo("Ashwell (HMP)"),
                Ensure.that(SentenceOffenceAndReleaseDetails.BOOKING_NUMBER).text().isEqualTo("A123456"),
                Ensure.that(SentenceOffenceAndReleaseDetails.LAST_RELEASE_DATE).text().isEqualTo("15 March 2021"),
                Ensure.that(SentenceOffenceAndReleaseDetails.CONDITIONAL_RELEASE_DATE).text().isEqualTo("24 June 2022"),
                // local police force
                Ensure.that(LocalPoliceForceDetails.LOCAL_POLICE_FORCE).text().isEqualTo("Essex"),
                // Issues or needs
                Ensure.that(IssuesAndNeedsDetails.VULNERABILITY_DIVERSITY_DETAIL).text().isEqualTo(sessionVariableCalled("vulnerabilityDiversityDetail")),
                Ensure.that(IssuesAndNeedsDetails.CONTRABAND_DETAIL).text().isEqualTo(sessionVariableCalled("contrabandDetail")),
                Ensure.that(IssuesAndNeedsDetails.MAPPA_LEVEL).text().isEqualTo("Level 1"),
                // Probation details
                Ensure.that(ProbationDetails.PROBATION_OFFICER_NAME).text().isEqualTo(sessionVariableCalled("probationOfficerName")),
                Ensure.that(ProbationDetails.PROBATION_OFFICER_PHONE_NO).text().isEqualTo(sessionVariableCalled("probationOfficerPhoneNumber")),
                Ensure.that(ProbationDetails.PROBATION_OFFICER_EMAIL).text().isEqualTo(sessionVariableCalled("probationOfficerEmail")),
                Ensure.that(ProbationDetails.LOCAL_DELIVERY_UNIT).text().isEqualTo(sessionVariableCalled("localDeliveryUnit")),
                Ensure.that(ProbationDetails.ASSISTANT_CHIEF_OFFICER_NAME).text().isEqualTo(sessionVariableCalled("asstChiefOfficerName"))
        );
    }

    @When("{word} starts the assessment process for the recall")
    public void clickOnAssessThisRecallButton(String customer) {
        theActorCalled(customer).attemptsTo(
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
    @Then("{word} can see that the recall is authorised")
    public void confirmRecallAuthorisation(String customer) {
        userIsOnPageWithTitle(customer, RecallAuthorisationPage.TITLE);
        theActorCalled(customer).remember("RECALL_ID", textContent(RecallAuthorisationPage.RECALL_ID));
    }

    @Then("{word} opens the documents")
    public void downloadRecallDocument(String customer){
        openDocumentInTab(customer, DocumentDetails.RECALL_DOCUMENT_LINK_PART_A);
        openDocumentInTab(customer, DocumentDetails.RECALL_DOCUMENT_LINK_LICENCE);
        openDocumentInTab(customer, DocumentDetails.RECALL_DOCUMENT_LINK_PREVIOUS_CONVICTIONS_SHEET);
        openDocumentInTab(customer, DocumentDetails.RECALL_DOCUMENT_LINK_PRE_SENTENCING_REPORT);
        theActorCalled(customer).attemptsTo(
            Click.on(RecallCheckAnswersPage.CONTINUE_BUTTON)
        );
    }

    @Then("{word} navigates to view the details captured during assessment")
    public void navigateToViewRecallAssessmentDetails(String customer){
        new AssessARecallPage().open("assess.recall", withParameters(sessionVariableCalled(NOMS_NUMBER), theActorCalled(customer).recall("RECALL_ID")));
        userIsOnPageWithTitle(customer, AssessARecallPage.TITLE);
    }

    @Then("{word} is able to see the details captured during assessment")
    public void confirmRecallDetailsCapturedDuringAssessment(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(RecallAssessmentDetails.ASSESSED_BY_USERNAME).text().isEqualTo(sessionVariableCalled("loggedInUserDisplayName")),
                Ensure.that(RecallAssessmentDetails.BOOKED_BY_USERNAME).text().isEqualTo(sessionVariableCalled("loggedInUserDisplayName")),
                Ensure.that(RecallAssessmentDetails.AGREE_WITH_RECALL_RECOMMENDATION).text().isEqualTo("Yes"),
                Ensure.that(RecallAssessmentDetails.AGREE_WITH_RECALL_RECOMMENDATION_ADDITIONAL_TEXT).text().isEqualTo("yes, agree with the fixed term recall"),
                Ensure.that(RecallAssessmentDetails.LICENCE_CONDITIONS_BREACHED).text().isEqualTo("Licence condition 1(a) has been breached"),
                Ensure.that(RecallAssessmentDetails.REASON_FOR_RECALL_OPTION_ONE).text().isEqualTo("Breach of exclusion zone"),
                Ensure.that(RecallAssessmentDetails.REASON_FOR_RECALL_OPTION_OTHER).text().isEqualTo("Other"),
                Ensure.that(RecallAssessmentDetails.OTHER_REASON_FOR_RECALL_TEXT).text().isEqualTo("other reason for recall"),
                Ensure.that(RecallAssessmentDetails.CURRENT_PRISON).text().isEqualTo("Ashfield (HMP)"),
                Ensure.that(RecallAssessmentDetails.DATETIME_RECALL_NOTIFICATION_EMAIL_SENT).text().isEqualTo("5 December 2020 at 15:33"),
                Ensure.that(RecallAssessmentDetails.UPLOADED_RECALL_NOTIFICATION_EMAIL_LINK).text().isEqualTo("email.msg")
        );
    }

    @Then("{word} can download the email")
    public void downloadEmail(String customer) {
        theActorCalled(customer).attemptsTo(
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
            Ensure.that(CreateDossierCheckReasonsDocPage.BOOKING_NUMBER).text().isEqualTo("A123456"),
            Ensure.that(CreateDossierCheckReasonsDocPage.LICENCE_CONDITIONS_BREACHED).text().isEqualTo("Licence condition 1(a) has been breached"),
            Ensure.that(CreateDossierCheckReasonsDocPage.RECALL_TYPE).text().isEqualTo("Fixed term"),
            Ensure.that(CreateDossierCheckReasonsDocPage.RECALL_LENGTH).text().isEqualTo("28 days"),
            Click.on(CreateDossierCheckReasonsDocPage.CONTINUE_BUTTON)
        );
    }

    @Then("{word} can open the dossier")
    public void canDownloadTheDossier(String customer) {
        userIsOnPageWithTitle(customer, CreateDossierDownloadDossierAndLetterPage.TITLE);
        openDocumentInTab(customer, CreateDossierDownloadDossierAndLetterPage.DOWNLOAD_DOSSIER_LINK);
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

    @Then("{word} is able to see the details captured during dossier creation")
    public void confirmDetailsCapturedDuringDossierCreation(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(CreateDossierDetails.DOSSIER_CREATED_BY_USERNAME).text().isEqualTo(sessionVariableCalled("loggedInUserDisplayName")),
                Ensure.that(CreateDossierDetails.ADDITIONAL_LICENCE_CONDITIONS).text().isEqualTo("Yes"),
                Ensure.that(CreateDossierDetails.MORE_DETAILS_FOR_ADDITIONAL_LICENCE_CONDITIONS_TEXT).text().isEqualTo("Licence condition 14(a)"),
                Ensure.that(CreateDossierDetails.DIFFERENT_NOMIS_NUMBER).text().isEqualTo("Yes"),
                Ensure.that(CreateDossierDetails.MORE_DETAILS_FOR_DIFFERENT_NOMIS_NUMBER_TEXT).text().isEqualTo("A4321AA"),
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
        new AssessARecallPage().open(
                "assess.recall",
                withParameters(sessionVariableCalled(NOMS_NUMBER), theActorCalled(customer).recall("RECALL_ID"))
        );
        userIsOnPageWithTitle(customer, AssessARecallPage.TITLE);
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
        theActorCalled(customer).attemptsTo(
                Click.on(link)
        );
        Actor actor = theActorCalled(customer);
        String linkHref = link.resolveFor(actor).getAttribute("href");
        WebDriver driver = getDriver();
        String oldTab = driver.getWindowHandle();
        List<String> newTab = new ArrayList<>(driver.getWindowHandles());
        newTab.remove(oldTab);
        driver.switchTo().window(newTab.get(0));
        Ensure.thatTheCurrentPage().currentUrl().hasValue().isEqualTo(linkHref);
        driver.close();
        driver.switchTo().window(oldTab);
    }

    private void userIsOnPageWithTitle(String customer, String uniquePageTitle) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(uniquePageTitle)
        );
    }
}
