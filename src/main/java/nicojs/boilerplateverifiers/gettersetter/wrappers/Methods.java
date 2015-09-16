package nicojs.boilerplateverifiers.gettersetter.wrappers;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Methods implements Iterable<MethodDeclaration> {
    private Map<String, MethodDeclaration> methodMap = new HashMap<>();

    public void put(String name, Method method) {
        this.methodMap.put(name, new MethodDeclaration(name, method));
    }

    public Methods getterMethods() {
        Methods getters = new Methods();
        for (MethodDeclaration entry : this) {
            if (entry.getName().startsWith("get")) {
                getters.put(entry.getName(), entry.getMethod());
            }
        }
        return getters;
    }


    public Methods setterMethods() {
        Methods setters = new Methods();
        for (MethodDeclaration entry : this) {
            if (entry.getName().startsWith("set")) {
                setters.put(entry.getName(), entry.getMethod());
            }
        }
        return setters;
    }

    public Getters getters() {
        Getters getters = new Getters();
        for (MethodDeclaration method : getterMethods()) {
            getters.put(method.getName(), new GetterDeclaration(method.getName(), method.getMethod()));
        }
        return getters;
    }

    public Setters setters() {
        Setters setters = new Setters();
        for (MethodDeclaration method : setterMethods()) {
            setters.put(method.getName(), new SetterDeclaration(method.getName(), method.getMethod()));
        }
        return setters;
    }

    public boolean hasMethodWithName(String methodName) {
        return methodMap.containsKey(methodName);
    }

    @Override
    public Iterator<MethodDeclaration> iterator() {
        return methodMap.values().iterator();
    }
}
