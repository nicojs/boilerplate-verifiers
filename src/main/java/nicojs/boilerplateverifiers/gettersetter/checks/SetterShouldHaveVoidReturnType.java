package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.wrappers.SetterDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Setters;

public class SetterShouldHaveVoidReturnType extends GetterSetterCheck {
    @Override
    public VerificationResult execute(GetSetVerificationContext context) {
        Setters setters = context.getMethods().setters();
        for (SetterDeclaration setter : setters) {
            if (setter.getReturnType() != void.class) {
                addFailure(setter.getName());
            }
        }
        return returnResult();
    }

    @Override
    protected String errorFormat() {
        return "Setter has a non-void return type: %s";
    }
}
