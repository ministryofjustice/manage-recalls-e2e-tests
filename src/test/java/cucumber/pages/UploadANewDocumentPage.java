package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class UploadANewDocumentPage extends PpudPage {
    public static final String TITLE = TITLE_ROOT + "Book a recall - Upload a new document";
    public static final Target DOCUMENT_UPLOAD = Target.the("upload element").located(By.name("document"));

}
