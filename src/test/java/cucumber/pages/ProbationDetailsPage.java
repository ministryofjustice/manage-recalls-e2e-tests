package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class ProbationDetailsPage extends PpudPage{
    public static final String TITLE = TITLE_ROOT + "Book a recall - Who recommended the recall?";

    public static final Target PROBATION_OFFICER_NAME_FIELD = Target.the("probation officer name field").located(By.id("probationOfficerName"));
    public static final Target PROBATION_OFFICER_PHONE_NO_FIELD = Target.the("probation officer phone number field").located(By.id("probationOfficerPhoneNumber"));
    public static final Target PROBATION_OFFICER_EMAIL_FIELD = Target.the("probation officer email field").located(By.id("probationOfficerEmail"));
    public static final Target PROBATION_DIVISION_DROPDOWN = Target.the("probation division field").located(By.id("probationDivision"));
    public static final Target ASSISTANT_CHIEF_OFFICER_NAME_FIELD = Target.the("assistant chief officer name field").located(By.id("authorisingAssistantChiefOfficer"));
}
