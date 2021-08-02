package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/assess-recall")
public class BookRecallPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Book a recall";
    public static final Target RECALL_CONFIRMATION_MATCHES = getTargetByDataQa("recallId");
    public static final Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");

}
