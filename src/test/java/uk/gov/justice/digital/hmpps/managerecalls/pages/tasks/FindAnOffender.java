package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.OffenderSearchForm;

public class FindAnOffender {

    public static Performable byNomisId(String nomisId){
        return Task.where("{0} attempts to search for #nomidId",
                Enter.theValue(nomisId).into(OffenderSearchForm.FIND_AN_OFFENDER_INPUT_FIELD),
                Click.on(OffenderSearchForm.SEARCH_BUTTON)
        );
    }
}
