package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;
import org.openqa.selenium.By;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk")
@NamedUrls(
        {
                @NamedUrl(name = "open.changehistoryoverview", url = "/persons/{1}/recalls/{2}/change-history")
        }
)
public class ChangeHistoryPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Change history";
    public static final Target UPLOADED_PART_A_USER = getTargetBySelector("[data-qa='PART_A_RECALL_REPORT'] [data-qa='createdByUserName']");
    public static final Target INFORMATION_TAB = Target.the("Information tab").located(By.id("tab_information-entered"));
    public static final Target VIEW_HISTORY_LINK_FOR_CURRENT_PRISON = getTargetByDataQa("viewHistory-currentPrison");
    public static final Target DOCUMENTS_TAB = Target.the("Documents tab").located(By.id("tab_documents"));
    public static final Target UPLOADED_PRE_CONS_HISTORY_LINK = getTargetByDataQa("viewHistory-PREVIOUS_CONVICTIONS_SHEET");
    public static final Target GENERATED_DOSSIER_LINK = getTargetByDataQa("generatedDocument-DOSSIER");
}
