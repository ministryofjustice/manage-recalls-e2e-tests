package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/find-person")
public class FindAnOffenderPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Find a person";

    public static final Target NOMS_NUMBER_INPUT = Target.the("nomsNumber input").locatedBy("[data-qa='search-field']");
    public static final Target SEARCH_BUTTON = Target.the("search button").locatedBy("[data-qa='search'][type='submit']");

    public static final Target SEARCH_RESULT_TEXT = getTargetByDataQa("search-results-count");
    public static final Target PERSON_MATCHES = getTargetByDataQa("search-results");
    public static final Target BOOK_RECALL_LINK = getTargetByDataQa("bookRecallButton");

}
