package cucumber.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class RecordIssuanceOfRecallNotificationPage extends PpudPage {

    public static final String TITLE = TITLE_ROOT + "Assess a recall - Email the recall notification";
    public static final Target CONFIRM_RECALL_NOTIFICATION_EMAIL_SENT_CHECKBOX = Target.the("checkbox to confirm that an email has been sent for the recall notification").located(By.id("confirmRecallNotificationEmailSent"));
    public static final Target DAY_FIELD = Target.the("recall notification email sent - day field").located(org.openqa.selenium.By.id("recallNotificationEmailSentDateTime-recallNotificationEmailSentDateTimeDay"));
    public static final Target MONTH_FIELD = Target.the("recall notification email sent - month field").located(org.openqa.selenium.By.id("recallNotificationEmailSentDateTime-recallNotificationEmailSentDateTimeMonth"));
    public static final Target YEAR_FIELD = Target.the("recall notification email sent - year field").located(org.openqa.selenium.By.id("recallNotificationEmailSentDateTime-recallNotificationEmailSentDateTimeYear"));
    public static final Target HOUR_FIELD = Target.the("recall notification email sent - hour field").located(org.openqa.selenium.By.id("recallNotificationEmailSentDateTime-recallNotificationEmailSentDateTimeHour"));
    public static final Target MINUTE_FIELD = Target.the("recall notification email sent - minute field").located(org.openqa.selenium.By.id("recallNotificationEmailSentDateTime-recallNotificationEmailSentDateTimeMinute"));

}