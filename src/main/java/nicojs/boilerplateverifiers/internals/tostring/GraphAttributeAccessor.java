package nicojs.boilerplateverifiers.internals.tostring;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Represents a GraphAttributeAccessor
 * Created by nicojs
 */
@EqualsAndHashCode
public class GraphAttributeAccessor {
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
    private final String path;

    public GraphAttributeAccessor(Field attribute, @NonNull String currentPath) {
        this.attribute = attribute;
        if ("".equals(currentPath)) {
            this.path = attribute.getName();
        } else {
            this.path = String.format("%s.%s", currentPath, attribute.getName());
        }
    }

    public static List<GraphAttributeAccessor> forClassAttributes(Class targetClass, String currentPath) {
        List<GraphAttributeAccessor> attributes = new ArrayList<>();
        for (Field field : targetClass.getDeclaredFields()) {
            if (isValid(field)) {
                attributes.add(new GraphAttributeAccessor(field, currentPath));
            }
        }
        return attributes;
    }

    private static boolean isValid(Field field) {
        return !Modifier.isStatic(field.getModifiers());
    }

    public void verify(Object actualObject, String actualStringRepresentation, VerificationContext context) {
        final Object actualValue = get(actualObject);
        if(!context.shouldBeIgnored(actualValue, path)) {
            if (isComplex() && actualValue != null) {
                new GraphAccessor(attribute.getType(), path).verifyAttributes(actualValue, actualStringRepresentation, context);
            } else {
                final String expectedStringRepresentation = formatExpectedStringRepresentation(actualValue);
                assertThat(String.format("Could not find string representation for field \"%s\" (declared in class \"%s\"). Path to this field is \"%s\".", attribute.getName(), attribute.getDeclaringClass().getSimpleName(), path),
                        actualStringRepresentation, containsString(expectedStringRepresentation));
            }
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

    private String formatExpectedStringRepresentation(Object value) {
        return String.format("%s=%s", attribute.getName(), value);
    }

    private Object get(Object actualObject) {
        attribute.setAccessible(true);
        Object value = null;
        try {
            value = attribute.get(actualObject);
        } catch (IllegalAccessException e) {
            fail(String.format("Field \"%s\" of class \"%s\" is not accessible", attribute.getName(), attribute.getDeclaringClass().getSimpleName()));
        }
        return value;
    }
}
