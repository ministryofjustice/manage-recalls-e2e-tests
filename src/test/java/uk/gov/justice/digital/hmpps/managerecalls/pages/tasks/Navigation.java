package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class Navigation {

    public static Performable toTheManageRecallsService(){
        return Task.where("{0} navigates to the manage recalls service",
        Open.browserOn().the(ManageRecallsLandingPage.class));
    }


}
