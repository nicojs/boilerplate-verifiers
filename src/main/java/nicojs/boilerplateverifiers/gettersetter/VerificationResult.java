package nicojs.boilerplateverifiers.gettersetter;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class VerificationResult {
    public final static VerificationResult SUCCESS = new VerificationResult(true, "ok");
    @NonNull
    private boolean success;
    @NonNull
    private String message;
}
