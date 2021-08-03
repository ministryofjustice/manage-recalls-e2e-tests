package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

public class OffenderProfilePage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Offender profile";
    public static final Target CREATE_RECALL_BUTTON = getTargetByDataQa("createRecallButton");

}
