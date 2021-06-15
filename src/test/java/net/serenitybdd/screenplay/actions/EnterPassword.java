package net.serenitybdd.screenplay.actions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import java.util.Arrays;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class EnterPassword {

    private final CharSequence[] theText;

    public EnterPassword(CharSequence... theText) {
        this.theText = theText;
    }

    public static EnterPassword theValue(CharSequence... text) {
        return new EnterPassword(text);
    }

    public static SendKeys keyValues(CharSequence... text) {
        return new SendKeys(text);
    }

    public EnterValue into(String cssOrXpathForElement) {
        return instrumented(EnterPasswordValueIntoTarget.class, Target.the(cssOrXpathForElement).locatedBy(cssOrXpathForElement), theText);
    }

    public EnterValue into(Target target) {
        return instrumented(EnterPasswordValueIntoTarget.class, target, theText);
    }
}
