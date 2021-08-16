package uk.gov.justice.digital.managerecalls.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import uk.gov.justice.digital.managerecalls.forms.RecallRecommendationDetailsFromProbationServiceForm;

public class OnRecallRecommendationReceivedPage {

    public static Performable submitTheDateAndTimeOfTheRecallRecommendationReceivedFromProbationService(){
        return Task.where("{0} submits the date and time of the recall recommendation received from probation service",
                Enter.theValue("05").into(RecallRecommendationDetailsFromProbationServiceForm.DAY_FIELD),
                Enter.theValue("12").into(RecallRecommendationDetailsFromProbationServiceForm.MONTH_FIELD),
                Enter.theValue("2020").into(RecallRecommendationDetailsFromProbationServiceForm.YEAR_FIELD),
                Enter.theValue("15").into(RecallRecommendationDetailsFromProbationServiceForm.HOUR_FIELD),
                Enter.theValue("33").into(RecallRecommendationDetailsFromProbationServiceForm.MINUTE_FIELD),
                Click.on(RecallRecommendationDetailsFromProbationServiceForm.CONTINUE_BUTTON)
        );
    }
}
