package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

public class BookRecallConfirmationPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Book a recall - Confirmation";
    public static final Target RECALL_ID = getTargetByDataQa("recallId");

}
