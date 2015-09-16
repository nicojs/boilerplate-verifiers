package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.wrappers.MethodDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Methods;

import java.lang.reflect.Modifier;

public abstract class PropertyMethodShouldBePublic extends GetterSetterCheck {
    protected abstract Methods getMethodsToTest(GetSetVerificationContext context);

    @Override
    public final VerificationResult execute(GetSetVerificationContext context) {
        Methods propertyMethods = getMethodsToTest(context);
        for (MethodDeclaration entry : propertyMethods) {
            if (!Modifier.isPublic(entry.getMethod().getModifiers())) {
                addFailure(entry.getName());
            }
        }

        return returnResult();
    }
}
