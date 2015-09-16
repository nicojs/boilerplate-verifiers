package nicojs.boilerplateverifiers.gettersetter.wrappers;

import java.lang.reflect.Method;

public class GetterDeclaration extends MethodDeclaration {
    public GetterDeclaration(String name, Method method) {
        super(name, method);
    }
}
