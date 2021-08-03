package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/todo")
public class TodoRecallsListPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "To do";

    public static final Target FIND_SOMEONE_LINK = getTargetByDataQa("navLinkFindSomeone");
    public static final Target RECALL_LIST_TODO_LINK = getTargetByDataQa("navLinkToDo");
    public static final Target FIRST_ASSESS_RECALL_DETAILS_LINK = getTargetByDataQa("viewRecallDetails");

}
