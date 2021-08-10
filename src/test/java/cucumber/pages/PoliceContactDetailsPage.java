package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class PoliceContactDetailsPage extends PpudPage {
    public static final String TITLE = TITLE_ROOT + "Manage a recall for an offender on licence - Book a recall - What are the police contact details?";
    public static final Target LOCAL_POLICE_STATION = getTargetByName("localPoliceService");
    public static final Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");
}