package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class RecallNotificationDownloadPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Download the dossier and letter";
    public static final Target DOWNLOAD_RECALL_NOTIFICATION_LINK = getTargetByDataQa("getRecallNotificationLink");

}