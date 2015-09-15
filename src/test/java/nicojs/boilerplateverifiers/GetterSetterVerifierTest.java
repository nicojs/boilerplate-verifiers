package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.gettersetter.checks.referenceclasses.ThePerfectClass;
import org.junit.Test;

public class GetterSetterVerifierTest {
    @Test(expected = AssertionError.class)
    public void shouldFailValidation() {
        GetterSetterVerifier
                .forClass(RandomGetter.class)
                .verify();
    }

    @Test
    public void givenThePerfectClass_whenVerified_verificationShouldSucceed() {
        GetterSetterVerifier
                .forClass(ThePerfectClass.class)
                .verify();
    }

    class RandomGetter {
        private String something;
        private String somethingElse;

        public String getCrap() {
            return something;
        }
    }
}