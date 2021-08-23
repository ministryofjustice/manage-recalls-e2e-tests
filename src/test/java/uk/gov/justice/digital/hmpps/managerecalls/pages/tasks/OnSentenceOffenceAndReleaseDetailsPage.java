package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.SentenceOffenceAndReleaseDetailsForm;

public class OnSentenceOffenceAndReleaseDetailsPage {

    public static Performable submitTheSentenceOffenceAndLatestReleaseDetails(){
        return Task.where("{0} submits the sentence, offence and latest release details",
                Enter.theValue("05").into(SentenceOffenceAndReleaseDetailsForm.SENTENCE_DAY_INPUT_FIELD),
                Enter.theValue("12").into(SentenceOffenceAndReleaseDetailsForm.SENTENCE_MONTH_INPUT_FIELD),
                Enter.theValue("2020").into(SentenceOffenceAndReleaseDetailsForm.SENTENCE_YEAR_INPUT_FIELD),

                Enter.theValue("05").into(SentenceOffenceAndReleaseDetailsForm.LICENCE_EXPIRY_DAY_INPUT_FIELD),
                Enter.theValue("12").into(SentenceOffenceAndReleaseDetailsForm.LICENCE_EXPIRY_MONTH_INPUT_FIELD),
                Enter.theValue("2020").into(SentenceOffenceAndReleaseDetailsForm.LICENCE_EXPIRY_YEAR_INPUT_FIELD),

                Enter.theValue("05").into(SentenceOffenceAndReleaseDetailsForm.SENTENCE_EXPIRY_DAY_INPUT_FIELD),
                Enter.theValue("12").into(SentenceOffenceAndReleaseDetailsForm.SENTENCE_EXPIRY_MONTH_INPUT_FIELD),
                Enter.theValue("2020").into(SentenceOffenceAndReleaseDetailsForm.SENTENCE_EXPIRY_YEAR_INPUT_FIELD),

                Enter.theValue("05").into(SentenceOffenceAndReleaseDetailsForm.SENTENCE_LENGTH_YEAR_INPUT_FIELD),
                Enter.theValue("12").into(SentenceOffenceAndReleaseDetailsForm.SENTENCE_LENGTH_MONTH_INPUT_FIELD),
                Enter.theValue("2020").into(SentenceOffenceAndReleaseDetailsForm.SENTENCE_LENGTH_DAY_INPUT_FIELD),

                Enter.theValue("Manchester Crown Court").into(SentenceOffenceAndReleaseDetailsForm.SENTENCING_COURT_INPUT_FIELD),
                Enter.theValue("Burglary").into(SentenceOffenceAndReleaseDetailsForm.INDEX_OFFENCE_INPUT_FIELD),
                Enter.theValue("Belmarsh").into(SentenceOffenceAndReleaseDetailsForm.RELEASING_PRISON_INPUT_FIELD),
                Enter.theValue("A1234").into(SentenceOffenceAndReleaseDetailsForm.BOOKING_NUMBER_INPUT_FIELD),

                Enter.theValue("05").into(SentenceOffenceAndReleaseDetailsForm.LATEST_RELEASE_DAY_INPUT_FIELD),
                Enter.theValue("12").into(SentenceOffenceAndReleaseDetailsForm.LATEST_RELEASE_MONTH_INPUT_FIELD),
                Enter.theValue("2020").into(SentenceOffenceAndReleaseDetailsForm.LATEST_RELEASE_YEAR_INPUT_FIELD),

                Enter.theValue("05").into(SentenceOffenceAndReleaseDetailsForm.CONDITIONAL_RELEASE_DAY_INPUT_FIELD),
                Enter.theValue("12").into(SentenceOffenceAndReleaseDetailsForm.CONDITIONAL_RELEASE_MONTH_INPUT_FIELD),
                Enter.theValue("2020").into(SentenceOffenceAndReleaseDetailsForm.CONDITIONAL_RELEASE_YEAR_INPUT_FIELD),

                Click.on(SentenceOffenceAndReleaseDetailsForm.CONTINUE_BUTTON)
        );
    }
}
