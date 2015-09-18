package nicojs.boilerplateverifiers.internals.tostring;

/**
 * Represents a GraphAccessor
 * Created by nicojs
 */
public class GraphAccessor {
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
            superAccessor = new GraphAccessor(targetClass.getSuperclass());
        }
    }

    public void verifyAttributes(Object actualObject, String actualStringRepresentation, VerificationContext context) {
        if (!context.isVerified(actualObject)) {
            context.addVerifiedObject(actualObject);
            for (GraphAttributeAccessor attribute : GraphAttributeAccessor.forClassAttributes(targetClass, path)) {
                attribute.verify(actualObject, actualStringRepresentation, context);
            }
            if (superAccessor != null) {
                superAccessor.verifyAttributes(actualObject, actualStringRepresentation, context);
            }
        }
    }
}
