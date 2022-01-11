package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk")
@NamedUrls(
        {
                @NamedUrl(name = "open.fieldchangehistorydetails", url = "/persons/{1}/recalls/{2}/change-history/field?id={3}")
        }
)
public class ChangeHistoryDetailsForFieldsPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Field change history - Prison held in";
    public static final Target CURRENT_PRISON = getTargetBySelector("[data-qa='fieldChangeHistory'] [data-qa='value']");
    public static final Target DATE_TME_THE_CURRENT_PRISON_WAS_UPDATED = getTargetBySelector("[data-qa='fieldChangeHistory'] [data-qa='dateAndTime']");
    public static final Target USER_WHO_UPDATED_THE_CURRENT_PRISON = getTargetBySelector("[data-qa='fieldChangeHistory'] [data-qa='updatedByUserName']");
}