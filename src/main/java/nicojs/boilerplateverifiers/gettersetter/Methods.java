package nicojs.boilerplateverifiers.gettersetter;

import java.lang.reflect.Method;
import java.util.HashMap;

public class Methods extends HashMap<String, Method> {

    public Methods onlyGetters() {
        Methods getters = new Methods();
        for (Entry<String, Method> entry : this.entrySet()) {
            if (entry.getKey().startsWith("get")) {
                getters.put(entry.getKey(), entry.getValue());
            }
        }
        return getters;
    }

    public Methods onlySetters() {
        Methods setters = new Methods();
        for (Entry<String, Method> entry : this.entrySet()) {
            if (entry.getKey().startsWith("set")) {
                setters.put(entry.getKey(), entry.getValue());
            }
        }
        return setters;
    }

    public boolean hasMethodWithName(String fieldName) {
        return this.containsKey(fieldName);
    }
}
