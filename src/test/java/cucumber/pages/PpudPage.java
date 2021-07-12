package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.pages.PageObject;

public abstract class PpudPage extends PageObject {

    protected static final String TITLE_ROOT = "Manage a recall for an offender on license - ";

    protected static Target getTargetByDataQa(String dataQa) {
        return Target.the(dataQa + " matches").locatedBy("[data-qa='" + dataQa + "']");
    }

}
