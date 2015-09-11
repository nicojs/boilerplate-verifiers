package nicojs.boilerplateverifiers.internals.tostring;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Represents a AttributeAccessor
 * Created by nicojs
 */
public class AttributeAccessor {
    private final static List<Class> PRIMITIVE_TYPES = Arrays.<Class>asList(
            AtomicInteger.class,
            Boolean.class,
            boolean.class,
            Byte.class,
            byte.class,
            Character.class,
            char.class,
            Class.class,
            Double.class,
            double.class,
            Float.class,
            float.class,
            Integer.class,
            int.class,
            Long.class,
            long.class,
            Short.class,
            short.class,
            String.class
    );
    private final Field attribute;

    public AttributeAccessor(Field attribute) {
        this.attribute = attribute;
        attribute.setAccessible(true);
    }

    public static List<AttributeAccessor> inspectAttributes(Class targetClass) {
        List<AttributeAccessor> attributes = new ArrayList<>();
        for (Field field : targetClass.getDeclaredFields()) {
            if (isValid(field)) {
                attributes.add(new AttributeAccessor(field));
            }
        }
        return attributes;
    }

    private static boolean isValid(Field field) {
        return !Modifier.isStatic(field.getModifiers());
    }

    public void verify(Object actualObject, String actualStringRepresentation) {
        if (isComplex()) {
            new ClassAccessor(attribute.getType()).verifyAttributes(get(actualObject), actualStringRepresentation);
        } else {
            final String expectedStringRepresentation = formatExpectedStringRepresentation(actualObject);
            assertThat(String.format("Could not find string representation for field \"%s\" (declared in class \"%s\").", attribute.getName(), attribute.getDeclaringClass().getSimpleName()),
                    actualStringRepresentation, containsString(expectedStringRepresentation));
        }
    }

    private boolean isComplex() {
        return !isMap() && !isCollection() && !isPrimitive();
    }

    private boolean isMap() {
        return attribute.getType().isAssignableFrom(Map.class);
    }

    private boolean isCollection() {
        return attribute.getType().isAssignableFrom(Collection.class);
    }

    private boolean isPrimitive() {
        return PRIMITIVE_TYPES.contains(attribute.getType());
    }

    private String formatExpectedStringRepresentation(Object actualObject) {
        return String.format("%s=%s", attribute.getName(), get(actualObject));
    }

    private Object get(Object actualObject) {
        Object value = null;
        try {
            value = attribute.get(actualObject);
        } catch (IllegalAccessException e) {
            fail(String.format("Field \"%s\" of class \"%s\" is not accessible", attribute.getName(), attribute.getDeclaringClass().getSimpleName()));
        }
        return value;
    }
}
