package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.Methods;

public class SetterShouldBePublic extends PropertyMethodShouldBePublic {

    @Override
    protected String errorFormat() {
        return "Setter was not public: %s.";
    }

    @Override
    protected Methods getMethodsToTest(GetSetVerificationContext context) {
        return context.getMethods().onlySetters();
    }
}
