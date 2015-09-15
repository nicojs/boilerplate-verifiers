package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class ParameterOfSetterTypeCheck extends GetterSetterCheck {
    private static final String ERROR_FORMAT = "Parameter of setter was not the same type as the referenced field: %s";

    @Override
    public VerificationResult execute(GetSetVerificationContext context) {
        Methods setters = context.getMethods().onlySetters();
        Fields fields = context.getFields();

        for (Map.Entry<String, Method> entry : setters.entrySet()) {
            String expectedFieldName = JavaBeansNameParser.propertyMethodToField(entry.getKey());
            Field field = fields.get(expectedFieldName);
            if (field.getType() != entry.getValue().getParameterTypes()[0]) {
                addFailure(entry.getKey());
            }
        }

        return returnResult();
    }

    @Override
    protected String errorFormat() {
        return ERROR_FORMAT;
    }
}
