package uk.gov.justice.digital.managerecalls.forms;

import org.openqa.selenium.By;

public class OffenderProfileForm extends BaseForm{

    public static final String TITLE = TITLE_ROOT + "Offender profile";
    public static By CREATE_RECALL_BUTTON = By.xpath("[data-qa='createRecallButton']");
}
