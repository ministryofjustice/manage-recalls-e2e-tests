package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class PoliceContactDetailsPage extends PpudPage {
    public static final String TITLE = TITLE_ROOT + "Book a recall - What is the name of the local police force?";
    public static final Target LOCAL_POLICE_FORCE = getTargetByName("localPoliceForce");
}
