package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.Methods;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;

import java.lang.reflect.Method;
import java.util.Map;

public class SetterShouldHaveVoidReturnType extends GetterSetterCheck {
    @Override
    public VerificationResult execute(GetSetVerificationContext context) {
        Methods setters = context.getMethods().onlySetters();
        for (Map.Entry<String, Method> entry : setters.entrySet()) {
            Method method = entry.getValue();
            if (method.getReturnType() != void.class) {
                addFailure(entry.getKey());
            }
        }
        return returnResult();
    }

    @Override
    protected String errorFormat() {
        return "Setter has a non-void return type: %s";
    }
}
