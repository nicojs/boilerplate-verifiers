package nicojs.boilerplateverifiers.internals.tostring;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a VerificationContext
 * Created by nicojs
 */
public class VerificationContext {
    private List<Object> verifiedObjects;

    public VerificationContext() {
        verifiedObjects = new ArrayList<>();
    }

    public void addVerifiedObject(Object verifiedObject) {
        verifiedObjects.add(verifiedObject);
    }

    public boolean isVerified(Object maybeVerifiedObject) {
        for (Object verifiedObject : verifiedObjects) {
            if (verifiedObject == maybeVerifiedObject) {
                return true;
            }
        }
        return false;
    }
}
