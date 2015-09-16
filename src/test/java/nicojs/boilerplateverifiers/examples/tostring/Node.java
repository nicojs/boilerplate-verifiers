package nicojs.boilerplateverifiers.examples.tostring;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Represents a Node
 * Created by nicojs
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class Node {
    private final String name;
    private Node parent;

    public String toString() {
        return "nicojs.boilerplateverifiers.examples.tostring.Node(name=" + this.name + ")";
    }
}
