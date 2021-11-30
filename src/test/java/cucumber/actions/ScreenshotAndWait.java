package cucumber.actions;

import net.serenitybdd.screenplay.Performable;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ScreenshotAndWait {

    public static Performable forMillis(int millisToWait) { return instrumented(TakeScreenshotAndWaitForMillis.class, millisToWait); }

}
