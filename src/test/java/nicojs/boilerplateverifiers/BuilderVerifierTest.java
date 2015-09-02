package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.examples.entities.AllAttributesBeingUsedExcept;
import nicojs.boilerplateverifiers.examples.errors.*;
import nicojs.boilerplateverifiers.examples.lombok.*;
import nicojs.boilerplateverifiers.examples.manual.BuilderClassWithAdditionalMethod;
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
        assertTwoAttributesMixedUp(Couple.class, "woman", "man");
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

    @Test
    public void verify_classWhichDoNotLetYouBuildParentAttributes_fails(){
        BuilderVerifier builder = BuilderVerifier.forClass(EmployeeCannotBuildParentAttributes.class).usingBuilderMethod("builderEmployee");
        assertError(builder, "Missing build method for field \"age\" (declared in class \"Person\"), use allAttributesShouldBeBuildExcept to ignore this attribute if this is by design.");
    }

    @Test
    public void verify_builderAccessorDoesNotReturnBuilderInstance_fails(){
        assertError(IncorrectBuilderPropertyAccessorReturnType.class, "Builder method for \"theAttribute\" does not return the instance of \"IncorrectBuilderPropertyAccessorReturnTypeBuilder\". Add 'return this' as a final statement of the method.");
    }

    @Test
    public void verify_allAttributesBeingUsedExcept_passes(){
        BuilderVerifier.forClass(AllAttributesBeingUsedExcept.class)
                .allAttributesShouldBeBuildExcept("var2", "var3")
                .verify();
    }

    @Test
    public void verify_builderClassWithInvalidMethod_fails(){
        assertError(BuilderClassWithAdditionalMethod.class, "Method \"doSomething\" on builder class should accept exactly one parameter");
    }

    @Test
    public void verify_allBuilderMethodsUsedExcept_passes(){
        BuilderVerifier.forClass(BuilderClassWithAdditionalMethod.class)
                .allMethodsOnBuilderClassShouldBeUsedExcept("doSomething")
                .verify();
    }

    private void assertError(Class clazz, String expectedSubstring) {
        BuilderVerifier builderVerifier = BuilderVerifier.forClass(clazz);
        assertError(builderVerifier, expectedSubstring);
    }

    private void assertError(BuilderVerifier builderVerifier, String expectedSubstring) {
        boolean caught = false;
        try{
            builderVerifier.verify();
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