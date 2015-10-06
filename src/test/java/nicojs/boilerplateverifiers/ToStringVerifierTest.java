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
        assertError(GraphWithToStringAndNodeWithoutToString.class, "Could not find string representation for field \"aString\" (declared in class \"PojoWithoutToString\"). Path to this field is \"pojoWithoutToString.aString\"");
    }

    @Test
    public void verify_nodeTreeWithoutRecursiveToString_fails() {
        assertError(NodeWithoutRecursiveToString.class,
                "Could not find string representation for field \"name\" (declared in class \"NodeWithoutRecursiveToString\"). Path to this field is \"parent.name\".");
    }

    @Test
    public void verify_nodeTreeWithSmartRecursiveToString_passes() {
        ToStringVerifier.forClass(NodeWithSmartRecursiveToString.class).verify();
    }


    @Test
    public void verify_treeGraphWithRecursiveToStringAndGraphTreeWithLoops_fails() {
        assertError(NodeWithRecursiveToString.class,
                "The invocation of the toString resulted in a StackOverflow error. An object of type \"NodeWithRecursiveToString\" was created with a graph (structure of objects) which contain looping references");
    }

    @Test
    public void verify_treeGraphStrategyWithRecursiveToString_passes() {
        ToStringVerifier.forClass(NodeWithRecursiveToString.class)
                .withGraphStrategy(GraphStrategy.TREE)
                .verify();
    }

    @Test
    public void verify_inheritanceWithToString_passes() {
        ToStringVerifier.forClass(InheritanceWithToString.class)
                .verify();
    }

    @Test
    public void verify_inheritanceWithoutParentWithoutToString_fails() {
        assertError(InheritanceParentWithoutToString.class, "Could not find string representation for field \"aString\" (declared in class \"PojoWithoutToString\"). Path to this field is \"%super.aString\"");
    }

    @Test
    public void verify_useAllAttributesExceptWhenIgnoringCorrectPath_passes() {
        ToStringVerifier.forClass(GraphWithToStringAndNodeWithoutToString.class)
                .useAllAttributesExcept("pojoWithoutToString")
                .verify();
        ToStringVerifier.forClass(GraphWithToStringAndNodeWithoutToString.class)
                .useAllAttributesExcept("pojoWithoutToString.aString")
                .verify();
    }

    private void assertError(Class targetClass, String expectedSubstring) {
        assertError(ToStringVerifier.forClass(targetClass), expectedSubstring);
    }

    private void assertError(ToStringVerifier sut, String expectedSubstring) {
        boolean caught = false;
        try {
            sut.verify();
        } catch (AssertionError error) {
            assertThat(error.getMessage(), containsString(expectedSubstring));
            caught = true;
        }
        assertThat(caught, is(true));
    }

}
