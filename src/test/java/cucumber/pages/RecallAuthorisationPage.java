package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class RecallAuthorisationPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Recall is authorised";
    public static final Target DOWNLOAD_REVOCATION_ORDER_LINK = getTargetByDataQa("getRevocationOrderButton");
    public static final Target RECALL_ID = getTargetByDataQa("recallId");
}