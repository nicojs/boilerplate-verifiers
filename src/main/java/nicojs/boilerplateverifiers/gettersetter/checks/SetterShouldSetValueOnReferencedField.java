package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Fields;
import nicojs.boilerplateverifiers.gettersetter.wrappers.SetterDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Setters;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import static nicojs.boilerplateverifiers.gettersetter.helpers.JavaBeansNameParser.propertyMethodToField;

public class SetterShouldSetValueOnReferencedField extends GetterSetterCheck {
    private static final String ERROR_FORMAT = "Setter was found that doesn't affect the referenced field: %s.";

    @Override
    public VerificationResult execute(GetSetVerificationContext context) {
        Setters setters = context.getMethods().setters();
        Fields fields = context.getFields();

        for (SetterDeclaration setter : setters) {
            Object parameterObject = constructParameterObject(setter, context.getValueFactories());
            Object instance = context.newEmptyInstance();
            setter.invoke(instance, parameterObject);

            String fieldOfSetter = propertyMethodToField(setter.getName());

            Object fieldValue = fields.getFieldByName(fieldOfSetter).get(instance);
            if (fieldValue == null || !fieldValue.equals(parameterObject)) {
                addFailure(setter.getName());
            }
        }

        return returnResult();
    }

    private Object constructParameterObject(SetterDeclaration setterDeclaration, ValueFactories valueFactories) {
        Class<?> parameterType = setterDeclaration.getParameterType();
        return valueFactories.provideNextValue(parameterType);
    }

    @Override
    protected String errorFormat() {
        return ERROR_FORMAT;
    }
}
