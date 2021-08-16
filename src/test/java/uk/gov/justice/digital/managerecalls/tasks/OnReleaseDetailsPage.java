package uk.gov.justice.digital.managerecalls.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import uk.gov.justice.digital.managerecalls.forms.RecallRecommendationDetailsFromProbationServiceForm;

public class OnReleaseDetailsPage {

    public static Performable submitTheLatestReleaseDetails(){
        return Task.where("{0} submits the latest release details",
                Enter.theValue("05").into(RecallRecommendationDetailsFromProbationServiceForm.DAY_FIELD),
                Enter.theValue("12").into(RecallRecommendationDetailsFromProbationServiceForm.MONTH_FIELD),
                Enter.theValue("2020").into(RecallRecommendationDetailsFromProbationServiceForm.YEAR_FIELD),
                Enter.theValue("15").into(RecallRecommendationDetailsFromProbationServiceForm.HOUR_FIELD),
                Enter.theValue("33").into(RecallRecommendationDetailsFromProbationServiceForm.MINUTE_FIELD),
                Click.on(RecallRecommendationDetailsFromProbationServiceForm.CONTINUE_BUTTON)
        );
    }
}
