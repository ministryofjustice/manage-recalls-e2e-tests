package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class CreateDossierConfirmEmailedPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Email the dossier and letter";
    public static final Target CONFIRM_DOSSIER_EMAIL_SENT_CHECKBOX = Target.the("checkbox to confirm that an email has been sent for the dossier").located(By.name("confirmDossierEmailSent"));
    public static final Target DAY_FIELD = Target.the("dossier email sent - day field").located(By.name("dossierEmailSentDateDay"));
    public static final Target MONTH_FIELD = Target.the("dossier email sent - month field").located(By.name("dossierEmailSentDateMonth"));
    public static final Target YEAR_FIELD = Target.the("dossier email sent - year field").located(By.name("dossierEmailSentDateYear"));
    public static final Target UPLOAD_FILE = Target.the("upload file").located(org.openqa.selenium.By.name("dossierEmailFileName"));

}