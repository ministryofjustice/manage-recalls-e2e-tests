package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class DecisionOnRecallRecommendationPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Assess a recall - Decision on recall recommendation";
    public static final Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");
    public static final Target YES_RADIO = Target.the("radio button to proceed with recommended fixed recall").located(By.id("agreeWithRecall"));
    public static final Target QUESTION_AROUND_RECALL_LENGTH = getTargetByDataQa("recommended-recall-length-text");
    public static final Target MORE_DETAILS_FOR_YES_OPTION_TEXT_BOX = Target.the("text box to provide more details to proceed with recommended fixed recall").located(By.id("agreeWithRecallDetailYes"));

}
