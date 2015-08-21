package nicojs.boilerplateverifiers.internals;

import nicojs.boilerplateverifiers.internals.valuefactories.ComplexObjectValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.EnumValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.InvocationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a ValueFactoryMap
 * Created by nicojs on 8/13/2015.
 */
public class ValueFactories {

    private final static Map<Class, Class> SIMPLIFICATION_MAP;
    private final InvocationContext context;

    static{
        SIMPLIFICATION_MAP = new HashMap<>();
        SIMPLIFICATION_MAP.put(Boolean.class, boolean.class);
        SIMPLIFICATION_MAP.put(Byte.class, byte.class);
        SIMPLIFICATION_MAP.put(Character.class, char.class);
        SIMPLIFICATION_MAP.put(Double.class, double.class);
        SIMPLIFICATION_MAP.put(Float.class, float.class);
        SIMPLIFICATION_MAP.put(Integer.class, int.class);
        SIMPLIFICATION_MAP.put(Long.class, long.class);
        SIMPLIFICATION_MAP.put(Short.class, short.class);
    }

    private Map<Class, ValueFactory> factoryMap;

    public ValueFactories() {
        factoryMap = new HashMap<>();
        context = new InvocationContext();
    }

    @SuppressWarnings("unchecked")
    public ValueFactory get(Class clazz) {
        Class simplifiedClass = simplify(clazz);
        ValueFactory valueFactory = factoryMap.get(simplifiedClass);
        if (valueFactory == null) {
            if (simplifiedClass.isEnum()) {
                valueFactory = new EnumValueFactory(simplifiedClass);
            } else {
                valueFactory = new ComplexObjectValueFactory(simplifiedClass, this, context);
            }
            factoryMap.put(simplifiedClass, valueFactory);
        }
        return valueFactory;
    }

    private Class simplify(Class clazz) {
        Class simpleClass = SIMPLIFICATION_MAP.get(clazz);
        if(simpleClass == null){
            simpleClass = clazz;
        }
        return simpleClass;
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
