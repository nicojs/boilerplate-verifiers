package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.VerificationContextBuilder;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SetterShouldOnlyAffectReferencedFieldTest {
    private SetterShouldOnlyAffectReferencedField sut = new SetterShouldOnlyAffectReferencedField();

    @Test
    public void givenClassWithValidSetter_whenValidationIsExecuted_returnsSuccess() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(ValidSetUsage.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(true));
    }

    @Test
    public void givenClassWithSetterThatChangesOtherField_whenValidationIsExecuted_returnsFailure() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(SetterChangesOtherField.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(false));
        assertThat(verificationResult.getMessage(), containsString("setSomething"));
    }

    class ValidSetUsage {
        private String something;

        public void setSomething(String something) {
            this.something = something;
        }
    }

    class SetterChangesOtherField {
        private String something;
        private String somethingElse;

        public void setSomething(String something) {
            this.somethingElse = something;
        }
    }

}