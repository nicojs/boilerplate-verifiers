package nicojs.boilerplateverifiers.internals.tostring;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nicojs.boilerplateverifiers.GraphStrategy;

/**
 * Represents a ToStringConfiguration
 * Created by nicojs
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ToStringConfiguration {

    private Class<?> targetClass;
    private GraphStrategy graphStrategy;

    public static ToStringConfiguration of(Class<?> targetClass) {
        return new ToStringConfiguration(targetClass, GraphStrategy.LOOPING_TREE);
    }

    public void setGraphStrategy(GraphStrategy graphStrategy) {
        this.graphStrategy = graphStrategy;
    }
}