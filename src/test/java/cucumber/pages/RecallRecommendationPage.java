package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/assess-recall")
public class RecallRecommendationPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Recall recommendation";
    public static final Target CONTINUE_BUTTON = getTargetByDataQa("addRecallTypeButton");
    public static final Target RECALL_LENGTH_14_DAYS = getTargetByInputValue("FOURTEEN_DAYS");

}
