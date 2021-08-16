package uk.gov.justice.digital.managerecalls.forms;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class OffenderResultForm extends BaseForm{

    public static final String TITLE = TITLE_ROOT + "Home";

    public static By SEARCH_BUTTON = By.xpath("[data-qa='search'][type='submit']");
    public static final Target SEARCH_RESULT_TEXT = getTargetByDataQa("search-results-count");
    public static final Target PERSON_MATCHES = getTargetByDataQa("search-results");
    public static final Target NOMS_NUMBER_MATCHES = getTargetByDataQa("nomsNumber");
    public static final Target FIRST_NAME_MATCHES = getTargetByDataQa("firstName");
    public static final Target LAST_NAME_MATCHES = getTargetByDataQa("lastName");
    public static final Target DATE_OF_BIRTH_MATCHES = getTargetByDataQa("dateOfBirth");
    public static final Target VIEW_PROFILE_LINK = getTargetByDataQa("viewProfileButton");

}
