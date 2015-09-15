package nicojs.boilerplateverifiers.gettersetter;

import lombok.Builder;
import lombok.Getter;
import nicojs.boilerplateverifiers.internals.Instantiator;
import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import java.lang.reflect.Field;
import java.util.Map;

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
        for (Map.Entry<String, Field> entry : fields.entrySet()) {
            Field field = entry.getValue();
            fillField(instance, field);
        }
        return instance;
    }

    private void fillField(T instance, Field field) {
        Class<?> fieldType = field.getType();
        field.setAccessible(true);
        Object value = valueFactories.provideNextValue(fieldType);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        }
        field.setAccessible(false);
    }
}
