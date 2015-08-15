package nicojs.boilerplateverifiers.internals;

import nicojs.boilerplateverifiers.internals.valuefactories.ClassValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.EnumValueFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a ValueFactoryMap
 * Created by nicojs on 8/13/2015.
 */
public class ValueFactories {
    private Map<Class, ValueFactory> factoryMap;

    public ValueFactories() {
        factoryMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public ValueFactory get(Class clazz) {
        ValueFactory valueFactory = factoryMap.get(clazz);
        if (valueFactory == null) {
            if (clazz.isEnum()) {
                valueFactory = new EnumValueFactory(clazz);
            } else {
                valueFactory = new ClassValueFactory(clazz, this);
            }
            factoryMap.put(clazz, valueFactory);
        }
        return valueFactory;
    }

    public Object provideNextValue(Class clazz) {
        return get(clazz).next();
    }

    void putIfNotExists(ValueFactory... valueFactories) {
        for (ValueFactory valueFactory : valueFactories) {
            if (factoryMap.get(valueFactory.getTargetClass()) == null) {
                factoryMap.put(valueFactory.getTargetClass(), valueFactory);
            }
        }

    }
}
