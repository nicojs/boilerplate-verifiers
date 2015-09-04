package nicojs.boilerplateverifiers.internals.valuefactories;

import nicojs.boilerplateverifiers.internals.Instantiator;
import nicojs.boilerplateverifiers.internals.ValueFactories;
import nicojs.boilerplateverifiers.ValueFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Represents a ClassValueFactory
 * Created by nicojs on 8/13/2015.
 */
public class ComplexObjectValueFactory<T> extends ValueFactory<T> {

    private final ValueFactories otherValueFactories;
    private final InvocationContext context;

    public ComplexObjectValueFactory(Class<T> targetClass, ValueFactories otherValueFactories, InvocationContext context) {
        super(targetClass);
        this.otherValueFactories = otherValueFactories;
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next() {
        Instantiator instantiator = Instantiator.of(getTargetClass());
        T newInstance = (T) instantiator.instantiate();
        context.put(getTargetClass(), newInstance);
        scramble(newInstance);
        return newInstance;
    }

    private void scramble(T newInstance) {
        Class<?> clazz = newInstance.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && !isReserved(field.getName())) {
                field.setAccessible(true);
                try {
                    field.set(newInstance, provideValue(field));
                } catch (IllegalAccessException e) {
                    throw new AssertionError(String.format("Could not set field \"%s\" of class \"%s\", which is necessary to instantiate a unique value of class \"%s\".",
                            field.getName(), field.getType().getSimpleName(), clazz.getSimpleName()), e);
                }
            }
        }

    }

    private Object provideValue(Field field) {
        Object value = context.get(field.getType());
        if (value == null) {
            value = otherValueFactories.provideNextValue(field.getType());
        }
        return value;
    }

    private boolean isReserved(String fieldName) {
        // Compiled inner classes have the this property as this$0
        return fieldName.contains("$");
    }
}
