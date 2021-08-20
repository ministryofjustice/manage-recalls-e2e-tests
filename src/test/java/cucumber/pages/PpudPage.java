package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;

public abstract class PpudPage extends PageObject {
    protected static final String TITLE_ROOT = "Manage a recall - ";

    public static Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");

    public static Target getTargetByDataQa(String dataQa) {
        return Target.the(dataQa + " matches").locatedBy("[data-qa='" + dataQa + "']");
    }

    protected static Target getTargetByInputValue(String value) {
        return Target.the(value + " matches").locatedBy("[value='" + value + "']");
    }

    public static Target getTargetByName(String name) {
        return Target.the(name + " matches").located(By.name(name));
    }

    public static Target dateYearInput(String fieldsetName) {
        return Target.the(fieldsetName + " matches").located(By.name(fieldsetName + "Year"));
    }

    public static Target dateMonthInput(String fieldsetName) {
        return Target.the(fieldsetName + " matches").located(By.name(fieldsetName + "Month"));
    }

    public static Target dateDayInput(String fieldsetName) {
        return Target.the(fieldsetName + " matches").located(By.name(fieldsetName + "Day"));
    }
}
