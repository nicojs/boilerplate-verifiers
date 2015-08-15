package nicojs.boilerplateverifiers.internals.valuefactories;

import nicojs.boilerplateverifiers.internals.Instantiator;
import nicojs.boilerplateverifiers.internals.ValueFactories;
import nicojs.boilerplateverifiers.internals.ValueFactory;

import java.lang.reflect.Field;

import static junit.framework.TestCase.fail;

/**
 * Represents a ClassValueFactory
 * Created by nicojs on 8/13/2015.
 */
public class ClassValueFactory<T> extends ValueFactory<T> {

    private final ValueFactories otherValueFactories;

    public ClassValueFactory(Class<T> targetClass, ValueFactories otherValueFactories) {
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
            field.setAccessible(true);
            try {
                field.set(newInstance, otherValueFactories.provideNextValue(field.getType()));
            } catch (IllegalAccessException e) {
                fail(String.format("Could not set field \"%s\" of class \"%s\", which necessary to instantiate a unique value of the class.",
                        field.getName(), clazz));
            }
        }

    }
}
