package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.wrappers.MethodDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Methods;

public abstract class PropertyMethodParameterCount extends GetterSetterCheck {

    protected abstract Methods methodsToTest(GetSetVerificationContext context);

    protected abstract int parameterCount();


    @Override
    public final VerificationResult execute(GetSetVerificationContext context) {
        Methods methods = methodsToTest(context);

        for (MethodDeclaration method : methods) {
            if (method.getParameterCount() != parameterCount()) {
                addFailure(method.getName());
            }
        }
        return returnResult();
    }
}
