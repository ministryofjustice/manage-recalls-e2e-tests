package uk.gov.justice.digital.managerecalls.forms;

import org.openqa.selenium.By;

public class OffenderSearchForm extends BaseForm{

    public static final String TITLE = TITLE_ROOT + "Home";

    public static By FIND_AN_OFFENDER_INPUT_FIELD = By.id("search-field");
    public static By SEARCH_BUTTON = By.xpath("[data-qa='search'][type='submit']");
}
