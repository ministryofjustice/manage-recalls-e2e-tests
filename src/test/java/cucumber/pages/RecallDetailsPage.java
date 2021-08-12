package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class RecallDetailsPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Assess a recall";
    public static final Target DATE_RECALL_EMAIL_RECEIVED = getTargetByDataQa("recallEmailReceivedDateTime");
    public static final Target RELEASING_PRISON = getTargetByDataQa("lastReleasePrison");
    public static final Target LAST_RELEASE_DATE = getTargetByDataQa("lastReleaseDateTime");
    public static final Target LOCAL_POLICE_STATION = getTargetByDataQa("localPoliceService");
    public static final Target RECALL_DOCUMENT_LINK_PART_A = getTargetByDataQa("uploadedDocument-PART_A_RECALL_REPORT");
    public static final Target RECALL_DOCUMENT_LINK_LICENCE = getTargetByDataQa("uploadedDocument-LICENCE");
}
