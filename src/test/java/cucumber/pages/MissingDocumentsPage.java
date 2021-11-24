package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class MissingDocumentsPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Book a recall - Missing documents";
    public static final Target TEXT_FIELD_TO_PROVIDE_MORE_DETAILS = Target.the("Text field to add details as to why the documents are missing").located(By.id("missingDocumentsDetail"));
    public static final Target UPLOAD_FILE = Target.the("upload file").located(By.id("missingDocumentsEmailFileName"));
}
