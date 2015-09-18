package nicojs.boilerplateverifiers.internals.tostring;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nicojs.boilerplateverifiers.internals.GraphBehavior;

/**
 * Represents a ToStringConfiguration
 * Created by nicojs
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ToStringConfiguration {

    private Class<?> targetClass;
    private GraphBehavior graphBehavior;

    public static ToStringConfiguration of(Class<?> targetClass) {
        return new ToStringConfiguration(targetClass, GraphBehavior.LOOPING_NODE_TREE);
    }

    public void setGraphBehavior(GraphBehavior graphBehavior) {
        this.graphBehavior = graphBehavior;
    }
}