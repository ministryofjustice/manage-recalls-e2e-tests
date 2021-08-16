package uk.gov.justice.digital.managerecalls.forms;

import net.serenitybdd.screenplay.targets.Target;

public class PrisonAndPoliceDetailsForm extends BaseForm{
    public static final String TITLE = TITLE_ROOT + "Manage a recall for an offender on licence - Book a recall - What are the police contact details?";
    public static final Target LOCAL_POLICE_SERVICE = getTargetByName("localPoliceService");
}

