package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.ensure.Ensure;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.LoginForm;

public class Login {

    public static Performable toTheManageRecallsService(String username, String password){
        return Task.where("{0} logs in to the manage recalls service",
                        Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(LoginForm.TITLE),
                        Enter.theValue(username).into(LoginForm.USERNAME_INPUT_FIELD),
                        Enter.theValue(password).into(LoginForm.PASSWORD_INPUT_FIELD),
                        Click.on(LoginForm.SIGN_IN_BUTTON)
                );
    }

//    public static Question<List<String>> offenderList() {
//        return actor -> TextContent.of(OffenderResultForm.PERSON_MATCHES).viewedBy(actor).asList();
//    }
}

