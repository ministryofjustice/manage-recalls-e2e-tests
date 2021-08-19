package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class RecallDetailsPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Assess a recall";
    public static final Target DATE_RECALL_EMAIL_RECEIVED = getTargetByDataQa("recallEmailReceivedDateTime");
    public static final Target LOCAL_POLICE_STATION = getTargetByDataQa("localPoliceService");
    public static final Target VULNERABILITY_DIVERSITY_DETAIL = getTargetByDataQa("vulnerabilityDiversityDetail");
    public static final Target CONTRABAND_DETAIL = getTargetByDataQa("contrabandDetail");
    public static final Target MAPPA_LEVEL = getTargetByDataQa("mappaLevel");
    public static final Target RECALL_DOCUMENT_LINK_PART_A = getTargetByDataQa("uploadedDocument-PART_A_RECALL_REPORT");
    public static final Target RECALL_DOCUMENT_LINK_LICENCE = getTargetByDataQa("uploadedDocument-LICENCE");
}
