package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/")
public class FindAnOffenderPage extends PageObject {

    public static final String TITLE = "Manage Recalls - Home";

    public static final Target NOMS_NUMBER_INPUT = Target.the("nomsNumber input").locatedBy("#search-field");
    public static final Target SEARCH_BUTTON = Target.the("search button").locatedBy("[data-qa='search'][type='submit']");

    public static final Target SEARCH_RESULT_TEXT = getTargetByDataQa("search-results-count");
    public static final Target PERSON_MATCHES = getTargetByDataQa("search-results");
    public static final Target NOMS_NUMBER_MATCHES = getTargetByDataQa("nomsNumber");
    public static final Target FIRST_NAME_MATCHES = getTargetByDataQa("firstName");
    public static final Target LAST_NAME_MATCHES = getTargetByDataQa("lastName");
    public static final Target DATE_OF_BIRTH_MATCHES = getTargetByDataQa("dateOfBirth");
    public static final Target REVOCATION_ORDER_LINK = getTargetByDataQa("generateRevocationOrderButton");

    private static Target getTargetByDataQa(String dataQa) {
        return Target.the(dataQa + " matches").locatedBy("[data-qa='" + dataQa + "']");
    }

}
