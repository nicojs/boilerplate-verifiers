package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.Methods;

public class GetterShouldBePublic extends PropertyMethodShouldBePublic {
    @Override
    protected Methods getMethodsToTest(GetSetVerificationContext context) {
        return context.getMethods().getterMethods();
    }

    @Override
    protected String errorFormat() {
        return "Getter was not public: %s.";
    }
}
