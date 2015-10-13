package nicojs.boilerplateverifiers.examples.tostring;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a NodeWithRecursiveToString
 * Created by nicojs
 */
public class NodeWithSmartRecursiveToString {
    private String name;
    private NodeWithSmartRecursiveToString parent;

    public String toString() {
        return toString(new ArrayList<NodeWithSmartRecursiveToString>());
    }

    private String toString(List<NodeWithSmartRecursiveToString> nodesAlreadyProcessed) {
        String s = "nicojs.boilerplateverifiers.examples.tostring.NodeWithSmartRecursiveToString(name=" + this.name + ", parent=";
        if (!nodesAlreadyProcessed.contains(this)) {
            nodesAlreadyProcessed.add(this);
            s += this.parent.toString(nodesAlreadyProcessed);
        } else {
            s += "(loop)";
        }
        s += ")";
        return s;
    }
}
