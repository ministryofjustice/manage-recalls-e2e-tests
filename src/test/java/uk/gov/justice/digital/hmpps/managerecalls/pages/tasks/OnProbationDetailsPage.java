package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import cucumber.pages.ProbationDetailsPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.ProbationDetailsForm;

public class OnProbationDetailsPage {

    public static Performable submitProbationDetails(){
        return Task.where("{0} submits the probation details",
                Enter.theValue("John Smith").into(ProbationDetailsForm.PROBATION_OFFICER_NAME_INPUT_FIELD),
                Enter.theValue("john.smith@digital.justice.gov.uk").into(ProbationDetailsForm.PROBATION_OFFICER_EMAIL_INPUT_FIELD),
                Enter.theValue("07775825221").into(ProbationDetailsForm.PROBATION_OFFICER_PHONE_NUMBER_INPUT_FIELD),
                SelectFromOptions.byValue("LONDON").from(ProbationDetailsPage.PROBATION_DIVISION_DROPDOWN),
                Enter.theValue("Jonny Thorn").into(ProbationDetailsForm.ASST_CHIEF_OFFICER_INPUT_FIELD),
                Click.on(ProbationDetailsForm.CONTINUE_BUTTON)
        );
    }
}