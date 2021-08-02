package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/offender-profile")
public class OffenderProfilePage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Offender profile";
    public static final Target CREATE_RECALL_BUTTON = getTargetByDataQa("createRecallButton");

}
