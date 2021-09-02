package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class CreateDossierAddInfoForPrisonLetterPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Create dossier - Add information to the prison letter";
    public static final Target ADDITIONAL_LICENCE_CONDITIONS_RADIO_BUTTON = Target.the("radio button to proceed with additional licence conditions").located(By.id("additionalLicenceConditions"));
    public static final Target MORE_DETAILS_FOR_ADDITIONAL_LICENCE_CONDITIONS_TEXT_BOX = Target.the("text area to provide more details to proceed with additional licence conditions").located(By.id("additionalLicenceConditionsDetail"));
    public static final Target DIFFERENT_NOMIS_NUMBER_RADIO_BUTTON = Target.the("radio button to proceed with different nomis number").located(By.id("differentNomsNumber"));
    public static final Target MORE_DETAILS_FOR_DIFFERENT_NOMIS_NUMBER_TEXT_BOX = Target.the("text area to provide more details to proceed with different nomis number").located(By.id("differentNomsNumberDetail"));

}