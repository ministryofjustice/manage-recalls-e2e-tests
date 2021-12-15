package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class ChangeHistoryPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Change history";
    public static final Target UPLOADED_PART_A_USER = getTargetBySelector("[data-qa='PART_A_RECALL_REPORT'] [data-qa='createdByUserName']");
    public static final Target UPLOADED_PART_A_HISTORY_LINK = getTargetByDataQa("viewHistory-PART_A_RECALL_REPORT");
    public static final Target GENERATED_DOSSIER_LINK = getTargetByDataQa("generatedDocument-DOSSIER");
}
