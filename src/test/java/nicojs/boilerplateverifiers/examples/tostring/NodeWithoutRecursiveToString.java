package nicojs.boilerplateverifiers.examples.tostring;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Represents a NodeWithoutRecursiveToString
 * Created by nicojs
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class NodeWithoutRecursiveToString {
    private final String name;
    private NodeWithoutRecursiveToString parent;

    public String toString() {
        return "nicojs.boilerplateverifiers.examples.tostring.NodeWithoutRecursiveToString(name=" + this.name + ")";
    }
}
