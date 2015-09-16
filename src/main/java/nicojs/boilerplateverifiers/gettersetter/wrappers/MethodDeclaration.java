package nicojs.boilerplateverifiers.gettersetter.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@AllArgsConstructor
@Getter
public class MethodDeclaration {
    private String name;
    private Method method;

    public Class<?> getReturnType() {
        return method.getReturnType();
    }
}
