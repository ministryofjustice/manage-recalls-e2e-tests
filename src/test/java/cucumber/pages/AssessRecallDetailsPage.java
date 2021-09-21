package cucumber.pages;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.util.EnvironmentVariables;

public class AssessRecallDetailsPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Assess a recall";

    public static final Target ASSESSED_BY_USERNAME = getTargetByDataQa("assessedByUserName");
    public static final Target AGREE_WITH_RECALL_RECOMMENDATION = getTargetByDataQa("agreeWithRecall");
    public static final Target AGREE_WITH_RECALL_RECOMMENDATION_ADDITIONAL_TEXT = getTargetByDataQa("agreeWithRecallDetail");
    public static final Target LICENCE_CONDITIONS_BREACHED = getTargetByDataQa("licenceConditionsBreached");
    public static final Target REASON_FOR_RECALL_OPTION_ONE = getTargetByDataQa("reasonsForRecall-BREACH_EXCLUSION_ZONE");
    public static final Target REASON_FOR_RECALL_OPTION_OTHER = getTargetByDataQa("reasonsForRecall-OTHER");
    public static final Target OTHER_REASON_FOR_RECALL_TEXT = getTargetByDataQa("reasonsForRecallOtherDetail");
    public static final Target CURRENT_PRISON = getTargetByDataQa("currentPrison");
    public static final Target DATETIME_RECALL_NOTIFICATION_EMAIL_SENT = getTargetByDataQa("recallNotificationEmailSentDateTime");
    public static final Target UPLOADED_RECALL_NOTIFICATION_EMAIL_LINK = getTargetByDataQa("uploadedDocument-RECALL_NOTIFICATION_EMAIL");

    public void getAssessRecallDetailsPage(EnvironmentVariables environmentVariables, String nomsNumber, String recallId) {
        getDriver().get(EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("webdriver.base.url") + "/persons/" + nomsNumber + "/recalls/" + recallId + "/assess");
    }

}
