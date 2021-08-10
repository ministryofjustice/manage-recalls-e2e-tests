package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class RecallReceivedPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Book a recall";
    public static final Target DAY_FIELD = getTargetByName("day");
    public static final Target MONTH_FIELD = getTargetByName("month");
    public static final Target YEAR_FIELD = getTargetByName("year");
    public static final Target HOUR_FIELD = getTargetByName("hour");
    public static final Target MINUTE_FIELD = getTargetByName("minute");
    public static final Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");

}
