package nicojs.boilerplateverifiers.gettersetter.wrappers;

import nicojs.boilerplateverifiers.gettersetter.exceptions.VerificationExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
            throw new VerificationExecutionException("Error while invoking setter method: " + getName(), e);
        }
        getMethod().setAccessible(accessible);
    }
}
