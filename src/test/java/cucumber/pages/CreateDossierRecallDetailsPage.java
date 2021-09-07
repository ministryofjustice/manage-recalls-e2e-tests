package cucumber.pages;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.util.EnvironmentVariables;

public class CreateDossierRecallDetailsPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Assess a recall";

    public static final Target ADDITIONAL_LICENCE_CONDITIONS = getTargetByDataQa("additionalLicenceConditions");
    public static final Target MORE_DETAILS_FOR_ADDITIONAL_LICENCE_CONDITIONS_TEXT = getTargetByDataQa("additionalLicenceConditionsDetail");
    public static final Target DIFFERENT_NOMIS_NUMBER = getTargetByDataQa("differentNomsNumber");
    public static final Target MORE_DETAILS_FOR_DIFFERENT_NOMIS_NUMBER_TEXT = getTargetByDataQa("differentNomsNumberDetail");

    public void getCreateDossierRecallDetailsPage(EnvironmentVariables environmentVariables, String nomsNumber, String recallId){
        getDriver().get(EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("webdriver.base.url") + "/persons/" + nomsNumber + "/recalls/" + recallId + "/assess");
    }

}
