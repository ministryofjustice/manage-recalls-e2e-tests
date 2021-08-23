package uk.gov.justice.digital.hmpps.managerecalls.pages.elements;

import org.openqa.selenium.By;

public class ProbationDetailsForm extends BaseForm{

    public static By PROBATION_OFFICER_NAME_INPUT_FIELD = By.id("probationOfficerName");
    public static By PROBATION_OFFICER_EMAIL_INPUT_FIELD = By.id("probationOfficerEmail");
    public static By PROBATION_OFFICER_PHONE_NUMBER_INPUT_FIELD = By.id("probationOfficerPhoneNumber");
    public static By PROBATION_DIVISION_DROPDOWN_FIELD = By.id("probationDivision");
    public static By ASST_CHIEF_OFFICER_INPUT_FIELD = By.id("authorisingAssistantChiefOfficer");
}
