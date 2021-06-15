package cucumber.pages.managerecalls;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk")
public class StartPage extends PageObject {

    public static final String TITLE = "Manage Recalls - Home";
    public static final Target START_NOW = Target.the("start now button").locatedBy("a.govuk-button");

}
