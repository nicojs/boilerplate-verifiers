package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.*;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Fields;
import nicojs.boilerplateverifiers.gettersetter.wrappers.MethodDeclaration;

public abstract class PropertyMethodShouldReferenceAField extends GetterSetterCheck {

    protected abstract Methods getMethodsToTest(GetSetVerificationContext context);

    @Override
    public final VerificationResult execute(GetSetVerificationContext context) {
        Fields fields = context.getFields();
        Methods methodsToTest = getMethodsToTest(context);

        for (MethodDeclaration entry : methodsToTest) {
            String expectedFieldName = JavaBeansNameParser.propertyMethodToField(entry.getName());
            if (!fields.hasFieldWithName(expectedFieldName)) {
                addFailure(entry.getName());
            }
        }

        return returnResult();
    }
}
