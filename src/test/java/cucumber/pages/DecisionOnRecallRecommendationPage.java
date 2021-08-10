package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class DecisionOnRecallRecommendationPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Decision on recall recommendation";
    public static final Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");
    public static final Target RECALL_LENGTH_14_DAYS = getTargetByInputValue("YES");

}
