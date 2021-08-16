package uk.gov.justice.digital.managerecalls.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import uk.gov.justice.digital.managerecalls.forms.OffenderProfileForm;

public class OnOffenderProfile {

    public static Performable createARecall(){
        return Task.where("{0} attempts to start booking a recall",
                Click.on(OffenderProfileForm.CREATE_RECALL_BUTTON)
        );
    }
}
