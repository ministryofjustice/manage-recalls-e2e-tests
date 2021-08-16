package uk.gov.justice.digital.managerecalls.forms;

import net.serenitybdd.screenplay.targets.Target;

public class LoginForm {

    public static final String TITLE = "HMPPS Digital Services - Sign in";

    public static final Target USERNAME = Target.the("username field").locatedBy("#username");
    public static final Target PASSWORD = Target.the("password field").locatedBy("#password");
    public static final Target SIGN_IN_BUTTON = Target.the("sign in button").locatedBy("#submit");
}
