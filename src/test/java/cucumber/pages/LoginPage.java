package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/")
public class LoginPage extends PageObject {

    public static final String TITLE = "HMPPS Digital Services - Sign in";

    public static final Target USERNAME = Target.the("username field").locatedBy("#username");
    public static final Target PASSWORD = Target.the("password field").locatedBy("#password");
    public static final Target SIGN_IN_BUTTON = Target.the("sign in button").locatedBy("#submit");
}
