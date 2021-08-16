package uk.gov.justice.digital.managerecalls.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.questions.TextContent;
import uk.gov.justice.digital.managerecalls.forms.LoginForm;
import uk.gov.justice.digital.managerecalls.forms.OffenderResultForm;

import java.util.List;

public class Login {

    public static Performable toTheManageRecallsService(String username, String password){
        return Task.where("{0} logs in to the manage recalls service",
                        Ensure.thatTheCurrentPage().title().hasValue().isEqualTo(LoginForm.TITLE),
                        Enter.theValue(username).into(LoginForm.USERNAME),
                        Enter.theValue(password).into(LoginForm.PASSWORD),
                        Click.on(LoginForm.SIGN_IN_BUTTON)
                );
    }

    public static Question<List<String>> offenderList() {
        return actor -> TextContent.of(OffenderResultForm.PERSON_MATCHES).viewedBy(actor).asList();
    }
}

