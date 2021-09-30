package cucumber.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class UploadRecallDocumentsPage extends PpudPage {
    public static final String TITLE = TITLE_ROOT + "Book a recall - Upload documents";
    public static final Target PART_A_RECALL_REPORT = Target.the("upload element: PART_A_RECALL_REPORT").located(By.name("PART_A_RECALL_REPORT"));
    public static final Target LICENCE = Target.the("upload element: LICENCE").located(By.name("LICENCE"));
    public static final Target PREVIOUS_CONVICTIONS_SHEET = Target.the("upload element: PREVIOUS_CONVICTIONS_SHEET").located(By.name("PREVIOUS_CONVICTIONS_SHEET"));
    public static final Target PRE_SENTENCING_REPORT = Target.the("upload element: PRE_SENTENCING_REPORT").located(By.name("PRE_SENTENCING_REPORT"));
}
