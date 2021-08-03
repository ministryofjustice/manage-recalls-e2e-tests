package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

public class BookRecallPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Book a recall";
    public static final Target RECALL_CONFIRMATION_MATCHES = getTargetByDataQa("recallId");
    public static final Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");

}
