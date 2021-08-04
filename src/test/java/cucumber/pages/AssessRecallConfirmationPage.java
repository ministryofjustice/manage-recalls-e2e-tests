package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

public class AssessRecallConfirmationPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Recall is authorised";
    public static final Target RECALL_ID = getTargetByDataQa("recallId");
    public static final Target DOWNLOAD_REVOCATION_ORDER_LINK = getTargetByDataQa("getRevocationOrderButton");

}
