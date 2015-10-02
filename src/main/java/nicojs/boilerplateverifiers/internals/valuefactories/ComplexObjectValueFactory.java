package nicojs.boilerplateverifiers.internals.valuefactories;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.Instantiator;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Represents a ClassValueFactory
 * Created by nicojs on 8/13/2015.
 */
public class ComplexObjectValueFactory<T> extends ValueFactory<T> {

    private final ValueFactories valueFactories;

    public ComplexObjectValueFactory(Class<T> targetClass, ValueFactories valueFactories) {
        super(targetClass);
        this.valueFactories = valueFactories;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T next(GraphCreationContext context) {
        // First, see if there is already a value for this class in the current graph
        final GraphCreationContext.GraphNodeResult node = context.getNode(getTargetClass());
        if (node.hasValue()) {
            return (T) node.getValue();
        } else {
            // If there is no value for this class yet in current graph: create a new one, fill out the graph and return it
            Instantiator instantiator = Instantiator.of(getTargetClass());
            T newInstance = (T) instantiator.instantiate();
            context.addNode(getTargetClass(), newInstance);
            scramble(newInstance, context);
            return newInstance;
        }
    }

    private void scramble(T newInstance, GraphCreationContext context) {
        Class<?> clazz = newInstance.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) && !isReserved(field.getName())) {
                field.setAccessible(true);
                try {
                    field.set(newInstance, valueFactories.provideNextValue(field.getType(), context));
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
