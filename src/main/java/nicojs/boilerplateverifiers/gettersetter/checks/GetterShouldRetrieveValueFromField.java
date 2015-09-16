package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Fields;
import nicojs.boilerplateverifiers.gettersetter.wrappers.GetterDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Getters;

import static nicojs.boilerplateverifiers.gettersetter.helpers.JavaBeansNameParser.propertyMethodToField;

public class GetterShouldRetrieveValueFromField extends GetterSetterCheck {
    private static final String ERROR_FORMAT = "Getters were found that do not return the field value: %s.";

    @Override
    public VerificationResult execute(GetSetVerificationContext context) {
        Getters getters = context.getMethods().getters();
        Fields fields = context.getFields();

        for (GetterDeclaration getter : getters) {
            Object instance = context.newConfiguredInstance();
            Object getterValue = getter.invoke(instance);

            String fieldOfGetter = propertyMethodToField(getter.getName());
            Object fieldValue = fields.getFieldByName(fieldOfGetter).get(instance);
            if (!fieldValue.equals(getterValue)) {
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
