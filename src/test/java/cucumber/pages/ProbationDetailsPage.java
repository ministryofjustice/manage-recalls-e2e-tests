package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class ProbationDetailsPage extends PpudPage{
    public static final String TITLE = TITLE_ROOT + "Book a recall - Who recommended the recall?";

    public static final Target PROBATION_OFFICER_NAME_FIELD = Target.the("probation officer name field").located(By.id("probationOfficerName"));
    public static final Target PROBATION_OFFICER_PHONE_NO_FIELD = Target.the("probation officer phone number field").located(By.id("probationOfficerPhoneNumber"));
    public static final Target PROBATION_OFFICER_EMAIL_FIELD = Target.the("probation officer email field").located(By.id("probationOfficerEmail"));
    public static final Target LOCAL_DELIVERY_UNIT_AUTOCOMPLETE = Target.the("location delivery unit field").located(By.id("localDeliveryUnit"));
    public static final Target ASSISTANT_CHIEF_OFFICER_NAME_FIELD = Target.the("assistant chief officer name field").located(By.id("authorisingAssistantChiefOfficer"));

    public static Target getTargetForLocalDeliveryUnit(String unit){
        return Target.the("local delivery unit").located(By.xpath("//ul[@id=\"localDeliveryUnit__listbox\"]/li[contains(text(), \"" + unit +"\")]"));
    }
}
