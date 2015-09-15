package nicojs.boilerplateverifiers.gettersetter;

import java.util.ArrayList;
import java.util.List;

public abstract class GetterSetterCheck {

    public abstract VerificationResult execute(GetSetVerificationContext context);

    protected abstract String errorFormat();

    private List<String> failures = new ArrayList<>();

    public void addFailure(String failure) {
        this.failures.add(failure);
    }

    public VerificationResult returnResult() {
        if (failures.isEmpty()) {
            return VerificationResult.SUCCESS;
        } else {
            String errorMessage = String.format(errorFormat(), failures);
            failures = new ArrayList<>();
            return VerificationResult.builder().success(false).message(errorMessage).build();
        }
    }
}
