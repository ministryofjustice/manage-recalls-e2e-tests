package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class RecallLicenceNamePage extends PpudPage {
    public static final Target LICENCE_NAME_FIRST_LAST = Target.the("licence name radio button - first + last").located(By.id("licenceNameCategory"));

}
