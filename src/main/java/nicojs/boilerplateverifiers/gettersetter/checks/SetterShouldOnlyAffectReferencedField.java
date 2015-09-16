package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;
import nicojs.boilerplateverifiers.gettersetter.helpers.JavaBeansNameParser;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.wrappers.FieldDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Fields;
import nicojs.boilerplateverifiers.gettersetter.wrappers.SetterDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Setters;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import static nicojs.boilerplateverifiers.gettersetter.helpers.JavaBeansNameParser.propertyMethodToField;

public class SetterShouldOnlyAffectReferencedField extends GetterSetterCheck {
    private static final String ERROR_FORMAT = "Setter was found that affects field different than referenced field: %s.";

    @Override
    public VerificationResult execute(GetSetVerificationContext context) {
        Setters setters = context.getMethods().setters();
        for (SetterDeclaration setter : setters) {
            Object parameterObject = constructParameterObject(setter, context.getValueFactories());
            Object instance = context.newEmptyInstance();
            setter.invoke(instance, parameterObject);

            String fieldOfSetter = propertyMethodToField(setter.getName());
            boolean wasOtherFieldChanged = wasOtherFieldChanged(context.getFields(), fieldOfSetter, instance);

            if (wasOtherFieldChanged) {
                addFailure(setter.getName());
            }
        }

        return returnResult();
    }

    private Object constructParameterObject(SetterDeclaration setterDeclaration, ValueFactories valueFactories) {
        Class<?> parameterType = setterDeclaration.getParameterType();
        return valueFactories.provideNextValue(parameterType);
    }

    private boolean wasOtherFieldChanged(Fields fields, String changedFieldName, Object instance) {
        for (FieldDeclaration field : fields) {
            if (field.getName().equals(changedFieldName)) {
                //skip, because it was the field we changed
            } else {
                Object fieldValue = field.get(instance);
                if (fieldValue != null) {
                    return true;    // failure
                }
            }
        }
        return false; //success
    }

    @Override
    protected String errorFormat() {
        return ERROR_FORMAT;
    }
}
