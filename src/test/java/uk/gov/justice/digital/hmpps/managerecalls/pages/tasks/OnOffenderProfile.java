package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.OffenderProfileForm;

public class OnOffenderProfile {

    public static Performable startBookingARecall(){
        return Task.where("{0} attempts to start booking a recall",
                Click.on(OffenderProfileForm.CREATE_A_RECALL_BUTTON)
        );
    }
}
