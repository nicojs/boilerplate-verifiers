package nicojs.boilerplateverifiers.gettersetter.wrappers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.fail;

public class SetterDeclaration extends MethodDeclaration {
    public SetterDeclaration(String name, Method method) {
        super(name, method);
    }

    public Class<?> getParameterType() {
        return getMethod().getParameterTypes()[0];
    }

    public void invoke(Object instance, Object setterValue) {
        boolean accessible = getMethod().isAccessible();
        getMethod().setAccessible(true);
        try {
            getMethod().invoke(instance, setterValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            //TODO create error / exception
            fail(e.getMessage());
        }
        getMethod().setAccessible(accessible);
    }
}
