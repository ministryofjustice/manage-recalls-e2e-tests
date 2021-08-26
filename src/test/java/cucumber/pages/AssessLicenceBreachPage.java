package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class AssessLicenceBreachPage extends PpudPage {

    public static final Target LICENCE_CONDITION_BREACHED_TEXT_BOX = Target.the("licence condition breached text box").located(By.id("licenceConditionsBreached"));
    public static final Target REASON_FOR_RECALL_CHECKBOX_OPTION_ONE = Target.the("reason for recall checkbox - option one").located(By.id("reasonsForRecall"));
    public static final Target REASON_FOR_RECALL_CHECKBOX_OPTION_OTHER = Target.the("reason for recall checkbox - option other").located(By.id("reasonsForRecall-17"));
    public static final Target OTHER_REASON_FOR_RECALL_TEXT_BOX = Target.the("other reason for recall text box").located(By.id("reasonsForRecallOtherDetail"));

}
