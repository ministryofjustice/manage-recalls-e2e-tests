package cucumber.pages;

import net.thucydides.core.pages.components.FileToUpload;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UploadRecallDocumentsPage extends PpudPage {
    public static final String TITLE = TITLE_ROOT + "Book a recall - Upload documents";

	public void uploadFile(String filePath, String doc) {
        WebDriver driver = getDriver();
        FileToUpload fileToUpload = new FileToUpload(driver, filePath);
        fileToUpload.fromLocalMachine().to(find(By.name(doc)));
    }
}
