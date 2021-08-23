package uk.gov.justice.digital.hmpps.managerecalls.pages.elements;

import org.openqa.selenium.By;

public class RecallDetailsForm extends BaseForm{

    public static final String TITLE = TITLE_ROOT + "Assess a recall";

    public static By RECALL_EMAIL_RECEIVED_DATE_TEXT = By.xpath("//*[@data-qa='recallEmailReceivedDateTime']");
    public static By LOCAL_POLICE_STATION_TEXT = By.xpath("//*[@data-qa='localPoliceService']");
    public static By VULNERABILITY_DIVERSITY_DETAIL_TEXT= By.xpath("//*[@data-qa='vulnerabilityDiversityDetail']");
    public static By CONTRABAND_DETAIL_TEXT = By.xpath("//*[@data-qa='contrabandDetail']");
    public static By MAPPA_LEVEL_TEXT = By.xpath("//*[@data-qa='mappaLevel']");
    public static By PROBATION_OFFICER_NAME_TEXT = By.xpath("//*[@data-qa='probationOfficerName']");
    public static By PROBATION_OFFICER_PHONE_NO_TEXT = By.xpath("//*[@data-qa='probationOfficerPhoneNumber']");
    public static By PROBATION_OFFICER_EMAIL_TEXT = By.xpath("//*[@data-qa='probationOfficerEmail']");
    public static By PROBATION_DIVISION_TEXT = By.xpath("//*[@data-qa='probationDivision']");
    public static By ASSISTANT_CHIEF_OFFICER_TEXT = By.xpath("//*[@data-qa='authorisingAssistantChiefOfficer']");
    public static By PART_A_RECALL_REPORT_FILE_DOWNLOAD_LINK = By.xpath("//*[@data-qa='uploadedDocument-PART_A_RECALL_REPORT']");
    public static By LICENCE_FILE_DOWNLOAD_LINK = By.xpath("//*[@data-qa='uploadedDocument-LICENCE']");

}
