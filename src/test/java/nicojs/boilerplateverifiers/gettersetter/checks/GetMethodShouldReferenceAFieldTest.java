package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.VerificationContextBuilder;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class GetMethodShouldReferenceAFieldTest {

    private GetMethodShouldReferenceAField sut = new GetMethodShouldReferenceAField();

    @Test
    public void ifClassHasGetMethodThatDoesntReferenceAFieldVerificationFails() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(LetsUseGetForEveryMethod.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(false));
        assertThat(verificationResult.getMessage(), containsString("getSomething"));
    }

    @Test
    public void ifClassHasGetMethodThatReferencesAFieldVerificationSucceeds() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(ValidGetUsage.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(true));
    }

    @Test
    public void ifClassHasNoGetMethodThatReferencesAFieldVerificationSucceeds() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(ValidSetUsage.class).build();

        VerificationResult verificationResult = sut.execute(context);
        assertThat(verificationResult.isSuccess(), is(true));
    }


    class LetsUseGetForEveryMethod {
        public String getSomething() {
            return "";
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