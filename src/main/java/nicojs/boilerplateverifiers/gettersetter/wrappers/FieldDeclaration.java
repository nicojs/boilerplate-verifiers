package nicojs.boilerplateverifiers.gettersetter.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nicojs.boilerplateverifiers.gettersetter.exceptions.VerificationExecutionException;

import java.lang.reflect.Field;

@AllArgsConstructor
@Getter
public class FieldDeclaration {
    private String name;
    private Field field;

    public Class<?> getType() {
        return field.getType();
    }

    public Object get(Object instance) {
        Object fieldValue = null;
        boolean accessible = field.isAccessible();

        field.setAccessible(true);
        try {
            fieldValue = field.get(instance);
        } catch (IllegalAccessException e) {
            throw new VerificationExecutionException("Error while getting field: " + field.getName(), e);
        } finally {
            field.setAccessible(accessible);
        }
        return fieldValue;
    }

    public void set(Object instance, Object fieldValue) {
        boolean accessible = field.isAccessible();

        field.setAccessible(true);
        try {
            field.set(instance, fieldValue);
        } catch (IllegalAccessException e) {
            throw new VerificationExecutionException("Error while setting field: " + field.getName(), e);
        } finally {
            field.setAccessible(accessible);
        }
    }
}
