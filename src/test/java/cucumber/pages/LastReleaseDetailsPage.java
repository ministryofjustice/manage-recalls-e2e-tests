package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class LastReleaseDetailsPage extends PpudPage {

    public static final Target RELEASING_PRISON = Target.the("releasing prison autocomplete").located(By.id("lastReleasePrison"));
    public static final Target SENTENCING_COURT = Target.the("sentencing court autocomplete").located(By.id("sentencingCourt"));
    public static final Target INDEX_OFFENCE = Target.the("index offence autocomplete").located(By.id("indexOffence"));

    public static Target getTargetForReleasingPrison(String prison){
        return Target.the("releasing prison " + prison).located(By.xpath("//ul[@id=\"lastReleasePrison__listbox\"]/li[contains(text(), \"" + prison +"\")]"));
    }

    public static Target getTargetForSentencingCourt(String court){
        return Target.the("sentencing court: " + court).located(By.xpath("//ul[@id=\"sentencingCourt__listbox\"]/li[contains(text(), \"" + court +"\")]"));
    }

    public static Target getTargetForIndexOffence(String offence){
        return Target.the("index offence: " + offence).located(By.xpath("//ul[@id=\"indexOffence__listbox\"]/li[contains(text(), \"" + offence +"\")]"));
    }

}
