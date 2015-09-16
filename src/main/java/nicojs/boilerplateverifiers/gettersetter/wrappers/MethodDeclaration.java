package nicojs.boilerplateverifiers.gettersetter.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nicojs.boilerplateverifiers.gettersetter.exceptions.VerificationExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
@Getter
public class MethodDeclaration {
    private String name;
    private Method method;

    public Class<?> getReturnType() {
        return method.getReturnType();
    }

    public int getParameterCount() {
        return method.getParameterTypes().length;
    }
}
