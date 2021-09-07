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
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import static cucumber.pages.FindAnOffenderPage.BOOK_RECALL_LINK;
import static cucumber.pages.TodoRecallsListPage.FIND_SOMEONE_LINK;
import static cucumber.questions.ReadTextContent.textContent;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.serenitybdd.core.Serenity.*;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.awaitility.Awaitility.await;

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

    @Then("{word} downloads the recall notification")
    public void clickOnRecallNotificationLink(String customer) {
        theActorCalled(customer).attemptsTo(
            Click.on(RecallNotificationDownloadPage.DOWNLOAD_RECALL_NOTIFICATION_LINK),
            Click.on(RecallNotificationDownloadPage.CONTINUE_BUTTON)
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
                Enter.theValue("2021").into(LastReleaseDetailsPage.dateYearInput("licenceExpiryDate")),
                Enter.theValue("03").into(LastReleaseDetailsPage.dateDayInput("sentenceExpiryDate")),
                Enter.theValue("11").into(LastReleaseDetailsPage.dateMonthInput("sentenceExpiryDate")),
                Enter.theValue("2021").into(LastReleaseDetailsPage.dateYearInput("sentenceExpiryDate")),
                Enter.theValue("3").into(LastReleaseDetailsPage.getTargetByName("sentenceLengthYears")),
                Enter.theValue("2").into(LastReleaseDetailsPage.getTargetByName("sentenceLengthMonths")),
                Enter.theValue("Manchester Crown Court").into(LastReleaseDetailsPage.getTargetByName("sentencingCourt")),
                Enter.theValue("A123456").into(LastReleaseDetailsPage.getTargetByName("bookingNumber")),
                Enter.theValue("Burglary").into(LastReleaseDetailsPage.getTargetByName("indexOffence")),
                Enter.theValue("As").into(LastReleaseDetailsPage.RELEASING_PRISON_AUTOCOMPLETE_FIELD),
                Click.on(LastReleaseDetailsPage.getTargetForReleasingPrison("Ashfield (HMP)")),
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
        userEnters(customer, PoliceContactDetailsPage.LOCAL_POLICE_FORCE, "Essex");
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

    @When("{word} submits the probation officer details")
    public void submitProbationOfficerDetails(String customer) {
        setSessionVariable("probationOfficerName").to("John Smith");
        setSessionVariable("probationOfficerPhoneNumber").to("07775825221");
        setSessionVariable("probationOfficerEmail").to("john.smith@digital.justice.gov.uk");
        setSessionVariable("probationDivision").to("London");
        setSessionVariable("asstChiefOfficerName").to("Jonny Thorn");
        userEnters(customer, ProbationDetailsPage.PROBATION_OFFICER_NAME_FIELD, sessionVariableCalled("probationOfficerName"));
        userEnters(customer, ProbationDetailsPage.PROBATION_OFFICER_EMAIL_FIELD, sessionVariableCalled("probationOfficerEmail"));
        userEnters(customer, ProbationDetailsPage.PROBATION_OFFICER_PHONE_NO_FIELD, sessionVariableCalled("probationOfficerPhoneNumber"));
        theActorCalled(customer).attemptsTo(
                SelectFromOptions.byVisibleText(sessionVariableCalled("probationDivision")).from(ProbationDetailsPage.PROBATION_DIVISION_DROPDOWN)
        );
        userEnters(customer, ProbationDetailsPage.ASSISTANT_CHIEF_OFFICER_NAME_FIELD, sessionVariableCalled("asstChiefOfficerName"));
        userClicksOn(customer, VulnerabilityAndContrabandDetailsPage.CONTINUE_BUTTON);
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
        userClicksOn(customer, UploadRecallDocumentsPage.CONTINUE_BUTTON);
    }

    @When("{word} clicks on the Book a recall link")
    public void clickOnBookRecallLink(String customer) {
        userClicksOn(customer, BOOK_RECALL_LINK);
    }

    @When("{word} begins to assess the recall that they have just booked")
    public void assessRecall(String customer) {
        Actor actor = theActorCalled(customer);
        String recallId = actor.recall("RECALL_ID");

        theActorCalled(customer).attemptsTo(
                Click.on(TodoRecallsListPage.RECALL_LIST_TODO_LINK),
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(TodoRecallsListPage.TITLE),
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
                Click.on(TodoRecallsListPage.getTargetByDataQa("create-dossier-" + recallId))
        );
    }

    @Then("{word} is able to see the details captured during booking")
    public void confirmDetailsCapturedDuringBooking(String customer) {
        theActorCalled(customer).attemptsTo(

                // User details
                Ensure.that(FindAnOffenderPage.NOMS_NUMBER_MATCHES).text().isEqualTo(sessionVariableCalled(NOMS_NUMBER)),
                Ensure.that(FindAnOffenderPage.FIRST_NAME_MATCHES).text().isNotBlank(),
                Ensure.that(FindAnOffenderPage.LAST_NAME_MATCHES).text().isNotBlank(),
                Ensure.that(FindAnOffenderPage.DATE_OF_BIRTH_MATCHES).text().isNotBlank(),

                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(RecallDetailsPage.TITLE),
                // Recall
                Ensure.that(RecallDetailsPage.DATE_RECALL_EMAIL_RECEIVED).text().isEqualTo("5 December 2020 at 15:33"),
                Ensure.that(RecallDetailsPage.LOCAL_POLICE_STATION).text().isEqualTo("Essex"),
                // Issues and needs
                Ensure.that(RecallDetailsPage.VULNERABILITY_DIVERSITY_DETAIL).text().isEqualTo(sessionVariableCalled("vulnerabilityDiversityDetail")),
                Ensure.that(RecallDetailsPage.CONTRABAND_DETAIL).text().isEqualTo(sessionVariableCalled("contrabandDetail")),
                Ensure.that(RecallDetailsPage.MAPPA_LEVEL).text().isEqualTo("Level 1"),
                // Sentence, offence and release details
                Ensure.that(RecallDetailsPage.getTargetByDataQa("sentenceDate")).text().isEqualTo("3 Aug 2020"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("sentenceExpiryDate")).text().isEqualTo("3 Nov 2021"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("licenceExpiryDate")).text().isEqualTo("12 Oct 2021"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("conditionalReleaseDate")).text().isEqualTo("24 Jun 2022"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("lastReleasePrison")).text().isEqualTo("Ashfield (HMP)"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("lastReleaseDate")).text().isEqualTo("15 Mar 2021"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("sentenceLength")).text().isEqualTo("3 years 2 months"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("recallLength")).text().isEqualTo("28 days"),
                Ensure.that(RecallDetailsPage.getTargetByDataQa("bookingNumber")).text().isEqualTo("A123456"),
                // Probation details
                Ensure.that(RecallDetailsPage.PROBATION_OFFICER_NAME).text().isEqualTo(sessionVariableCalled("probationOfficerName")),
                Ensure.that(RecallDetailsPage.PROBATION_OFFICER_PHONE_NO).text().isEqualTo(sessionVariableCalled("probationOfficerPhoneNumber")),
                Ensure.that(RecallDetailsPage.PROBATION_OFFICER_EMAIL).text().isEqualTo(sessionVariableCalled("probationOfficerEmail")),
                Ensure.that(RecallDetailsPage.PROBATION_DIVISION).text().isEqualTo(sessionVariableCalled("probationDivision")),
                Ensure.that(RecallDetailsPage.ASSISTANT_CHIEF_OFFICER_NAME).text().isEqualTo(sessionVariableCalled("asstChiefOfficerName"))
        );
    }

    @When("{word} starts the assessment process for the recall")
    public void clickOnAssessThisRecallButton(String customer) {
        userClicksOn(customer, RecallDetailsPage.CONTINUE_BUTTON);
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

    @Then("{word} downloads the documents")
    public void downloadRecallDocument(String customer){
        userClicksOn(customer, RecallDetailsPage.RECALL_DOCUMENT_LINK_PART_A);
        //await().atMost(10, SECONDS).until(partAIsDownloaded());
        userClicksOn(customer, RecallDetailsPage.RECALL_DOCUMENT_LINK_LICENCE);
        //await().atMost(10, SECONDS).until(licenceIsDownloaded());
    }

    @Then("{word} navigates to view the details captured during assessment")
    public void navigateToViewRecallAssessmentDetails(String customer){
        Actor actor = theActorCalled(customer);
        new AssessRecallDetailsPage().getAssessRecallDetailsPage(environmentVariables, sessionVariableCalled(NOMS_NUMBER), actor.recall("RECALL_ID"));
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(AssessRecallDetailsPage.TITLE)
        );
    }

    @Then("{word} is able to see the details captured during assessment")
    public void confirmRecallDetailsCapturedDuringAssessment(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.that(AssessRecallDetailsPage.AGREE_WITH_RECALL_RECOMMENDATION).text().isEqualTo("Yes"),
                Ensure.that(AssessRecallDetailsPage.AGREE_WITH_RECALL_RECOMMENDATION_ADDITIONAL_TEXT).text().isEqualTo("yes, agree with the fixed term recall"),
                Ensure.that(AssessRecallDetailsPage.LICENCE_CONDITIONS_BREACHED).text().isEqualTo("Licence condition 1(a) has been breached"),
                Ensure.that(AssessRecallDetailsPage.REASON_FOR_RECALL_OPTION_ONE).text().isEqualTo("Breach of exclusion zone"),
                Ensure.that(AssessRecallDetailsPage.REASON_FOR_RECALL_OPTION_OTHER).text().isEqualTo("Other"),
                Ensure.that(AssessRecallDetailsPage.OTHER_REASON_FOR_RECALL_TEXT).text().isEqualTo("other reason for recall"),
                Ensure.that(AssessRecallDetailsPage.CURRENT_PRISON).text().isEqualTo("Ashfield (HMP)"),
                Ensure.that(AssessRecallDetailsPage.DATETIME_RECALL_NOTIFICATION_EMAIL_SENT).text().isEqualTo("5 December 2020 at 15:33"),
                Ensure.that(AssessRecallDetailsPage.UPLOADED_RECALL_NOTIFICATION_EMAIL_LINK).text().isEqualTo("email.msg")
        );
    }

    @Then("{word} can download the email")
    public void downloadEmail(String customer) {
        theActorCalled(customer).attemptsTo(
                Click.on(AssessRecallDetailsPage.UPLOADED_RECALL_NOTIFICATION_EMAIL_LINK)
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

    @Then("{word} can download the dossier")
    public void canDownloadTheDossier(String customer) {
        userIsOnPageWithTitle(customer, CreateDossierDownloadDossierAndLetterPage.TITLE);
        theActorCalled(customer).attemptsTo(
            Click.on(CreateDossierDownloadDossierAndLetterPage.DOWNLOAD_DOSSIER_LINK)
        );
    }

    @When("{word} has reviewed the dossier")
    public void hasReviewedTheDossier(String customer) {
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(CreateDossierDownloadDossierAndLetterPage.TITLE),
                Click.on(CreateDossierAddInfoForPrisonLetterPage.CONTINUE_BUTTON)
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
                Ensure.that(CreateDossierRecallDetailsPage.ADDITIONAL_LICENCE_CONDITIONS).text().isEqualTo("Yes"),
                Ensure.that(CreateDossierRecallDetailsPage.MORE_DETAILS_FOR_ADDITIONAL_LICENCE_CONDITIONS_TEXT).text().isEqualTo("Licence condition 14(a)"),
                Ensure.that(CreateDossierRecallDetailsPage.DIFFERENT_NOMIS_NUMBER).text().isEqualTo("Yes"),
                Ensure.that(CreateDossierRecallDetailsPage.MORE_DETAILS_FOR_DIFFERENT_NOMIS_NUMBER_TEXT).text().isEqualTo("A4321AA")
        );
    }

    @When("{word} navigates to view the details captured during dossier creation")
    public void viewDossierCreationDetails(String customer) {
        Actor actor = theActorCalled(customer);
        new CreateDossierRecallDetailsPage().getCreateDossierRecallDetailsPage(environmentVariables, sessionVariableCalled(NOMS_NUMBER), actor.recall("RECALL_ID"));
        theActorCalled(customer).attemptsTo(
                Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(CreateDossierRecallDetailsPage.TITLE)
        );
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

    private Callable<Boolean> recallNotificationEmailIsDownloaded() {
        return () -> fileIsDownloaded("/tmp", "email.msg");
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
