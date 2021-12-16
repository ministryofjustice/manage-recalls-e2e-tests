package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CreateANewRecallNotificationPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Create a new recall notification";

        public static final Target DETAILS = Target.the("Details box").located(By.id("details"));
}
