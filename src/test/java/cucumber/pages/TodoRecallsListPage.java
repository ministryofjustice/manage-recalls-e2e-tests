package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/")
public class TodoRecallsListPage extends PageObject {

    public static final String TITLE = "Manage a recall for an offender on license - To do";

    public static final Target FIND_SOMEONE_LINK = getTargetByDataQa("navLinkFindSomeone");

    private static Target getTargetByDataQa(String dataQa) {
        return Target.the(dataQa + " matches").locatedBy("[data-qa='" + dataQa + "']");
    }

}
