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


    public boolean shouldBeIgnored(String pathToObject){
        return isIgnored(pathToObject);
    }

    private boolean isIgnored(String path){
        return pathsToIgnore.contains(path);
    }
}
