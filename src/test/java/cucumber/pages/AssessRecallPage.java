package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

public class AssessRecallPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Assess Recall";
    public static final Target DOWNLOAD_REVOCATION_ORDER_LINK = getTargetByDataQa("getRevocationOrderButton");
    public static final Target RECALL_LENGTH = getTargetByDataQa("recallLength");

}
