package nicojs.boilerplateverifiers.gettersetter;

import org.junit.Test;

import java.beans.IntrospectionException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ContextProviderTest {

    @Test
    public void givenValidClass_whenFieldsHaveBeenFiltered_excludesDefaultClassFields() {
        List<String> defaultClassFields = Arrays.asList("class");
        VerificationContextBuilder sut = VerificationContextBuilder.forClass(ValidGetterSetterUsage.class);

        Fields fieldsToTest = sut.determineFieldsToTest();

        for (String field : defaultClassFields) {
            assertThat(fieldsToTest.containsKey(field), is(false));
        }
    }

    @Test
    public void givenValidClass_whenFieldsHaveBeenFiltered_excludesDefaultInnerClassFields() {
        List<String> defaultInnerClassFields = Arrays.asList("this$0");
        VerificationContextBuilder sut = VerificationContextBuilder.forClass(ValidGetterSetterUsage.class);

        Fields fieldsToTest = sut.determineFieldsToTest();

        for (String field : defaultInnerClassFields) {
            assertThat(fieldsToTest.containsKey(field), is(false));
        }
    }

    @Test
    public void givenValidClass_whenMethodsHaveBeenFiltered_excludesDefaultClassMethods() throws IntrospectionException {
        List<String> defaultClassFields = Arrays.asList("getClass");
        VerificationContextBuilder sut = VerificationContextBuilder.forClass(ValidGetterSetterUsage.class);

        Methods methodsToTest = sut.determineMethodsToTest();

        for (String method : defaultClassFields) {
            assertThat(methodsToTest.containsKey(method), is(false));
        }
    }

    @Test
    public void givenValidClass_whenFieldsHaveBeenExcluded_fieldsAreNotPresentInTheFieldsToTest() {
        String excluded = "somethingElse";
        VerificationContextBuilder sut = VerificationContextBuilder.forClass(ValidGetterSetterUsage.class);
        sut.excludeField(excluded);

        Fields fieldsToTest = sut.determineFieldsToTest();
        assertThat(fieldsToTest.hasFieldWithName(excluded), is(false));
    }

    @Test
    public void givenValidClass_whenFieldsHaveBeenExcluded_fieldsGettersOrSettersAreNotPresentInTheMethodsToTest() {
        String excluded = "somethingElse";
        VerificationContextBuilder sut = VerificationContextBuilder.forClass(ValidGetterSetterUsage.class);
        sut.excludeField(excluded);

        Methods methodsToTest = sut.determineMethodsToTest();
        assertThat(methodsToTest.hasMethodWithName("getSomethingElse"), is(false));
        assertThat(methodsToTest.hasMethodWithName("setSomethingElse"), is(false));
    }

    @Test
    public void givenValidClass_whenContextIsCreated_canGetNewEmtpyInstanceOfGivenClass() {
        GetSetVerificationContext<ValidGetterSetterUsage> sut = VerificationContextBuilder.forClass(ValidGetterSetterUsage.class).build();
        ValidGetterSetterUsage emptyInstance = sut.newEmptyInstance();

        assertThat(emptyInstance.getSomething(), is(nullValue()));
        assertThat(emptyInstance.getSomethingElse(), is(nullValue()));
    }

    @Test
    public void givenValidClass_whenContextIsCreated_canGetNewInitializedInstanceOfGivenClass() {
        GetSetVerificationContext<ValidGetterSetterUsage> sut = VerificationContextBuilder.forClass(ValidGetterSetterUsage.class).build();
        ValidGetterSetterUsage configuredInstance = sut.newConfiguredInstance();

        assertThat(configuredInstance.getSomething(), is(notNullValue()));
        assertThat(configuredInstance.getSomethingElse(), is(notNullValue()));
    }

    class ValidGetterSetterUsage {
        private String something;
        private String somethingElse;

        public String getSomething() {
            return something;
        }

        public String getSomethingElse() {
            return somethingElse;
        }

        public void setSomething(String something) {
            this.something = something;
        }

        public void setSomethingElse(String somethingElse) {
            this.somethingElse = somethingElse;
        }
    }

    class RandomGetter {
        private String something;
        private String somethingElse;

        public String getCrap() {
            return something;
        }
    }
}