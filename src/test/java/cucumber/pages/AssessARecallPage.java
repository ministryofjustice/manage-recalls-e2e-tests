package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk")
@NamedUrls(
        {
                @NamedUrl(name = "assess.recall", url = "/persons/{1}/recalls/{2}/assess")
        }
)
public class AssessARecallPage extends PpudPage {
    public static final String TITLE = TITLE_ROOT + "Assess a recall";

    public class PersonDetails {
        public static final Target NAME = getTargetByDataQa("name");
        public static final Target PRE_CONS_NAME = getTargetByDataQa("previousConvictionMainName");
        public static final Target DATE_OF_BIRTH = getTargetByDataQa("dateOfBirth");
        public static final Target GENDER = getTargetByDataQa("gender");
        public static final Target CRO_NUMBER = getTargetByDataQa("croNumber");
        public static final Target PNC_NUMBER = getTargetByDataQa("pncNumber");
        public static final Target NOMS_NUMBER = getTargetByDataQa("nomsNumber");
    }
    public class RecallDetails {
        public static final Target RECALL_LENGTH = getTargetByDataQa("recallLength");
        public static final Target DATE_RECALL_EMAIL_RECEIVED = getTargetByDataQa("recallEmailReceivedDateTime");
        public static final Target LOCAL_POLICE_STATION = getTargetByDataQa("localPoliceForce");
    }
    public class IssuesAndNeedsDetails {
        public static final Target VULNERABILITY_DIVERSITY_DETAIL = getTargetByDataQa("vulnerabilityDiversityDetail");
        public static final Target CONTRABAND_DETAIL = getTargetByDataQa("contrabandDetail");
        public static final Target MAPPA_LEVEL = getTargetByDataQa("mappaLevel");
    }
    public class SentenceOffenceAndReleaseDetails {
        public static final Target SENTENCE_DATE = getTargetByDataQa("sentenceDate");
        public static final Target SENTENCE_EXPIRY_DATE = getTargetByDataQa("sentenceExpiryDate");
        public static final Target LICENCE_EXPIRY_DATE = getTargetByDataQa("licenceExpiryDate");
        public static final Target CONDITIONAL_RELEASE_DATE = getTargetByDataQa("conditionalReleaseDate");
        public static final Target LAST_RELEASE_PRISON = getTargetByDataQa("lastReleasePrison");
        public static final Target LAST_RELEASE_DATE = getTargetByDataQa("lastReleaseDate");
        public static final Target SENTENCE_LENGTH = getTargetByDataQa("sentenceLength");
        public static final Target INDEX_OFFENCE = getTargetByDataQa("indexOffence");
        public static final Target BOOKING_NUMBER = getTargetByDataQa("bookingNumber");
    }
    public class ProbationDetails {
        public static final Target PROBATION_OFFICER_NAME = getTargetByDataQa("probationOfficerName");
        public static final Target PROBATION_OFFICER_PHONE_NO = getTargetByDataQa("probationOfficerPhoneNumber");
        public static final Target PROBATION_OFFICER_EMAIL = getTargetByDataQa("probationOfficerEmail");
        public static final Target PROBATION_DIVISION = getTargetByDataQa("probationDivision");
        public static final Target ASSISTANT_CHIEF_OFFICER_NAME = getTargetByDataQa("authorisingAssistantChiefOfficer");
    }
    public class DocumentDetails {
        public static final Target RECALL_DOCUMENT_LINK_PART_A = getTargetByDataQa("uploadedDocument-PART_A_RECALL_REPORT");
        public static final Target RECALL_DOCUMENT_LINK_LICENCE = getTargetByDataQa("uploadedDocument-LICENCE");
        public static final Target RECALL_DOCUMENT_LINK_PREVIOUS_CONVICTIONS_SHEET = getTargetByDataQa("uploadedDocument-PREVIOUS_CONVICTIONS_SHEET");
        public static final Target RECALL_DOCUMENT_LINK_PRE_SENTENCING_REPORT = getTargetByDataQa("uploadedDocument-PRE_SENTENCING_REPORT");
    }
    public class RecallAssessmentDetails {
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
    }
    public class CreateDossierDetails {
        public static final Target ADDITIONAL_LICENCE_CONDITIONS = getTargetByDataQa("additionalLicenceConditions");
        public static final Target MORE_DETAILS_FOR_ADDITIONAL_LICENCE_CONDITIONS_TEXT = getTargetByDataQa("additionalLicenceConditionsDetail");
        public static final Target DIFFERENT_NOMIS_NUMBER = getTargetByDataQa("differentNomsNumber");
        public static final Target MORE_DETAILS_FOR_DIFFERENT_NOMIS_NUMBER_TEXT = getTargetByDataQa("differentNomsNumberDetail");
        public static final Target DATE_DOSSIER_EMAIL_SENT = getTargetByDataQa("dossierEmailSentDate");
        public static final Target UPLOADED_DOSSIER_EMAIL_LINK = getTargetByDataQa("uploadedDocument-DOSSIER_EMAIL");
    }
}
