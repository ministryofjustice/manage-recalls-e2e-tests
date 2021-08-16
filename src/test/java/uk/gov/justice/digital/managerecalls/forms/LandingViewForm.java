package uk.gov.justice.digital.managerecalls.forms;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LandingViewForm extends BaseForm{

    public static final String TITLE = TITLE_ROOT + "To do";

    public static final Target FIND_SOMEONE_LINK = getTargetByDataQa("navLinkFindSomeone");
    public static final Target RECALL_LIST_TODO_LINK = getTargetByDataQa("navLinkToDo");
    public static final Target RECALLS_TABLE = Target.the("Recalls Table").located(By.className("govuk-table"));
}
