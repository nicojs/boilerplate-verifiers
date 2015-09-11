package nicojs.boilerplateverifiers.internals.tostring;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a ToStringConfiguration
 * Created by nicojs
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ToStringConfiguration {

    private Class<?> targetClass;

    public static ToStringConfiguration of(Class<?> targetClass) {
        return new ToStringConfiguration(targetClass);
    }
}