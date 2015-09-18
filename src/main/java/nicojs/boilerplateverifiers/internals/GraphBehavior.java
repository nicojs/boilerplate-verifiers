package nicojs.boilerplateverifiers.internals;

/**
 * Represents a setting to specify the behavior of a graph
 * Created by nicojs
 */
public enum GraphBehavior {
    /**
     * Represents the graph mode for a self referencing graph
     * For example Book --- Author relationship
     */
    SELF_REFERENCING,
    /**
     * Represents the graph mode for a graph which functions as a node tree
     * The 'leafs' will be null.
     */
    NODE_TREE,
    /**
     * Represents the graph mode for a graph which functions as a node tree.
     * The 'leafs' will again point to the 'grandparent' and thus making loopings.
     */
    LOOPING_NODE_TREE
}
