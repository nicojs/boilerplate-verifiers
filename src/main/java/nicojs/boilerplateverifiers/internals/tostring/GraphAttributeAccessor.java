package nicojs.boilerplateverifiers.internals.tostring;

import lombok.EqualsAndHashCode;
import lombok.Getter;
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
    private final Object nodeValue;
    @Getter
    private final String path;
    private GraphNodeAccessor nodeAccessor;

    public GraphAttributeAccessor(Field attribute, @NonNull Object nodeValue, @NonNull String currentPath, GraphAccessorCreationContext context) {
        this.attribute = attribute;
        this.nodeValue = nodeValue;
        if ("".equals(currentPath)) {
            this.path = attribute.getName();
        } else {
            this.path = String.format("%s.%s", currentPath, attribute.getName());
        }
        if (isNode() && context.shouldInspect(getValue())) {
            nodeAccessor = new GraphNodeAccessor(attribute.getType(), getValue(), path, context);
        }
    }

    public static List<GraphAttributeAccessor> forAttributes(Class targetClass, Object nodeValue, String currentPath, GraphAccessorCreationContext context) {
        List<GraphAttributeAccessor> attributes = new ArrayList<>();
        for (Field field : targetClass.getDeclaredFields()) {
            if (isValid(field)) {
                attributes.add(new GraphAttributeAccessor(field, nodeValue, currentPath, context));
            }
        }
        return attributes;
    }

    private static boolean isValid(Field field) {
        return !Modifier.isStatic(field.getModifiers());
    }

    public void verify(String actualStringRepresentation) {
        final Object actualValue = getValue();
        if (isNode()) {
            if (nodeAccessor != null) {
                nodeAccessor.verifyAttributes(actualStringRepresentation);
            }
        } else {
            final String expectedStringRepresentation = formatExpectedStringRepresentation(actualValue);
            assertThat(String.format("Could not find string representation for field \"%s\" (declared in class \"%s\"). Path to this field is \"%s\".", attribute.getName(), attribute.getDeclaringClass().getSimpleName(), path),
                    actualStringRepresentation, containsString(expectedStringRepresentation));
        }
    }

    private boolean isNode() {
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

    private Object getValue() {
        attribute.setAccessible(true);
        Object value = null;
        try {
            value = attribute.get(nodeValue);
        } catch (IllegalAccessException e) {
            fail(String.format("Field \"%s\" of class \"%s\" is not accessible", attribute.getName(), attribute.getDeclaringClass().getSimpleName()));
        }
        return value;
    }

    public List<String> remove(List<String> paths) {
        if (nodeAccessor != null) {
            return nodeAccessor.remove(paths);
        } else {
            return paths;
        }
    }

    public List<String> retrievePaths() {
        List<String> paths = new ArrayList<>();
        paths.add(path);
        if(nodeAccessor != null){
            paths.addAll(nodeAccessor.retrievePaths());
        }
        return paths;
    }
}
