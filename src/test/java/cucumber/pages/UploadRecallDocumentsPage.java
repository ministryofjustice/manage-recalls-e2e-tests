package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/assess-recall")
public class UploadRecallDocumentsPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Upload documents";
    public static final Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");

}
