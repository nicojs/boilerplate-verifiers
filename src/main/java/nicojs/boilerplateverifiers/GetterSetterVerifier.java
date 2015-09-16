package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.gettersetter.GetSetVerificationContext;
import nicojs.boilerplateverifiers.gettersetter.VerificationContextBuilder;
import nicojs.boilerplateverifiers.gettersetter.VerificationResult;
import nicojs.boilerplateverifiers.gettersetter.checks.Validations;
import nicojs.boilerplateverifiers.gettersetter.exceptions.GetterSetterVerificationException;

import java.util.ArrayList;
import java.util.List;

public class GetterSetterVerifier<T> {
    private VerificationContextBuilder<T> verificationContextBuilder;
    private List<Validations> verificationToRun;

    GetterSetterVerifier(Class<T> classToTest) {
        this.verificationToRun = determineDefaultValidations();
        this.verificationContextBuilder = VerificationContextBuilder.forClass(classToTest);
    }

    private List<Validations> determineDefaultValidations() {
        List<Validations> verificationToRun = new ArrayList<>();
        for (Validations validations : Validations.values()) {
            verificationToRun.add(validations);
        }
        return verificationToRun;
    }

    public static <T> GetterSetterVerifier<T> forClass(Class<T> classToTest) {
        return new GetterSetterVerifier(classToTest);
    }

    public void verify() {
        GetSetVerificationContext verificationContext = verificationContextBuilder.build();
        runValidations(verificationContext);
    }

    private void runValidations(GetSetVerificationContext context) {
        for (Validations validation : this.verificationToRun) {
            VerificationResult result = validation.getGetterSettercheck().execute(context);
            if (!result.isSuccess()) {
                throw new GetterSetterVerificationException(String.format("Validation %s failed!: %s", validation, result.getMessage()));
            }
        }
    }

    //package scope while work in progress
    GetterSetterVerifier<T> excludeField(String fieldName) {
        verificationContextBuilder.excludeField(fieldName);
        return this;
    }

    //package scope while work in progress
    GetterSetterVerifier<T> excludeChecks(Validations... validation) {
        for (Validations excluded : validation) {
            this.verificationToRun.remove(excluded);
        }
        return this;
    }
}
