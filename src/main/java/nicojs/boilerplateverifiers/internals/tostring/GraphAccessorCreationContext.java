package nicojs.boilerplateverifiers.internals.tostring;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a GraphAccessorCreationContext
 * Created by nicojs
 */
public class GraphAccessorCreationContext {

    private List<Object> graphNodeValues;

    public GraphAccessorCreationContext() {
        graphNodeValues = new ArrayList<>();
    }

    public void addGraphNodeValue(Object graphNodeValue){
        graphNodeValues.add(graphNodeValue);
    }

    public boolean shouldInspect(Object graphNodeValue){
        boolean shouldInspect = graphNodeValue != null;
        if(shouldInspect) {
            for (Object nodeValue : graphNodeValues) {
                // Deliberate test for reference equality
                if (nodeValue == graphNodeValue) {
                    shouldInspect = false;
                }
            }
        }
        return shouldInspect;
    }
}
