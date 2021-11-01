package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk")
@NamedUrls(
        {
                @NamedUrl(name = "assess.recall", url = "/persons/{1}/recalls/{2}/assess"),
                @NamedUrl(name = "view.recall", url = "/persons/{1}/recalls/{2}/view-recall"),
        }
)
public class AssessARecallPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Assess a recall";
    public static final Target RECALL_ASSESSMENT_DUE_TEXT = getTargetByDataQa("recallAssessmentDueText");

    public static class RecallAssessmentDetails {
        public static final Target ASSESSED_BY_USERNAME = getTargetByDataQa("assessedByUserName");
        public static final Target BOOKED_BY_USERNAME = getTargetByDataQa("bookedByUserName");
        public static final Target AGREE_WITH_RECALL_RECOMMENDATION = getTargetByDataQa("agreeWithRecall");
        public static final Target AGREE_WITH_RECALL_RECOMMENDATION_ADDITIONAL_TEXT = getTargetByDataQa("agreeWithRecallDetail");
        public static final Target LICENCE_CONDITIONS_BREACHED = getTargetByDataQa("licenceConditionsBreached");
        public static final Target REASON_FOR_RECALL_OPTION_ONE = getTargetByDataQa("reasonsForRecall-BREACH_EXCLUSION_ZONE");
        public static final Target REASON_FOR_RECALL_OPTION_OTHER = getTargetByDataQa("reasonsForRecall-OTHER");
        public static final Target OTHER_REASON_FOR_RECALL_TEXT = getTargetByDataQa("reasonsForRecallOtherDetail");
        public static final Target CURRENT_PRISON = getTargetByDataQa("currentPrison");
        public static final Target DATETIME_RECALL_NOTIFICATION_EMAIL_SENT = getTargetByDataQa("recallNotificationEmailSentDateTime");
        public static final Target UPLOADED_RECALL_NOTIFICATION_EMAIL_LINK = getTargetByDataQa("uploadedDocument-RECALL_NOTIFICATION_EMAIL");
    }

    public static class CreateDossierDetails {
        public static final Target DOSSIER_DUE_DATE = getTargetByDataQa("dossierTargetDate");
        public static final Target DOSSIER_CREATED_BY_USERNAME = getTargetByDataQa("dossierCreatedByUserName");
        public static final Target ADDITIONAL_LICENCE_CONDITIONS = getTargetByDataQa("additionalLicenceConditions");
        public static final Target DIFFERENT_NOMIS_NUMBER = getTargetByDataQa("differentNomsNumber");
        public static final Target DATE_DOSSIER_EMAIL_SENT = getTargetByDataQa("dossierEmailSentDate");
        public static final Target UPLOADED_DOSSIER_EMAIL_LINK = getTargetByDataQa("uploadedDocument-DOSSIER_EMAIL");
    }
}
