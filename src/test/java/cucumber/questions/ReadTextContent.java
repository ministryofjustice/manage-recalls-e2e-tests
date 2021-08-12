package cucumber.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class ReadTextContent implements Question<String> {
    private Target target;

    private ReadTextContent(Target target) {
        this.target = target;
    }

    public static ReadTextContent textContent(Target target) {
        return new ReadTextContent(target);
    }

    @Override
    public String answeredBy(Actor actor) {
        return target.resolveFor(actor).getTextContent();
    }
}
