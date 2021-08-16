package uk.gov.justice.digital.managerecalls.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.TextContent;
import uk.gov.justice.digital.managerecalls.forms.OffenderResultForm;

import java.util.List;

public class OffenderSearchResult {

    public static Question<List<String>> offenderList() {
        return actor -> TextContent.of(OffenderResultForm.PERSON_MATCHES).viewedBy(actor).asList();
    }

    public static Performable viewTheOffenderDetails(){
        return Task.where("{0} attempts to view the profile returned in the search result",
                Click.on(OffenderResultForm.VIEW_PROFILE_LINK)
        );
    }
}
