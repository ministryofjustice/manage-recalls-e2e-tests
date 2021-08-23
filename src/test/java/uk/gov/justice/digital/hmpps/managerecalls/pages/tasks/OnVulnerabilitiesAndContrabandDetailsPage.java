package uk.gov.justice.digital.hmpps.managerecalls.pages.tasks;

import cucumber.pages.VulnerabilityAndContrabandDetailsPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import uk.gov.justice.digital.hmpps.managerecalls.pages.elements.VulnerabilityAndContrabandDetailsForm;

public class OnVulnerabilitiesAndContrabandDetailsPage {

    public static Performable submitTheVulnerabilitiesAndContrabandDetails(){
        return Task.where("{0} submits the vulnerabilities and contraband details",
                Click.on(VulnerabilityAndContrabandDetailsForm.YES_OPTION_FOR_VULNERABILITIES_RADIO_BUTTON),
                Enter.theValue("offender vulnerable to bullying").into(VulnerabilityAndContrabandDetailsForm.VULNERABILITIES_INPUT_FIELD),
                Click.on(VulnerabilityAndContrabandDetailsForm.YES_OPTION_FOR_CONTRABAND_RADIO_BUTTON),
                Enter.theValue("yes, the offender has smuggled drugs in the past").into(VulnerabilityAndContrabandDetailsForm.VULNERABILITIES_INPUT_FIELD),
                SelectFromOptions.byValue("LEVEL_1").from(VulnerabilityAndContrabandDetailsPage.SELECT_MAPPA_LEVEL),
                Click.on(VulnerabilityAndContrabandDetailsForm.CONTINUE_BUTTON)
        );
    }
}
