package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;

public class ChangeHistoryDocumentCategoryPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Uploaded document change history";
    public static final Target MISSING_DOCUMENT_TEXT = getTargetByDataQa("missingDocumentsLabel");
    public static final Target MISSING_DOCUMENT_DETAILS = Target.the("missing document details").locatedBy("//*[@id=\"main-content\"]/div/div/dl[@data-qa=\"missing-1-row\"]/div[2]/dd");
    public static final Target EMAIL_UPLOADED = getTargetByDataQa("missingDocumentsEmail");
}
