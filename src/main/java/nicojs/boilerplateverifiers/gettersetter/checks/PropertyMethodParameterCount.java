package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.Methods;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;

import java.lang.reflect.Method;
import java.util.Map;

public abstract class PropertyMethodParameterCount extends GetterSetterCheck {

    protected abstract Methods methodsToTest(GetSetVerificationContext context);

    protected abstract int parameterCount();


    @Override
    public final VerificationResult execute(GetSetVerificationContext context) {
        Methods methods = methodsToTest(context);

        for (Map.Entry<String, Method> entry : methods.entrySet()) {
            Method method = entry.getValue();
            if (method.getParameterCount() != parameterCount()) {
                addFailure(entry.getKey());
            }
        }
        return returnResult();
    }
}
