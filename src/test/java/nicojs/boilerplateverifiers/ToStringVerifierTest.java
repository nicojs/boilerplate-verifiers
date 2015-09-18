package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.examples.tostring.*;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test class for {@link ToStringVerifier}
 * Created by nicojs
 */
public class ToStringVerifierTest {

    @Test
    public void verify_pojoWhichDoesNotImplementToString_fails() {
        assertError(PojoWithoutToString.class, "Could not find string representation for field \"aString\" (declared in class \"PojoWithoutToString\")");
    }

    @Test
    public void verify_pojoWithToString_passes() {
        ToStringVerifier.forClass(PojoWithToString.class).verify();
    }

    @Test
    public void verify_simpleGraphNodeDoesNotImplementToString_fails() {
        assertError(GraphWithToStringAndChildWithoutToString.class, "Could not find string representation for field \"aString\" (declared in class \"PojoWithoutToString\")");
    }

    @Test
    public void verify_nodeTreeWithoutRecursiveToString_fails() {
        assertError(NodeWithoutRecursiveToString.class,
                "Could not find string representation for field \"name\" (declared in class \"NodeWithoutRecursiveToString\"). Path to this field is \"parent.name\".");
    }

    @Test
    public void verify_nodeTreeWithSmartRecursiveToString_passes(){
        ToStringVerifier.forClass(NodeWithSmartRecursiveToString.class).verify();
    }

    private void assertError(Class targetClass, String expectedSubstring) {
        boolean caught = false;
        try {
            ToStringVerifier.forClass(targetClass).verify();
        } catch (AssertionError error) {
            assertThat(error.getMessage(), containsString(expectedSubstring));
            caught = true;
        }
        assertThat(caught, is(true));
    }

}
