package cucumber.actions;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class TakeScreenshotAndWaitForMillis implements Performable {

    private final int millisToWait;

    public TakeScreenshotAndWaitForMillis(int millisToWait) {
        this.millisToWait = millisToWait;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            Serenity.takeScreenshot();
            Thread.sleep(millisToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
