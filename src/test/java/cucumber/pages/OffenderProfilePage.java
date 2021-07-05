package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/offender-profile")
public class OffenderProfilePage extends PageObject {

    public static final String TITLE = "Manage Recalls - Offender profile";

    public static final Target DOWNLOAD_REVOCATION_ORDER_LINK = getTargetByDataQa("createRevocationOrderButton");
    public static final Target CREATE_RECALL_BUTTON = getTargetByDataQa("createRecallButton");
    public static final Target RECALL_ID_MATCHES = getTargetByDataQa("recallId");

    private static Target getTargetByDataQa(String dataQa) {
        return Target.the(dataQa + " matches").locatedBy("[data-qa='" + dataQa + "']");
    }

}
