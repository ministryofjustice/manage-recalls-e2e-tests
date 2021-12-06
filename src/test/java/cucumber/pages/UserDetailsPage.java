package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/user-details")
public class UserDetailsPage extends PpudPage {
    public static final Target FIRST_NAME = Target.the("first name text box").located(By.id("firstName"));
    public static final Target LAST_NAME = Target.the("last name text box").located(By.id("lastName"));
    public static final Target EMAIL_ADDRESS = Target.the("email address").located(By.id("email"));
    public static final Target PHONE_NUMBER = Target.the("phone number").located(By.id("phoneNumber"));
    public static final Target BAND_3_OPTION = Target.the("band 3 radio button").located(By.id("caseworkerBand"));
    public static final Target BAND_4_OPTION = Target.the("band 4+ radio button").located(By.id("caseworkerBand-2"));
    public static final Target SIGNATURE = Target.the("signature").located(By.id("signature"));
    public static final Target UPDATE_BUTTON = getTargetByDataQa("saveButton");
}
