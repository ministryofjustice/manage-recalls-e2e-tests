package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.pages.PageObject;

public abstract class PpudPage extends PageObject {
    protected static final String TITLE_ROOT = "Manage a recall for an offender on licence - ";

    public static Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");

    protected static Target getTargetByDataQa(String dataQa) {
        return Target.the(dataQa + " matches").locatedBy("[data-qa='" + dataQa + "']");
    }

    protected static Target getTargetByInputValue(String value) {
        return Target.the(value + " matches").locatedBy("[value='" + value + "']");
    }

    protected static Target getTargetByName(String name) {
        return Target.the(name + " matches").locatedBy("[name='" + name + "']");
    }
}
