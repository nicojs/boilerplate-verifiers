package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.Methods;

public class SetMethodShouldReferenceField extends PropertyMethodShouldReferenceAField {
    private final String ERROR_FORMAT = "Setters were found that do not reference a field: %s";

    @Override
    protected String errorFormat() {
        return ERROR_FORMAT;
    }

    @Override
    protected Methods getMethodsToTest(GetSetVerificationContext context) {
        return context.getMethods().setterMethods();
    }
}
