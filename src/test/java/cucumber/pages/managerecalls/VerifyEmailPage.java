package cucumber.pages.managerecalls;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/auth/verify-email")
public class VerifyEmailPage extends PageObject {

    public static final String TITLE = "HMPPS Digital Services - Verify Email";
    public static final Target SKIP_FOR_NOW = Target.the("skip for now button").locatedBy("#cancel");

}
