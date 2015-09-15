package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.VerificationContextBuilder;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SetMethodShouldReferenceFieldTest {
    private SetMethodShouldReferenceField sut = new SetMethodShouldReferenceField();

    @Test
    public void ifClassHasSetMethodThatDoesntReferenceAFieldVerificationFails() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(LetsUseSetForEveryMethod.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(false));
        assertThat(verificationResult.getMessage(), containsString("setSomething"));
    }

    @Test
    public void ifClassHasSetMethodThatReferencesAFieldVerificationSucceeds() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(ValidSetUsage.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(true));
    }

    @Test
    public void ifClassHasNoSetMethodVerificationSucceeds() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(ValidGetUsage.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(true));
    }


    class LetsUseSetForEveryMethod {
        public void setSomething(String set) {

        }
    }

    class ValidGetUsage {
        private String something;

        public String getSomething() {
            return something;
        }
    }

    class ValidSetUsage {
        private String something;

        public void setSomething(String something) {
            this.something = something;
        }
    }
}