package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CreateANewReasonsForRecallPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Create a new reasons for recall";

        public static final Target DETAILS = Target.the("Details box").located(By.id("details"));
}
