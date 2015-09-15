package nicojs.boilerplateverifiers.gettersetter.checks;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.VerificationContextBuilder;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GetterShouldBePublicTest {
    private GetterShouldBePublic sut = new GetterShouldBePublic();

    @Test
    public void givenClassWithPublicGetters_validationIsRun_validationSuccess() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(PublicGetter.class).build();

        VerificationResult result = sut.execute(context);

        assertThat(result.isSuccess(), is(true));
    }

    @Test
    public void givenClassWithPrivateGetters_validationIsRun_validationFails() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(PrivateGetter.class).build();

        VerificationResult result = sut.execute(context);

        assertThat(result.isSuccess(), is(false));
        assertThat(result.getMessage(), containsString("getSomething"));
    }

    @Test
    public void givenClassWithProtectedGetters_validationIsRun_validationFails() {
        GetSetVerificationContext context = VerificationContextBuilder.forClass(ProtectedGetter.class).build();

        VerificationResult result = sut.execute(context);

        assertThat(result.isSuccess(), is(false));
        assertThat(result.getMessage(), containsString("getSomething"));
    }

    class PublicGetter {
        public String getSomething() {
            return "";
        }
    }

    class PrivateGetter {
        private String getSomething() {
            return "";
        }
    }

    class ProtectedGetter {
        protected String getSomething() {
            return "";
        }
    }
}