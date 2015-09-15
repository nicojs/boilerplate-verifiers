package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.Methods;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

public abstract class PropertyMethodShouldBePublic extends GetterSetterCheck {
    protected abstract Methods getMethodsToTest(GetSetVerificationContext context);

    @Override
    public final VerificationResult execute(GetSetVerificationContext context) {
        Methods propertyMethods = getMethodsToTest(context);
        for (Map.Entry<String, Method> method : propertyMethods.entrySet()) {
            if (!Modifier.isPublic(method.getValue().getModifiers())) {
                addFailure(method.getKey());
            }
        }

        return returnResult();
    }
}
