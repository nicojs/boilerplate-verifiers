package nicojs.boilerplateverifiers.internals.valuefactories;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.Instantiator;
import nicojs.boilerplateverifiers.internals.ValueProvider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Represents a ValueFactory for complex objects (generally: entity classes)
 * Created by nicojs on 8/13/2015.
 */
public class ComplexObjectValueFactory<T> extends ValueFactory<T> {

    private final ValueProvider valueProvider;

    public ComplexObjectValueFactory(Class<T> targetClass, ValueProvider valueProvider) {
        super(targetClass);
        this.valueProvider = valueProvider;
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
            populate(newInstance, context, newInstance.getClass());
            return newInstance;
        }
    }

    /**
     * Recursive function for populating the attributes of a given instance.
     * Uses reflection to set all attributes to new (unique) values.
     * @param newInstance The instance to populate
     * @param context The context maintaining the state of the graph that is currently being created.
     * @param clazz The class of which the attributes will be populated in this iteration (will recursively also populate all superclasses)
     */
    private void populate(T newInstance, GraphCreationContext context, Class clazz) {
        if (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) && !isReserved(field.getName())) {
                    field.setAccessible(true);
                    try {
                        field.set(newInstance, valueProvider.provideNextValue(field.getType(), context));
                    } catch (IllegalAccessException e) {
                        throw new AssertionError(String.format("Could not set field \"%s\" of class \"%s\", which is necessary to instantiate a unique value of class \"%s\".",
                                field.getName(), field.getType().getSimpleName(), clazz.getSimpleName()), e);
                    }
                }
            }
            populate(newInstance, context, clazz.getSuperclass());
        }

    }

    private boolean isReserved(String fieldName) {
        // Compiled inner classes have the this property as this$0
        return fieldName.contains("$");
    }
}
