package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/user-details")
public class UserDetailsPage extends PpudPage {
    public static final Target FIRST_NAME_TEXT_BOX = Target.the("first name text box").located(By.id("firstName"));
    public static final Target LAST_NAME_TEXT_BOX = Target.the("last name text box").located(By.id("lastName"));
    public static final Target UPDATE_BUTTON = getTargetByDataQa("updateButton");
}
