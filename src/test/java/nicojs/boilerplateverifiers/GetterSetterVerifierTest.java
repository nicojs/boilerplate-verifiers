package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.gettersetter.checks.Validations;
import nicojs.boilerplateverifiers.gettersetter.checks.referenceclasses.ThePerfectClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

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
        HashSet<Validations> validationsSet = new HashSet<>(Arrays.asList(Validations.values()));
        Set<Set<Validations>> validationPowerSet = powerSet(validationsSet);

        for (Class<?> classToTest : classList) {
            for (Set<Validations> combination : validationPowerSet) {
                Validations[] validationSetArray = combination.toArray(new Validations[combination.size()]);
                try {
                    GetterSetterVerifier.forClass(classToTest).excludeChecks(validationSetArray).verify();
                } catch (AssertionError ex) {
                    //nothing, is expected error for some of the given classes
                } catch (Throwable ex) {
                    throw new RuntimeException(String.format("Unexpected exception occured while testing class: %s with excluded checks: %s", classToTest, combination), ex);
                }
            }
        }
    }


    public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        Set<Set<T>> sets = new HashSet<Set<T>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        List<T> list = new ArrayList<T>(originalSet);
        T head = list.get(0);
        Set<T> rest = new HashSet<T>(list.subList(1, list.size()));
        for (Set<T> set : powerSet(rest)) {
            Set<T> newSet = new HashSet<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }

    class RandomGetter {
        private String something;
        private String somethingElse;

        public String getCrap() {
            return something;
        }
    }
}