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
    private final List<GraphAttributeAccessor> attributeAccessors;
    private GraphNodeAccessor superAccessor;


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

    public List<String> remove(List<String> pathsToRemove) {
        pathsToRemove = new ArrayList<>(pathsToRemove); // copy over to make sure 'remove' is supported
        List<String> pathsFound = new ArrayList<>();
        removeSuperAccessorIfNeeded(pathsToRemove, pathsFound);
        removeAttributeAccessorsIfNeeded(pathsToRemove, pathsFound);
        pathsToRemove.removeAll(pathsFound);
        pathsToRemove = removeFromSuperAccessorGraph(pathsToRemove);
        pathsToRemove = removeFromAttributeAccessorGraphs(pathsToRemove);
        return pathsToRemove;
    }

    private List<String> removeFromAttributeAccessorGraphs(List<String> pathsToRemove) {
        for (GraphAttributeAccessor attributeAccessor : attributeAccessors) {
            pathsToRemove = attributeAccessor.remove(pathsToRemove);
        }
        return pathsToRemove;
    }

    private List<String> removeFromSuperAccessorGraph(List<String> pathsToRemove) {
        if(superAccessor!=null){
            pathsToRemove = superAccessor.remove(pathsToRemove);
        }
        return pathsToRemove;
    }

    private void removeAttributeAccessorsIfNeeded(List<String> pathsToRemove, List<String> pathsFound) {
        for (String path : pathsToRemove) {
            List<GraphAttributeAccessor> attributeAccessorsToRemove = new ArrayList<>();
            for (GraphAttributeAccessor attributeAccessor : attributeAccessors) {
                if(path.equals(attributeAccessor.getPath())){
                    attributeAccessorsToRemove.add(attributeAccessor);
                    pathsFound.add(path);
                }
            }
            for (GraphAttributeAccessor graphAttributeAccessor : attributeAccessorsToRemove) {
                attributeAccessors.remove(graphAttributeAccessor);
            }
        }
    }

    private void removeSuperAccessorIfNeeded(List<String> pathsToRemove, List<String> pathsFound) {
        for (String path : pathsToRemove) {
            if (superAccessor != null) {
                if (path.equals(superAccessor.path)) {
                    superAccessor = null;
                    pathsFound.add(path);
                }
            }
        }
    }
}
