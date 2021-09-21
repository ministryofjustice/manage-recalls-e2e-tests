package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class RecallCheckAnswersPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Book a recall - Check the details before booking this recall";

    public class PersonDetails {
        public static final Target NAME = getTargetByDataQa("name");
        public static final Target DATE_OF_BIRTH = getTargetByDataQa("dateOfBirth");
        public static final Target GENDER = getTargetByDataQa("gender");
        public static final Target NOMS_NUMBER = getTargetByDataQa("nomsNumber");
        public static final Target CRO_NUMBER = getTargetByDataQa("croNumber");
        public static final Target PRE_CONS_NAME = getTargetByDataQa("previousConvictionMainName");
    }
    public class RecallDetails {
        public static final Target DATE_RECALL_EMAIL_RECEIVED = getTargetByDataQa("recallEmailReceivedDateTime");
    }
    public class SentenceOffenceAndReleaseDetails {
        public static final Target SENTENCE_TYPE = getTargetByDataQa("sentenceType");
        public static final Target SENTENCE_DATE = getTargetByDataQa("sentenceDate");
        public static final Target LICENCE_EXPIRY_DATE = getTargetByDataQa("licenceExpiryDate");
        public static final Target SENTENCE_EXPIRY_DATE = getTargetByDataQa("sentenceExpiryDate");
        public static final Target SENTENCE_LENGTH = getTargetByDataQa("sentenceLength");
        public static final Target SENTENCING_COURT = getTargetByDataQa("sentencingCourt");
        public static final Target INDEX_OFFENCE = getTargetByDataQa("indexOffence");
        public static final Target LAST_RELEASE_PRISON = getTargetByDataQa("lastReleasePrison");
        public static final Target BOOKING_NUMBER = getTargetByDataQa("bookingNumber");
        public static final Target LAST_RELEASE_DATE = getTargetByDataQa("lastReleaseDate");
        public static final Target CONDITIONAL_RELEASE_DATE = getTargetByDataQa("conditionalReleaseDate");
    }
    public class LocalPoliceForceDetails {
        public static final Target LOCAL_POLICE_FORCE = getTargetByDataQa("localPoliceForce");
    }
    public class IssuesAndNeedsDetails {
        public static final Target VULNERABILITY_DIVERSITY_DETAIL = getTargetByDataQa("vulnerabilityDiversityDetail");
        public static final Target CONTRABAND_DETAIL = getTargetByDataQa("contrabandDetail");
        public static final Target MAPPA_LEVEL = getTargetByDataQa("mappaLevel");
    }
    public class ProbationDetails {
        public static final Target PROBATION_OFFICER_NAME = getTargetByDataQa("probationOfficerName");
        public static final Target PROBATION_OFFICER_EMAIL = getTargetByDataQa("probationOfficerEmail");
        public static final Target PROBATION_OFFICER_PHONE_NO = getTargetByDataQa("probationOfficerPhoneNumber");
        public static final Target PROBATION_DIVISION = getTargetByDataQa("probationDivision");
        public static final Target ASSISTANT_CHIEF_OFFICER_NAME = getTargetByDataQa("authorisingAssistantChiefOfficer");
    }
    public class DocumentDetails {
        public static final Target RECALL_DOCUMENT_LINK_PART_A = getTargetByDataQa("uploadedDocument-PART_A_RECALL_REPORT");
        public static final Target RECALL_DOCUMENT_LINK_LICENCE = getTargetByDataQa("uploadedDocument-LICENCE");
        public static final Target RECALL_DOCUMENT_LINK_PREVIOUS_CONVICTIONS_SHEET = getTargetByDataQa("uploadedDocument-PREVIOUS_CONVICTIONS_SHEET");
        public static final Target RECALL_DOCUMENT_LINK_PRE_SENTENCING_REPORT = getTargetByDataQa("uploadedDocument-PRE_SENTENCING_REPORT");
    }
}
