package nicojs.boilerplateverifiers.internals.tostring;

import java.lang.reflect.Field;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Represents a AttributeAccessor
 * Created by nicojs
 */
public class AttributeAccessor {
    private final Field attribute;

    public AttributeAccessor(Field attribute){
        this.attribute = attribute;
        attribute.setAccessible(true);
    }

    public void verify(Object actualObject, String actualStringRepresentation) {
        final String expectedStringRepresentation = formatExpectedStringRepresentation(actualObject);
        assertThat(String.format("Could not find string representation for field \"%s\" (declared in class \"%s\").", attribute.getName(), attribute.getDeclaringClass().getSimpleName()),
                actualStringRepresentation, containsString(expectedStringRepresentation));
    }

    private String formatExpectedStringRepresentation(Object actualObject) {
        String expectedStringRepresentation = null;
        try {
            expectedStringRepresentation = String.format("%s=%s", attribute.getName(), attribute.get(actualObject));
        } catch (IllegalAccessException e) {
            fail(String.format("Field \"%s\" of class \"%s\" is not accessible", attribute.getName(), attribute.getDeclaringClass().getSimpleName()));
        }
        return expectedStringRepresentation;
    }
}
