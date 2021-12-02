package cucumber.actions;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Refresh {

    public static Performable page() { return instrumented(Reload.class); }

}
