package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.VerificationContextBuilder;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.checks.Validations;

import java.util.Arrays;
import java.util.List;

public class GetterSetterVerifier<T> {
    private VerificationContextBuilder<T> verificationContextBuilder;
    private Class<T> classToTest;
    private List<Validations> verificationToRun = Arrays.asList(Validations.values());

    GetterSetterVerifier(Class<T> classToTest) {
        this.classToTest = classToTest;
        this.verificationContextBuilder = VerificationContextBuilder.forClass(classToTest);
    }


    public GetterSetterVerifier<T> excludeField(String fieldName) {
        verificationContextBuilder.excludeField(fieldName);
        return this;
    }

    public static <T> GetterSetterVerifier<T> forClass(Class<T> classToTest) {
        return new GetterSetterVerifier(classToTest);
    }

    public GetterSetterVerifier<T> excludeCheck(Validations validation) {
        this.verificationToRun.remove(validation);
        return this;
    }

    public void verify() {
        GetSetVerificationContext verificationContext = verificationContextBuilder.build();
        runValidations(verificationContext);
    }

    private void runValidations(GetSetVerificationContext context) {
        for (Validations validation : this.verificationToRun) {
            VerificationResult result = validation.getGetterSettercheck().execute(context);
            if (!result.isSuccess()) {
                throw new AssertionError(String.format("Validation %s failed!: %s", validation, result.getMessage()));
            }
        }
    }
}
