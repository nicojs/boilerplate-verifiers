package nicojs.boilerplateverifiers.internals.valuefactories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a InstantiationContextProvider
 * Created by nicojs on 8/21/2015.
 */
public class InvocationContext {

    public static final int DEFAULT_CAPACITY = 1;
    private final int capacityPerType;

    private Map<Class, ObjectContainer> complexObjects;

    public InvocationContext(int capacityPerType) {
        this.capacityPerType = capacityPerType;
        complexObjects = new HashMap<>();
    }

    public InvocationContext() {
        this(DEFAULT_CAPACITY);
    }

    public Object get(Class<?> clazz) {
        final ObjectContainer objectContainer = complexObjects.get(clazz);
        Object object = null;
        if (objectContainer != null) {
            object = objectContainer.get();
        }
        return object;
    }

    public void add(Class<?> clazz, Object object) {
        ObjectContainer objectContainer = complexObjects.get(clazz);
        if (objectContainer == null) {
            objectContainer = new ObjectContainer();
            complexObjects.put(clazz, objectContainer);
        }
        objectContainer.add(object);
    }

    private class ObjectContainer {
        private List<Object> objects;
        private int currentIndex;

        private ObjectContainer() {
            objects = new ArrayList<>(capacityPerType);
            currentIndex = 0;
        }

        private Object get() {
            // Rotate the objects
            Object theObject = null;
            if (objects.size() >= capacityPerType) {
                theObject = objects.get(currentIndex % capacityPerType);
                currentIndex++;
            }
            return theObject;
        }

        private void add(Object object) {
            objects.add(object);
        }
    }
}
