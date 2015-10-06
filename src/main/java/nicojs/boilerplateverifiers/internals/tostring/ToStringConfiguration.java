package nicojs.boilerplateverifiers.internals.tostring;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nicojs.boilerplateverifiers.GraphStrategy;

/**
 * Represents a ToStringConfiguration
 * Created by nicojs
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ToStringConfiguration {

    private Class<?> targetClass;

    @Setter
    private GraphStrategy graphStrategy;

    @Setter
    private String[] attributesBlacklist;

    public static ToStringConfiguration of(Class<?> targetClass) {
        return new ToStringConfiguration(targetClass, GraphStrategy.LOOPING_TREE, new String[0]);
    }
}