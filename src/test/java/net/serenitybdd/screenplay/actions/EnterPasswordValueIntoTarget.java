package net.serenitybdd.screenplay.actions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;

public class EnterPasswordValueIntoTarget extends EnterValueIntoTarget {
    private Target target;
    protected String theTextAsXs;

    public EnterPasswordValueIntoTarget(Target target, CharSequence... theText) {
        super(target, theText);
        this.target = target;

        theTextAsXs = "*".repeat(theTextAsAString.length());
    }

    @Step("{0} enters #theTextAsXs into #target")
    public <T extends Actor> void performAs(T theUser) {
        textValue().ifPresent(
                text -> target.resolveFor(theUser).type(text)
        );
        if (getFollowedByKeys().length > 0) {
            target.resolveFor(theUser).sendKeys(getFollowedByKeys());
        }
    }
}
