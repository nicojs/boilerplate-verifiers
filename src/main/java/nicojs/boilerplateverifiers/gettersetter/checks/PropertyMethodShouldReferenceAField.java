package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.*;

public abstract class PropertyMethodShouldReferenceAField extends GetterSetterCheck {

    protected abstract Methods getMethodsToTest(GetSetVerificationContext context);

    @Override
    public final VerificationResult execute(GetSetVerificationContext context) {
        Fields fields = context.getFields();
        Methods methodsToTest = getMethodsToTest(context);

        for (String setterMethodName : methodsToTest.keySet()) {
            String expectedFieldName = JavaBeansNameParser.propertyMethodToField(setterMethodName);
            if (!fields.hasFieldWithName(expectedFieldName)) {
                addFailure(setterMethodName);
            }
        }

        return returnResult();
    }
}
