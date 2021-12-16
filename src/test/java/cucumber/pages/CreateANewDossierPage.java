package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class CreateANewDossierPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Create a new dossier";

        public static final Target DETAILS = Target.the("Details box").located(By.id("details"));
}
