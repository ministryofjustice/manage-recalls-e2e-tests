package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

public class AssessRecallRecommendationPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Decision on recall recommendation";
    public static final Target RECALL_DECISION = getTargetByInputValue("YES");

}
