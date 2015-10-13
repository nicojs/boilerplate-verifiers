package nicojs.boilerplateverifiers.examples.tostring;

import lombok.ToString;

/**
 * Represents a NodeWithRecursiveToString
 * Created by nicojs
 */
@ToString
public class NodeWithRecursiveToString {
    private String name;
    private NodeWithRecursiveToString parent;
}
