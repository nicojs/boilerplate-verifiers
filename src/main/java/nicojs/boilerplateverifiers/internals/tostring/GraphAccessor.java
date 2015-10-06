package nicojs.boilerplateverifiers.internals.tostring;

/**
 * Represents a GraphAccessor
 * Created by nicojs
 */
public class GraphAccessor {
    public static final String SUPER_PATH_VALUE = "%super";
    private final Class targetClass;
    private final String path;
    private GraphAccessor superAccessor;


    public GraphAccessor(Class targetClass) {
        this(targetClass, "");
    }

    public GraphAccessor(Class targetClass, String path) {
        this.targetClass = targetClass;
        this.path = path;
        inspectSuperClass();
    }

    private void inspectSuperClass() {
        if (targetClass.getSuperclass() != null) {
            superAccessor = new GraphAccessor(targetClass.getSuperclass(), String.format("%s%s", path, SUPER_PATH_VALUE));
        }
    }

    public void verifyAttributes(Object actualObject, String actualStringRepresentation, VerificationContext context) {
        if (!context.shouldBeIgnored(actualObject, path)) {
            if (superAccessor != null) {
                superAccessor.verifyAttributes(actualObject, actualStringRepresentation, context);
            }

            // Before we walk the graph, add this object to list of verified object in the context, preventing infinite loops
            context.addVerifiedObject(actualObject);
            for (GraphAttributeAccessor attribute : GraphAttributeAccessor.forClassAttributes(targetClass, path)) {
                attribute.verify(actualObject, actualStringRepresentation, context);
            }
        }
    }
}
