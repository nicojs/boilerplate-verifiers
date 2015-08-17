package nicojs.boilerplateverifiers.internals.valuefactories;

import nicojs.boilerplateverifiers.internals.Instantiator;
import nicojs.boilerplateverifiers.internals.ValueFactories;
import nicojs.boilerplateverifiers.internals.ValueFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Represents a ClassValueFactory
 * Created by nicojs on 8/13/2015.
 */
public class ComplexObjectValueFactory<T> extends ValueFactory<T> {

    private final ValueFactories otherValueFactories;

    public ComplexObjectValueFactory(Class<T> targetClass, ValueFactories otherValueFactories) {
        super(targetClass);
        this.otherValueFactories = otherValueFactories;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next() {
        Instantiator instantiator = Instantiator.of(getTargetClass());
        T newInstance = (T) instantiator.instantiate();
        scramble(newInstance);
        return newInstance;
    }

    private void scramble(T newInstance) {
        Class<?> clazz = newInstance.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && !isReserved(field.getName())) {
                field.setAccessible(true);
                try {
                    field.set(newInstance, otherValueFactories.provideNextValue(field.getType()));
                } catch (IllegalAccessException e) {
                    throw new AssertionError(String.format("Could not set field \"%s\" of class \"%s\", which is necessary to instantiate a unique value of class \"%s\".",
                            field.getName(), field.getType().getSimpleName(), clazz.getSimpleName()), e);
                }
            }
        }

    }

    private boolean isReserved(String fieldName) {
        // Compiled inner classes have the this property as this$0
        return fieldName.contains("$");
    }
}
