package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.examples.errors.BooleansWronglyAssigned;
import nicojs.boilerplateverifiers.examples.errors.Couple;
import nicojs.boilerplateverifiers.examples.errors.ErrorCollectionContainer;
import nicojs.boilerplateverifiers.examples.errors.ErrorMapContainer;
import nicojs.boilerplateverifiers.examples.errors.ErrorQueueContainer;
import nicojs.boilerplateverifiers.examples.errors.ErrorSetContainer;
import nicojs.boilerplateverifiers.examples.errors.NotAllAtributesCanBeBuild;
import nicojs.boilerplateverifiers.examples.errors.Switches;
import nicojs.boilerplateverifiers.examples.lombok.*;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Represents a BuilderVerifierTest
 * Created by nicojs on 8/12/2015.
 */
public class BuilderVerifierTest {

    @Test
    public void verify_flatClassWithLombokGeneratedBuilder_passes() {
        BuilderVerifier.forClass(Person.class).verify();
    }

    @Test
    public void verify_complexClassWithLombokGeneratedBuilder_passes() {
        BuilderVerifier.forClass(Book.class).verify();
    }

    @Test
    public void verify_classWithoutGetters_passes() {
        BuilderVerifier.forClass(NoGetter.class).verify();
    }

    @Test
    public void verify_complexClassWithWrongAssignment_fails() {
        boolean caught = false;
        try {
            BuilderVerifier.forClass(Couple.class).verify();
        } catch (AssertionError error) {
            assertThat(error.getMessage(), containsString("Value used to build was not equal to value after build for property \"man\""));
            caught = true;
        }
        assertThat(caught, is(true));
    }

    @Test
    public void verify_classWithEnumWithLombokGeneratedBuilder_passes() {
        BuilderVerifier.forClass(Switch.class).verify();
    }

    @Test
    public void verify_classWithEnumsWronglyAssignedAndTooLittleEnumValuesToFailEveryTime_failsSometimes() {
        int numberOfTimesCaught = 0;
        for (int i = 0; i < 100; i++) {
            try {
                BuilderVerifier.forClass(Switches.class).verify();
            } catch (AssertionError error) {
                assertThat(error.getMessage(), containsString("Value used to build was not equal to value after build for property \"switch"));
                numberOfTimesCaught++;
            }
        }
        assertThat(numberOfTimesCaught, is(greaterThan(0)));
    }

    @Test
    public void verify_classWithBooleansWronglyAssigned_failsSometimes(){
        int numberOfTimesCaught = 0;
        for (int i = 0; i < 100; i++) {
            try {
                BuilderVerifier.forClass(BooleansWronglyAssigned.class).verify();
            } catch (AssertionError error) {
                assertThat(error.getMessage(), containsString("Value used to build was not equal to value after build for property"));
                numberOfTimesCaught++;
            }
        }
        assertThat(numberOfTimesCaught, is(greaterThan(0)));
    }

    @Test
    public void verify_classWithCollectionsAndLombokGeneratedBuilder_passes(){
        BuilderVerifier.forClass(CollectionContainer.class).verify();
    }

    @Test
    public void verify_classWithCollectionsAndWrongAssignmentInBuilder_fails(){
        assertTwoAttributesMixedUp(ErrorCollectionContainer.class, "man", "women");
    }

    @Test
    public void verify_classWithMapsAndLombokGeneratedBuilder_passes(){
        BuilderVerifier.forClass(MapContainer.class).verify();
    }

    @Test
    public void verify_classWithMapsAndWrongAssignmentInBuilder_fails(){
        assertTwoAttributesMixedUp(ErrorMapContainer.class, "map", "hashMap");
    }

    @Test
    public void verify_classWithSetsAndLombokGeneratedBuilder_passes(){
        BuilderVerifier.forClass(SetContainer.class).verify();
    }

    @Test
    public void verify_classWithSetsAndWrongAssignment_fails(){
        assertTwoAttributesMixedUp(ErrorSetContainer.class, "set", "hashSet");
    }

    @Test
    public void verify_classWithQueuesAndLombokGeneratedBuilder_passes(){
        BuilderVerifier.forClass(QueueContainer.class).verify();
    }

    @Test
    public void verify_classWithQueuesWrongAssignment_fails(){
        assertError(ErrorQueueContainer.class, "");
    }

    @Test
    public void verify_withDifferentBuilderName_fails(){
        assertError(Employee.class, "No method found called \"builder\", did you call it differently?");
    }

    @Test
    public void verify_withInheritance_passes(){
        BuilderVerifier.forClass(Employee.class)
                .usingBuilderMethod("buildEmployee")
                .verify();
    }

    @Test
    public void verify_allPrimitiveAttributesWithLombok_passes(){
        BuilderVerifier.forClass(PrimitiveBag.class)
                .verify();
    }

    @Test
    public void verify_classWithFinalComplexAttributeAndLombokBuilder_passes(){
        BuilderVerifier.forClass(FinalAttribute.class)
                .verify();
    }

    @Test
    public void verify_classWithComplexAttributeWhichHasAStaticFieldAndLombokBuilder_passes(){
        BuilderVerifier.forClass(ClassWithAttributeWichHasStaticField.class)
                .verify();
    }

    @Test
    public void verify_classWhichDoesNotBuildAllAttributes_fails(){
        assertError(NotAllAtributesCanBeBuild.class, "Missing build method for field \"attribute3\"");
    }

    private void assertError(Class clazz, String expectedSubstring) {
        boolean caught = false;
        try{
            BuilderVerifier.forClass(clazz).verify();
        }catch(AssertionError error){
            assertThat(error.getMessage(), containsString(expectedSubstring));
            caught = true;
        }
        assertThat(caught, is(true));
    }

    private void assertTwoAttributesMixedUp(final Class<?> clazz, final String first, final String second) {
        boolean caught = false;
        try{
            BuilderVerifier.forClass(clazz).verify();
        }catch(AssertionError error){
            caught = true;
            assertThat(error.getMessage(), containsString("Value used to build was not equal to value after build for property"));
            boolean containsMap = error.getMessage().contains("\"" + first + "\"");
            boolean containsHashMap = error.getMessage().contains("\"" + second + "\"");
            assertThat(containsHashMap || containsMap, is(true));
        }
        assertThat(caught, is(true));
    }

}