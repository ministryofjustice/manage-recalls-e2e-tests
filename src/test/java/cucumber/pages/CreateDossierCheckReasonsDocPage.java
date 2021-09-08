package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class CreateDossierCheckReasonsDocPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Check and create the reasons for recall document";
    public static final Target NAME = getTargetByDataQa("name");
    public static final Target NOMS_NUMBER = getTargetByDataQa("nomsNumber");
    public static final Target BOOKING_NUMBER = getTargetByDataQa("bookingNumber");
    public static final Target LICENCE_CONDITIONS_BREACHED = getTargetByDataQa("licenceConditionsBreached");
    public static final Target RECALL_TYPE = getTargetByDataQa("recallType");
    public static final Target RECALL_LENGTH = getTargetByDataQa("recallLength");
}