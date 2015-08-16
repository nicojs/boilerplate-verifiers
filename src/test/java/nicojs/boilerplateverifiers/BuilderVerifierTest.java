package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.examples.errors.BooleansWronglyAssigned;
import nicojs.boilerplateverifiers.examples.errors.Couple;
import nicojs.boilerplateverifiers.examples.errors.ErrorCollectionContainer;
import nicojs.boilerplateverifiers.examples.errors.ErrorMapContainer;
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
        BuilderVerifier.of(Person.class).verify();
    }

    @Test
    public void verify_complexClassWithLombokGeneratedBuilder_passes() {
        BuilderVerifier.of(Book.class).verify();
    }

    @Test
    public void verify_classWithoutGetters_passes() {
        BuilderVerifier.of(NoGetter.class).verify();
    }

    @Test
    public void verify_complexClassWithWrongAssignment_fails() {
        boolean caught = false;
        try {
            BuilderVerifier.of(Couple.class).verify();
        } catch (AssertionError error) {
            assertThat(error.getMessage(), containsString("Value used to build was not equal to value after build for property \"man\""));
            caught = true;
        }
        assertThat(caught, is(true));
    }

    @Test
    public void verify_classWithEnumWithLombokGeneratedBuilder_passes() {
        BuilderVerifier.of(Switch.class).verify();
    }

    @Test
    public void verify_classWithEnumsWronglyAssignedAndTooLittleEnumValuesToFailEveryTime_failsSometimes() {
        int numberOfTimesCaught = 0;
        for (int i = 0; i < 100; i++) {
            try {
                BuilderVerifier.of(Switches.class).verify();
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
                BuilderVerifier.of(BooleansWronglyAssigned.class).verify();
            } catch (AssertionError error) {
                assertThat(error.getMessage(), containsString("Value used to build was not equal to value after build for property"));
                numberOfTimesCaught++;
            }
        }
        assertThat(numberOfTimesCaught, is(greaterThan(0)));
    }

    @Test
    public void verify_classWithCollectionsAndLombokGeneratedBuilder_passes(){
        BuilderVerifier.of(CollectionContainer.class).verify();
    }

    @Test
    public void verify_classWithCollectionsAndWrongAssignmentInBuilder_fails(){
        boolean caught = false;
        try{
            BuilderVerifier.of(ErrorCollectionContainer.class).verify();
        }catch(AssertionError error){
            caught = true;
            assertThat(error.getMessage(), containsString("Value used to build was not equal to value after build for property \"man\""));
        }
        assertThat(caught, is(true));
    }

    @Test
    public void verify_classWithMapsAndLombokGeneratedBuilder_passes(){
        BuilderVerifier.of(MapContainer.class).verify();
    }

    @Test
    public void verify_classWithMapsAndWrongAssignmentInBuilder_fails(){
        boolean caught = false;
        try{
            BuilderVerifier.of(ErrorMapContainer.class).verify();
        }catch(AssertionError error){
            caught = true;
            assertThat(error.getMessage(), containsString("Value used to build was not equal to value after build for property \"map\""));
        }
        assertThat(caught, is(true));
    }


    @Test
    public void verify_withDifferentBuilderName_fails(){
        try{
            BuilderVerifier.of(Employee.class).verify();
        }catch (AssertionError error){
            assertThat(error.getMessage(), is("No method found called \"builder\", did you call it differently?"));
        }
    }

    @Test
    public void verify_withInheritance_passes(){
        BuilderVerifier.of(Employee.class)
                .usingBuilderMethod("buildEmployee")
                .verify();
    }

    @Test
    public void verify_allPrimitiveAttributesWithLombok_passes(){
        BuilderVerifier.of(PrimitiveBag.class)
                .verify();
    }

    @Test
    public void verify_classWithFinalComplexAttributeAndLombokBuilder_passes(){
        BuilderVerifier.of(FinalAttribute.class)
                .verify();
    }

    @Test
    public void verify_classWithComplexAttributeWhichHasAStaticFieldAndLombokBuilder_passes(){
        BuilderVerifier.of(ClassWithAttributeWichHasStaticField.class)
                .verify();
    }
}