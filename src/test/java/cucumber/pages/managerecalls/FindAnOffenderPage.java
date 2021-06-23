package cucumber.pages.managerecalls;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/")
public class FindAnOffenderPage extends PageObject {

    public static final String TITLE = "Manage Recalls - Home";
    public static final Target NOMS_NUMBER_INPUT = Target.the("nomsNumber input").locatedBy("#search-field");
    public static final Target SEARCH_BUTTON = Target.the("search button").locatedBy("[data-qa='search'][type='submit']");
    public static final Target SEARCH_RESULT_TEXT = Target.the("search result text").locatedBy("[data-qa='search-results-count']");
    public static final Target PERSON_MATCHES = Target.the("person matches").locatedBy("[data-qa='search-results']");
    public static final Target NOMS_NUMBER_MATCHES = Target.the("nomsNumber matches").locatedBy("[data-qa='nomsNumber']");

}
