package nicojs.boilerplateverifiers.gettersetter.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;

import static org.junit.Assert.fail;

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
            //TODO introduce own error message
            fail(e.getMessage());
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
            fail(e.getMessage());
        }
        field.setAccessible(accessible);
    }
}
