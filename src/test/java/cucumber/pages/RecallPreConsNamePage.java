package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class RecallPreConsNamePage extends PpudPage {
    public static final Target HAS_OTHER_PRE_CONS_NAME_OPTION = Target.the("pre-cons main name option - other").located(By.id("previousConvictionMainNameCategory-3"));
    public static final Target OTHER_PRE_CONS_NAME = Target.the("pre-cons main name text box").located(By.id("previousConvictionMainName"));

}
