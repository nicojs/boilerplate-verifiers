package nicojs.boilerplateverifiers.gettersetter;

import lombok.Builder;
import lombok.Getter;
import nicojs.boilerplateverifiers.gettersetter.wrappers.FieldDeclaration;
import nicojs.boilerplateverifiers.gettersetter.wrappers.Fields;
import nicojs.boilerplateverifiers.internals.Instantiator;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import java.lang.reflect.Field;

import static org.junit.Assert.fail;

@Getter
@Builder
public class GetSetVerificationContext<T> {
    private Class<T> classToTest;
    private Fields fields;
    private Methods methods;
    private ValueFactories valueFactories;

    public T newEmptyInstance() {
        return Instantiator.of(classToTest).instantiate();
    }

    public T newConfiguredInstance() {
        T emptyInstance = newEmptyInstance();
        return fillFields(emptyInstance);
    }

    private T fillFields(T instance) {
        for (FieldDeclaration field : fields) {
            fillField(instance, field);
        }
        return instance;
    }

    private void fillField(T instance, FieldDeclaration field) {
        Class<?> fieldType = field.getType();
        Object fieldValue = valueFactories.provideNextValue(fieldType);
        field.set(instance, fieldValue);
    }
}
