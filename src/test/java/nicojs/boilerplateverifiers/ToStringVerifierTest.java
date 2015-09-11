package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.examples.tostring.PojoWithToString;
import nicojs.boilerplateverifiers.examples.tostring.PojoWithoutToString;
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
    public void verify_pojoWhichDoesNotImplementToString_fails(){
        assertError(PojoWithoutToString.class, "Could not find string representation for field \"aString\" (declared in class \"PojoWithoutToString\")");
    }

    @Test
    public void verify_pojoWithToString_passes(){
        ToStringVerifier.forClass(PojoWithToString.class).verify();
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
