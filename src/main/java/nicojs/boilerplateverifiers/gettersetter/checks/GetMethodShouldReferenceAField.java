package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.Methods;

public class GetMethodShouldReferenceAField extends PropertyMethodShouldReferenceAField {
    private static final String ERROR_FORMAT = "Getters were found that do not reference a field: %s";

    @Override
    protected Methods getMethodsToTest(GetSetVerificationContext context) {
        return context.getMethods().onlyGetters();
    }

    @Override
    protected String errorFormat() {
        return ERROR_FORMAT;
    }

}
