package uk.gov.justice.digital.managerecalls.forms;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public abstract class BaseForm {

    protected static final String TITLE_ROOT = "Manage a recall for an offender on licence - ";

    public static By CONTINUE_BUTTON = By.xpath("[data-qa='viewProfileButton'][class='govuk-link']");

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
