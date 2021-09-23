package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
public class CreateDossierDownloadDossierAndLetterPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Download the dossier and letter";
    public static final Target DOWNLOAD_DOSSIER_LINK = getTargetByDataQa("getDossierLink");
    public static final Target DOWNLOAD_LETTER_LINK = getTargetByDataQa("getLetterLink");
    public static final Target CONFIRM_DOSSIER_CHECKED = getTargetByName("hasDossierBeenChecked");

}