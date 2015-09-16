package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.gettersetter.checks.Validations;
import nicojs.boilerplateverifiers.gettersetter.checks.referenceclasses.ThePerfectClass;
import nicojs.boilerplateverifiers.gettersetter.exceptions.GetterSetterVerificationException;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Test
    @Ignore("Work in progress")
    public void givenListsOfClassesAndSettings_whenVerified_noUnexpectedExceptionsShouldBeThrown() {
        List<Class<?>> classList = Arrays.asList(ThePerfectClass.class, RandomGetter.class);
        Set<Set<Validations>> validationsPowerSet = collectionValidationsPowerSet();
        //TODO add fields loop

        for (Class<?> classToTest : classList) {
            for (Set<Validations> combination : validationsPowerSet) {
                Validations[] validationSetArray = combination.toArray(new Validations[combination.size()]);
                try {
                    GetterSetterVerifier.forClass(classToTest).excludeChecks(validationSetArray).verify();
                } catch (GetterSetterVerificationException ex) {
                    //nothing, is expected error for some of the given classes
                } catch (Throwable ex) {
                    throw new RuntimeException(String.format("Unexpected exception occured while testing class: %s with excluded checks: %s", classToTest, combination), ex);
                }
            }
        }
    }

    private Set<Set<Validations>> collectionValidationsPowerSet() {
        HashSet<Validations> validationsSet = new HashSet<>(Arrays.asList(Validations.values()));
        return TestHelper.powerSet(validationsSet);
    }

    class RandomGetter {
        private String something;
        private String somethingElse;

        public String getCrap() {
            return something;
        }
    }
}