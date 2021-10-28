package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class UploadRecallDocumentsPage extends PpudPage {
    public static final String TITLE = TITLE_ROOT + "Book a recall - Upload documents";
    public static final Target DOCUMENT_UPLOAD = Target.the("upload element").located(By.name("documents"));
    public static final Target SELECT_CATEGORY_UNCATEGORISED = Target.the("select element").locatedBy("[data-qa='category-UNCATEGORISED']");

    public static Target waitForNumberOfDocuments(String number){
        return Target.the("category").locatedBy("[data-qa='document-count-" + number + "']");
    }

    public static Target getTargetForCategoryDropdownByNumber(Number number){
         return Target.the("category").locatedBy("[data-qa='category-index-UNCATEGORISED-" + number + "']");
    }
}
