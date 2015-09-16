package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.JavaBeansNameParser;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.wrappers.FieldDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Fields;
import nicojs.boilerplateverifiers.gettersetter.wrappers.SetterDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Setters;

public class ParameterOfSetterTypeCheck extends GetterSetterCheck {
    private static final String ERROR_FORMAT = "Parameter of setter was not the same type as the referenced field: %s";

    @Override
    public VerificationResult execute(GetSetVerificationContext context) {
        Setters setters = context.getMethods().setters();
        Fields fields = context.getFields();

        for (SetterDeclaration entry : setters) {
            String expectedFieldName = JavaBeansNameParser.propertyMethodToField(entry.getName());
            FieldDeclaration field = fields.getFieldByName(expectedFieldName);
            if (field.getType() != entry.getParameterType()) {
                addFailure(entry.getName());
            }
        }

        return returnResult();
    }

    @Override
    protected String errorFormat() {
        return ERROR_FORMAT;
    }
}
