package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.PrisonAndPoliceDetailsForm;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.RecallRequestForm;

public class OnPoliceDetailsPage {

    public static Performable submitPoliceDetails(){
        return Task.where("{0} submits the police details",
                Enter.theValue("Manchester police station").into(PrisonAndPoliceDetailsForm.LOCAL_POLICE_STATION_INPUT_FIELD),
                Click.on(RecallRequestForm.CONTINUE_BUTTON)
        );
    }
}
