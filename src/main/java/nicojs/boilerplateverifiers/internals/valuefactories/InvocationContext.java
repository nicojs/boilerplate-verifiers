package nicojs.boilerplateverifiers.internals.valuefactories;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a InstantiationContextProvider
 * Created by nicojs on 8/21/2015.
 */
public class InvocationContext {

    @Getter
    private Map<Class, Object> complexObjects;

    public InvocationContext() {
        complexObjects = new HashMap<>();
    }

    public Object get(Class<?> clazz) {
        return complexObjects.get(clazz);
    }

    public void put(Class<?> clazz, Object object){
        complexObjects.put(clazz, object);
    }
}
