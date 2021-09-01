package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class AssessCurrentPrisonPage extends PpudPage {

    public static final Target CURRENT_PRISON_AUTOCOMPLETE_FIELD = Target.the("current prison dropdown field").located(By.id("currentPrison"));

    public static Target getTargetForCurrentPrison(String prison){
        return Target.the("current prison, Ashfield (HMP)").located(By.xpath("//ul[@id=\"currentPrison__listbox\"]/li[contains(text(), \"" + prison +"\")]"));
    }

}
