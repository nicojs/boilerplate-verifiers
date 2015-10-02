package nicojs.boilerplateverifiers.internals.valuefactories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nicojs.boilerplateverifiers.GraphStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a context which is responsible for containing state about a graph that is being created.
 * A graph in this context is any complex object of which its attributes in turn are instantiated (recursively).
 * Created by nicojs on 8/21/2015.
 */
public class GraphCreationContext {

    private final GraphStrategy graphStrategy;
    private Map<Class, GraphObjectContainer> complexObjects;

    public GraphCreationContext(GraphStrategy graphStrategy) {
        this.graphStrategy = graphStrategy;
        complexObjects = new HashMap<>();
    }

    public GraphNodeResult getNode(Class<?> clazz) {
        GraphObjectContainer graphObjectContainer = complexObjects.get(clazz);
        GraphNodeResult result;
        if (graphObjectContainer == null) {
            result = new GraphNodeResult(false);
        }else{
            result = graphObjectContainer.getNextNode();
        }
        return result;
    }

    public void addNode(Class<?> clazz, Object object) {
        GraphObjectContainer graphObjectContainer = complexObjects.get(clazz);
        if (graphObjectContainer == null) {
            graphObjectContainer = new GraphObjectContainer();
            complexObjects.put(clazz, graphObjectContainer);
        }
        graphObjectContainer.addNode(object);
    }

    @AllArgsConstructor
    public class GraphNodeResult{
        private final boolean hasValue;

        @Getter
        private final Object value;

        public GraphNodeResult(boolean hasValue){
            this(hasValue, null);
        }

        public boolean hasValue(){
            return hasValue;
        }
    }

    private class GraphObjectContainer {
        private List<Object> objects;
        private int currentIndex;

        private GraphObjectContainer() {
            objects = new ArrayList<>(graphStrategy.getNumberOfNodes());
            currentIndex = 0;
        }

        private GraphNodeResult getNextNode() {
            // Rotate the objects
            GraphNodeResult result;
            if (objects.size() >= graphStrategy.getNumberOfNodes()) {
                if(graphStrategy.isLooping()) {
                    result = new GraphNodeResult(true, objects.get(currentIndex % graphStrategy.getNumberOfNodes()));
                    currentIndex++;
                }else{
                    result = new GraphNodeResult(true);
                }
            }else{
                result = new GraphNodeResult(false);
            }
            return result;
        }

        private void addNode(Object object) {
            objects.add(object);
        }
    }
}
