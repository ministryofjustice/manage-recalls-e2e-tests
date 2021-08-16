package uk.gov.justice.digital.managerecalls.forms;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class SentenceOffenceAndReleaseDetailsForm extends BaseForm{

    public static final Target RELEASING_PRISON = getTargetByName("lastReleasePrison");
    public static By SENTENCE_DAY_FIELD = By.id("sentenceDate-sentenceDateDay");
    public static By SENTENCE_MONTH_FIELD = By.id("sentenceDate-sentenceDateMonth");
    public static By SENTENCE_YEAR_FIELD = By.id("sentenceDate-sentenceDateYear");

    public static By LICENCE_EXPIRY_DAY_FIELD = By.id("licenceExpiryDate-licenceExpiryDateDay");
    public static By LICENCE_EXPIRY_MONTH_FIELD = By.id("licenceExpiryDate-licenceExpiryDateMonth");
    public static By LICENCE_EXPIRY_YEAR_FIELD = By.id("licenceExpiryDate-licenceExpiryDateYear");


}
