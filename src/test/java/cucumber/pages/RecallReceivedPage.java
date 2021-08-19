package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class RecallReceivedPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Book a recall - When did you receive the recall request?";
    public static final Target DAY_FIELD = Target.the("recall received day field").located(By.id("recallEmailReceivedDateTime-day"));
    public static final Target MONTH_FIELD = Target.the("recall received month field").located(By.id("recallEmailReceivedDateTime-month"));
    public static final Target YEAR_FIELD = Target.the("recall received year field").located(By.id("recallEmailReceivedDateTime-year"));
    public static final Target HOUR_FIELD = Target.the("recall received hour field").located(By.id("recallEmailReceivedDateTime-hour"));
    public static final Target MINUTE_FIELD = Target.the("recall received minute field").located(By.id("recallEmailReceivedDateTime-minute"));

}
