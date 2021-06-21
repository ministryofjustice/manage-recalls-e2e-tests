package cucumber.questions;

import cucumber.pages.managerecalls.LoginPage;
import cucumber.pages.managerecalls.VerifyEmailPage;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.openqa.selenium.WebDriver;

public record UserIsOn(String expectedTitle) implements Question<Boolean> {

    public static UserIsOn loginPage() {
        return new UserIsOn(LoginPage.TITLE);
    }

    public static UserIsOn verifyEmailPage() {
        return new UserIsOn(VerifyEmailPage.TITLE);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        WebDriver driver = Serenity.getDriver();
        return driver.getTitle().equals(expectedTitle);
    }
}
