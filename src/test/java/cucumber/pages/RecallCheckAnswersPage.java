package cucumber.pages;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.util.EnvironmentVariables;

import static net.serenitybdd.core.Serenity.getDriver;

public class RecallCheckAnswersPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Book a recall - Check the details before booking this recall";

   public static final Target NAME = getTargetByDataQa("name");

    public static final Target NOMS_NUMBER_MATCHES = getTargetByDataQa("nomsNumber");
    public static final Target FIRST_NAME_MATCHES = getTargetByDataQa("firstName");
    public static final Target LAST_NAME_MATCHES = getTargetByDataQa("lastName");
    public static final Target DATE_OF_BIRTH_MATCHES = getTargetByDataQa("dateOfBirth");
       public static final Target PRE_CONS_NAME = getTargetByDataQa("previousConvictionMainName");
       public static final Target DATE_RECALL_EMAIL_RECEIVED = getTargetByDataQa("recallEmailReceivedDateTime");
       public static final Target LOCAL_POLICE_STATION = getTargetByDataQa("localPoliceForce");
       public static final Target VULNERABILITY_DIVERSITY_DETAIL = getTargetByDataQa("vulnerabilityDiversityDetail");
       public static final Target CONTRABAND_DETAIL = getTargetByDataQa("contrabandDetail");
       public static final Target MAPPA_LEVEL = getTargetByDataQa("mappaLevel");
       public static final Target PROBATION_OFFICER_NAME = getTargetByDataQa("probationOfficerName");
       public static final Target PROBATION_OFFICER_PHONE_NO = getTargetByDataQa("probationOfficerPhoneNumber");
       public static final Target PROBATION_OFFICER_EMAIL = getTargetByDataQa("probationOfficerEmail");
       public static final Target PROBATION_DIVISION = getTargetByDataQa("probationDivision");
       public static final Target ASSISTANT_CHIEF_OFFICER_NAME = getTargetByDataQa("authorisingAssistantChiefOfficer");
       public static final Target RECALL_DOCUMENT_LINK_PART_A = getTargetByDataQa("uploadedDocument-PART_A_RECALL_REPORT");
       public static final Target RECALL_DOCUMENT_LINK_LICENCE = getTargetByDataQa("uploadedDocument-LICENCE");
       public static final Target RECALL_DOCUMENT_LINK_PREVIOUS_CONVICTIONS_SHEET = getTargetByDataQa("uploadedDocument-PREVIOUS_CONVICTIONS_SHEET");
       public static final Target RECALL_DOCUMENT_LINK_PRE_SENTENCING_REPORT = getTargetByDataQa("uploadedDocument-PRE_SENTENCING_REPORT");

}
