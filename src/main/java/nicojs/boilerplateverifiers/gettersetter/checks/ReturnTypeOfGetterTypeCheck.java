package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.helpers.JavaBeansNameParser;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.wrappers.FieldDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Fields;
import nicojs.boilerplateverifiers.gettersetter.wrappers.GetterDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Getters;

public class ReturnTypeOfGetterTypeCheck extends GetterSetterCheck {
    private static final String ERROR_FORMAT = "Return Type of getter was not the same type as the referenced field: %s";

    @Override
    public VerificationResult execute(GetSetVerificationContext context) {
        Getters getters = context.getMethods().getters();
        Fields fields = context.getFields();

        for (GetterDeclaration getter : getters) {
            String expectedFieldName = JavaBeansNameParser.propertyMethodToField(getter.getName());
            FieldDeclaration field = fields.getFieldByName(expectedFieldName);
            if (field.getType() != getter.getReturnType()) {
                addFailure(getter.getName());
            }
        }

        return returnResult();
    }

    @Override
    protected String errorFormat() {
        return ERROR_FORMAT;
    }
}
