package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.examples.entities.AllAttributesBeingUsedExcept;
import nicojs.boilerplateverifiers.examples.errors.*;
import nicojs.boilerplateverifiers.examples.lombok.*;
import nicojs.boilerplateverifiers.examples.manual.BuilderClassWithAdditionalMethod;
import nicojs.boilerplateverifiers.examples.manual.ClassWithBuilderPrefix;
import nicojs.boilerplateverifiers.examples.manual.ClassWithWeirdGetter;
import nicojs.boilerplateverifiers.examples.manual.SubClass;
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
    public void verify_classWithBooleansWronglyAssigned_failsSometimes() {
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
    public void verify_classWithCollectionsAndLombokGeneratedBuilder_passes() {
        BuilderVerifier.forClass(CollectionContainer.class).verify();
    }

    @Test
    public void verify_classWithCollectionsAndWrongAssignmentInBuilder_fails() {
        assertTwoAttributesMixedUp(ErrorCollectionContainer.class, "man", "women");
    }

    @Test
    public void verify_classWithMapsAndLombokGeneratedBuilder_passes() {
        BuilderVerifier.forClass(MapContainer.class).verify();
    }

    @Test
    public void verify_classWithMapsAndWrongAssignmentInBuilder_fails() {
        assertTwoAttributesMixedUp(ErrorMapContainer.class, "map", "hashMap");
    }

    @Test
    public void verify_classWithSetsAndLombokGeneratedBuilder_passes() {
        BuilderVerifier.forClass(SetContainer.class).verify();
    }

    @Test
    public void verify_classWithSetsAndWrongAssignment_fails() {
        assertTwoAttributesMixedUp(ErrorSetContainer.class, "set", "hashSet");
    }

    @Test
    public void verify_classWithQueuesAndLombokGeneratedBuilder_passes() {
        BuilderVerifier.forClass(QueueContainer.class).verify();
    }

    @Test
    public void verify_classWithQueuesWrongAssignment_fails() {
        assertError(ErrorQueueContainer.class, "");
    }

    @Test
    public void verify_withDifferentBuilderName_fails() {
        assertError(Employee.class, "No method found called \"builder\", did you call it differently?");
    }

    @Test
    public void verify_withInheritance_passes() {
        BuilderVerifier.forClass(Employee.class)
                .usingBuilderMethod("buildEmployee")
                .verify();
    }

    @Test
    public void verify_allPrimitiveAttributesWithLombok_passes() {
        BuilderVerifier.forClass(PrimitiveBag.class)
                .verify();
    }

    @Test
    public void verify_classWithFinalComplexAttributeAndLombokBuilder_passes() {
        BuilderVerifier.forClass(FinalAttribute.class)
                .verify();
    }

    @Test
    public void verify_classWithComplexAttributeWhichHasAStaticFieldAndLombokBuilder_passes() {
        BuilderVerifier.forClass(ClassWithAttributeWichHasStaticField.class)
                .verify();
    }

    @Test
    public void verify_classWhichDoesNotBuildAllAttributes_fails() {
        assertError(NotAllAtributesCanBeBuild.class, "Missing build method for field \"attribute3\"");
    }

    @Test
    public void verify_classWhichIsNotBuildingAllAttributes_fails() {
        assertError(NotBuildingAllAttributes.class, "Missing build method for field \"notBuildableString\" (declared in class \"NotBuildingAllAttributes\"), use allAttributesShouldBeBuildExcept(\"notBuildableString\") to specify that this attribute should not be build");
    }

    @Test
    public void verify_classWhichDoNotLetYouBuildParentAttributes_fails() {
        BuilderVerifier builder = BuilderVerifier.forClass(EmployeeCannotBuildParentAttributes.class).usingBuilderMethod("builderEmployee");
        assertError(builder, "Missing build method for field \"age\" (declared in class \"Person\"), use allAttributesShouldBeBuildExcept(\"age\") or withoutBuildingSuperClasses() to specify that this attribute should not be build.");
    }

    @Test
    public void verify_builderAccessorDoesNotReturnBuilderInstance_fails() {
        assertError(IncorrectBuilderPropertyAccessorReturnType.class, "Builder method for \"theAttribute\" does not return the instance of \"IncorrectBuilderPropertyAccessorReturnTypeBuilder\". Add 'return this' as a final statement of the method.");
    }

    @Test
    public void verify_allAttributesBeingUsedExcept_passes() {
        BuilderVerifier.forClass(AllAttributesBeingUsedExcept.class)
                .allAttributesShouldBeBuildExcept("var2", "var3")
                .verify();
    }

    @Test
    public void verify_builderClassWithInvalidMethod_fails() {
        assertError(BuilderClassWithAdditionalMethod.class, "Method \"doSomething\" on builder class should accept exactly one parameter");
    }

    @Test
    public void verify_allMethodsOnBuilderClassShouldBeUsedExcept_passes() {
        BuilderVerifier.forClass(BuilderClassWithAdditionalMethod.class)
                .allMethodsOnBuilderClassShouldBeUsedExcept("doSomething")
                .verify();
    }

    @Test
    public void verify_withoutUsingGettersForVerification_passes() {
        BuilderVerifier.forClass(ClassWithWeirdGetter.class)
                .withoutUsingGettersForVerification()
                .verify();
    }

    @Test
    public void verify_withClassWithWrongAssignmentButBrokenCustomValueFactory_passes() {
        final Person person = Person.builder().build();
        BuilderVerifier.forClass(Couple.class).withValueFactories(new ValueFactory<Person>(Person.class) {
            @Override
            public Person next() {
                return person;
            }
        }).verify();
    }

    @Test
    public void verify_withoutBuildingSuperClass_passes() {
        BuilderVerifier.forClass(SubClass.class)
                .withoutBuildingSuperClasses()
                .verify();
    }

    @Test
    public void verify_classWithBuilderMethodNotStatic_fails() {
        assertError(ClassWithBuilderMethodNotStatic.class, "Method \"builder\" of class \"ClassWithBuilderMethodNotStatic\" is not declared static.");
    }

    @Test
    public void verify_classWithABuilderWithPrefix_passes() {
        BuilderVerifier.forClass(ClassWithBuilderPrefix.class)
                .withPrefixForAllMethodsOnBuilder("with")
                .verify();
    }

    @Test
    public void verify_classWithABuilderWithPrefixButWrongPrefixSupplied_fails() {
        assertError(BuilderVerifier.forClass(ClassWithBuilderPrefix.class).withPrefixForAllMethodsOnBuilder("prefix"),
                "Expected method \"withString\" on builder class \"ClassWithBuilderPrefixBuilder\" to begin with prefix \"prefix\", but it did not. Please use \"allMethodsOnBuilderClassShouldBeUsedExcept(\"withString\") if this method should be ignored.\"");
    }

    @Test
    public void verify_classWithABuilderWithPrefixButMethodIsIgnored_passes() {
        BuilderVerifier.forClass(ClassWithBuilderPrefix.class)
                .allMethodsOnBuilderClassShouldBeUsedExcept("withString")
                .allAttributesShouldBeBuildExcept("string")
                .verify();
    }

    @Test
    public void verify_builderConstructorPublic_fails(){
        assertError(BuilderConstructorPublic.class, "The constructor for builder \"BuilderConstructorPublicBuilder\" should not be declared public.");
    }

    @Test
    public void verify_nonFinalAttribute_fails() {
        assertError(NonFinalAttribute.class, "Field \"notFinal\" of class \"NonFinalAttribute\" is not declared final.");
    }

    private void assertError(Class clazz, String expectedSubstring) {
        BuilderVerifier builderVerifier = BuilderVerifier.forClass(clazz);
        assertError(builderVerifier, expectedSubstring);
    }

    private void assertError(BuilderVerifier builderVerifier, String expectedSubstring) {
        boolean caught = false;
        try {
            builderVerifier.verify();
        } catch (AssertionError error) {
            assertThat(error.getMessage(), containsString(expectedSubstring));
            caught = true;
        }
        assertThat(caught, is(true));
    }

    private void assertTwoAttributesMixedUp(final Class<?> clazz, final String first, final String second) {
        boolean caught = false;
        try {
            BuilderVerifier.forClass(clazz).verify();
        } catch (AssertionError error) {
            caught = true;
            assertThat(error.getMessage(), containsString("Value used to build was not equal to value after build for property"));
            boolean containsMap = error.getMessage().contains("\"" + first + "\"");
            boolean containsHashMap = error.getMessage().contains("\"" + second + "\"");
            assertThat(containsHashMap || containsMap, is(true));
        }
        assertThat(caught, is(true));
    }

}