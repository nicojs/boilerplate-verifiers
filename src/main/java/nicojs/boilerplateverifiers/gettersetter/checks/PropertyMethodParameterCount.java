package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Methods;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.wrappers.MethodDeclaration;

import java.lang.reflect.Method;

public abstract class PropertyMethodParameterCount extends GetterSetterCheck {

    protected abstract Methods methodsToTest(GetSetVerificationContext context);

    protected abstract int parameterCount();


    @Override
    public final VerificationResult execute(GetSetVerificationContext context) {
        Methods methods = methodsToTest(context);

        for (MethodDeclaration entry : methods) {
            Method method = entry.getMethod();
            if (method.getParameterCount() != parameterCount()) {
                addFailure(entry.getName());
            }
        }
        return returnResult();
    }
}
