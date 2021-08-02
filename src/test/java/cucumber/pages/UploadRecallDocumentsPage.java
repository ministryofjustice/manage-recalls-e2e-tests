package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;
import net.thucydides.core.pages.components.FileToUpload;
import org.openqa.selenium.By;

@DefaultUrl("https://manage-recalls-dev.hmpps.service.justice.gov.uk/assess-recall")
public class UploadRecallDocumentsPage extends PpudPage {
    WebElementFacade fileUpload;
    public static final String TITLE = TITLE_ROOT + "Upload documents";
    public static final Target CONTINUE_BUTTON = getTargetByDataQa("continueButton");

	public void uploadFile(String filePath) {
        WebDriver driver = getDriver();
        FileToUpload fileToUpload = new FileToUpload(driver, filePath);
        fileToUpload.fromLocalMachine().to(find(By.name("LICENCE")));
    }
}
