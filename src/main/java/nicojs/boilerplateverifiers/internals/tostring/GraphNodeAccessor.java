package nicojs.boilerplateverifiers.internals.tostring;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a GraphNodeAccessor
 * Created by nicojs
 */
public class GraphNodeAccessor {
    public static final String SUPER_PATH_VALUE = "%super";
    private final Class targetClass;
    private final Object value;
    private final String path;
    private GraphNodeAccessor superAccessor;
    private final List<GraphAttributeAccessor> attributeAccessors;


    public GraphNodeAccessor(Class targetClass, Object value) {
        this(targetClass, value, "", new GraphAccessorCreationContext());
    }

    public GraphNodeAccessor(Class targetClass, Object value, String path, GraphAccessorCreationContext context) {
        this.targetClass = targetClass;
        this.value = value;
        this.path = path;
        this.attributeAccessors = new ArrayList<>();
        inspectSuperClass(context);
        context.addGraphNodeValue(value);
        inspectAttributes(context);
    }

    private void inspectAttributes(GraphAccessorCreationContext context) {
        attributeAccessors.addAll(GraphAttributeAccessor.forAttributes(targetClass, value, path, context));
    }

    private void inspectSuperClass(GraphAccessorCreationContext context) {
        if (targetClass.getSuperclass() != null) {
            superAccessor = new GraphNodeAccessor(targetClass.getSuperclass(), value, String.format("%s%s", path, SUPER_PATH_VALUE), context);
        }
    }

    public void verifyAttributes(String actualStringRepresentation, VerificationContext context) {
        if (!context.shouldBeIgnored(path)) {
            if (superAccessor != null) {
                superAccessor.verifyAttributes(actualStringRepresentation, context);
            }

            for (GraphAttributeAccessor attribute : attributeAccessors) {
                attribute.verify(actualStringRepresentation, context);
            }
        }
    }
}
