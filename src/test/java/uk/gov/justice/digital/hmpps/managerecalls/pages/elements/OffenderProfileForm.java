package uk.gov.justice.digital.hmpps.managerecalls.pages.elements;

import org.openqa.selenium.By;

public class OffenderProfileForm extends BaseForm{

    public static final String TITLE = TITLE_ROOT + "Offender profile";
    public static By CREATE_A_RECALL_BUTTON = By.xpath("//*[@data-qa='createRecallButton']");
}
