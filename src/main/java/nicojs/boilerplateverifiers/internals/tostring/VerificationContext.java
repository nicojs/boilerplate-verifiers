package nicojs.boilerplateverifiers.internals.tostring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a VerificationContext
 * Created by nicojs
 */
public class VerificationContext {
    private List<Object> verifiedObjects;
    private List<String> pathsToIgnore;

    public VerificationContext(String[] pathsToIgnore) {
        verifiedObjects = new ArrayList<>();
        this.pathsToIgnore = Arrays.asList(pathsToIgnore);
    }

    public void addVerifiedObject(Object verifiedObject) {
        verifiedObjects.add(verifiedObject);
    }

    public boolean shouldBeIgnored(Object maybeVerifiedObject, String pathToObject){
        return isVerified(maybeVerifiedObject) || isIgnored(pathToObject);
    }

    private boolean isVerified(Object maybeVerifiedObject) {
        for (Object verifiedObject : verifiedObjects) {
            // Deliberate test for reference equality
            if (verifiedObject == maybeVerifiedObject) {
                return true;
            }
        }
        return false;
    }

    private boolean isIgnored(String path){
        return pathsToIgnore.contains(path);
    }
}
