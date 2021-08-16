package uk.gov.justice.digital.managerecalls.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import uk.gov.justice.digital.managerecalls.forms.LandingViewForm;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/")
public class ManageRecallsLandingPage extends PageObject {

    public static Performable goToFindAnOffenderView(){
        return Task.where("{0} goes to the find an offender view",
                Click.on(LandingViewForm.FIND_SOMEONE_LINK)
        );
    }
}
