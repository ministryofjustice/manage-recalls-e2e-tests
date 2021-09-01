package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class LastReleaseDetailsPage extends PpudPage {

    public static final Target RELEASING_PRISON_AUTOCOMPLETE_FIELD = Target.the("releasing prison dropdown field").located(By.id("lastReleasePrison"));

    public static Target getTargetForReleasingPrison(String prison){
        return Target.the("releasing prison, Ashfield (HMP)").located(By.xpath("//ul[@id=\"lastReleasePrison__listbox\"]/li[contains(text(), \"" + prison +"\")]"));
    }

}
