package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/todo")
public class TodoRecallsListPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Recalls";

    public static final Target FIND_SOMEONE_LINK = getTargetByDataQa("navLinkFindSomeone");
    public static final Target COMPLETED_TAB = Target.the("Completed tab matches").locatedBy("#tab_completed");

    public static Target assignedToForRecallId(String recallId) {
        return getTargetBySelector("[data-qa='recall-id-" + recallId + "'] [data-qa='assignedTo']");
    }

    public static Target dueDateForRecallId(String recallId) {
        return getTargetBySelector("[data-qa='recall-id-" + recallId + "'] [data-qa='dueDate']");
    }

    public static Target viewLinkForRecallId(String recallId) {
        return getTargetByDataQa("view-recall-" + recallId);
    }

    public static Target continueBookingLinkForRecallId(String recallId) {
        return getTargetByDataQa("continue-booking-" + recallId);
    }

    public static Target assessRecallLinkForRecallId(String recallId) {
        return getTargetByDataQa("assess-recall-" + recallId);
    }
}
