package nicojs.boilerplateverifiers.internals.tostring;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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
        this.attributes = new ArrayList<>();
        inspect();
    }

    private void inspect() {
        for (Field field : targetClass.getDeclaredFields()) {
            if(isValid(field)) {
                attributes.add(new AttributeAccessor(field));
            }
        }
        if(targetClass.getSuperclass() != null){
            superAccessor = new ClassAccessor(targetClass.getSuperclass());
        }
    }

    private boolean isValid(Field field) {
        return !Modifier.isStatic(field.getModifiers());
    }

    public void verifyAttributes(Object actualObject, String actualStringRepresentation){
        for (AttributeAccessor attribute : attributes) {
            attribute.verify(actualObject, actualStringRepresentation);
        }
        if(superAccessor != null){
            superAccessor.verifyAttributes(actualObject, actualStringRepresentation);
        }
    }
}
