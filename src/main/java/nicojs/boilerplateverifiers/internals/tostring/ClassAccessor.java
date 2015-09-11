package nicojs.boilerplateverifiers.internals.tostring;

import java.util.List;

/**
 * Represents a ClassAccessor
 * Created by nicojs
 */
public class ClassAccessor {
    private final Class targetClass;
    private final List<AttributeAccessor> attributes;
    private ClassAccessor superAccessor;


    public ClassAccessor(Class targetClass) {
        this.targetClass = targetClass;
        attributes = AttributeAccessor.inspectAttributes(targetClass);
        inspect();
    }

    private void inspect() {
        if (targetClass.getSuperclass() != null) {
            superAccessor = new ClassAccessor(targetClass.getSuperclass());
        }
    }

    public void verifyAttributes(Object actualObject, String actualStringRepresentation) {
        for (AttributeAccessor attribute : attributes) {
            attribute.verify(actualObject, actualStringRepresentation);
        }
        if (superAccessor != null) {
            superAccessor.verifyAttributes(actualObject, actualStringRepresentation);
        }
    }
}
