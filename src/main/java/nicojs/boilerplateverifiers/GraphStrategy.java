package nicojs.boilerplateverifiers;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a setting to specify the behavior of a graph
 * Created by nicojs
 */
@AllArgsConstructor
@Getter
public enum GraphStrategy {
    /**
     * Represents the graph mode for a self referencing graph
     * For example Book --- Author relationship
     */
    SELF_REFERENCING(1, true),
    /**
     * Represents the graph mode for a graph which functions as a node tree
     * The 'leafs' will be null.
     */
    TREE(5, false),
    /**
     * Represents the graph mode for a graph which functions as a node tree.
     * The 'leafs' will again point to the 'grandparent' and thus making loopings.
     */
    LOOPING_TREE(5, true);

    private final int numberOfNodes;
    private final boolean looping;

}
