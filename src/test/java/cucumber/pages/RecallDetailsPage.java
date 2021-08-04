package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

public class RecallDetailsPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Assess a recall";
    public static final Target RECALL_LENGTH = getTargetByDataQa("recallLength");
    public static final Target RECALL_DOCUMENT_LINK_PART_A = getTargetByDataQa("uploadedDocument-PART_A_RECALL_REPORT");
    public static final Target RECALL_DOCUMENT_LINK_LICENCE = getTargetByDataQa("uploadedDocument-LICENCE");

}
