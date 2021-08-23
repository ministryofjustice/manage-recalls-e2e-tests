package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.RecallRequestForm;

public class OnRecallRecommendationReceivedPage {

    public static Performable submitTheDateAndTimeOfTheRecallRecommendationReceivedFromProbationService(){
        return Task.where("{0} submits the date and time of the recall recommendation received from probation service",
                Enter.theValue("05").into(RecallRequestForm.EMAIL_RECEIVED_DAY_FIELD),
                Enter.theValue("12").into(RecallRequestForm.EMAIL_RECEIVED_MONTH_FIELD),
                Enter.theValue("2020").into(RecallRequestForm.EMAIL_RECEIVED_YEAR_FIELD),
                Enter.theValue("15").into(RecallRequestForm.EMAIL_RECEIVED_HOUR_FIELD),
                Enter.theValue("33").into(RecallRequestForm.EMAIL_RECEIVED_MINUTE_FIELD),
                Click.on(RecallRequestForm.CONTINUE_BUTTON)
        );
    }
}
