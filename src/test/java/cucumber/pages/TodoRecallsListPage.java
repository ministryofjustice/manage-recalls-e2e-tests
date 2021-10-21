package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/todo")
public class TodoRecallsListPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "To do";

    public static final Target FIND_SOMEONE_LINK = getTargetByDataQa("navLinkFindSomeone");
    public static final Target RECALL_LIST_TODO_LINK = getTargetByDataQa("navLinkToDo");
    public static final Target RECALL_LIST_DUE_DATE = getTargetByDataQa("dueDate");
    public static final Target RECALLS_TABLE = Target.the("Recalls Table").located(By.className("govuk-table"));



}
