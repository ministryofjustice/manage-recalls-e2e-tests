package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.OffenderResultForm;

public class OffenderSearchResult {

//    public static Question<List<String>> offenderList() {
//        return actor -> TextContent.of(OffenderResultForm.PERSON_MATCHES).viewedBy(actor).asList();
//    }

    public static Performable navigateToOffenderProfileDetails(){
        return Task.where("{0} attempts to view the profile returned in the search result",
                Click.on(OffenderResultForm.VIEW_PROFILE_LINK)
        );
    }
}
