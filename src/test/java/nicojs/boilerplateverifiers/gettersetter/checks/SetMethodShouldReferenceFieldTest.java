package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.VerificationContextBuilder;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.checks.examples.OnlyValidGetter;
import nicojs.boilerplateverifiers.gettersetter.checks.examples.OnlyValidSetter;
import nicojs.boilerplateverifiers.gettersetter.checks.examples.SetterThatDoenstReferenceField;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SetMethodShouldReferenceFieldTest {
    private SetMethodShouldReferenceField sut = new SetMethodShouldReferenceField();

    @Test
    public void ifClassHasSetMethodThatDoesntReferenceAFieldVerificationFails() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(SetterThatDoenstReferenceField.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(false));
        assertThat(verificationResult.getMessage(), containsString("setSomething"));
    }

    @Test
    public void ifClassHasSetMethodThatReferencesAFieldVerificationSucceeds() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(OnlyValidSetter.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(true));
    }

    @Test
    public void ifClassHasNoSetMethodVerificationSucceeds() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(OnlyValidGetter.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(true));
    }
}