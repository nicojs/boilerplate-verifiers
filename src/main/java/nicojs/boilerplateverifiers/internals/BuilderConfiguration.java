package nicojs.boilerplateverifiers.internals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a BuilderConfiguration
 * Created by nicojs on 9/3/2015.
 */
@Getter
@AllArgsConstructor
public class BuilderConfiguration {
    public static final String BUILD_METHOD_NAME = "build";
    private static final String DEFAULT_BUILDER_CREATOR_METHOD_NAME = "builder";
    private static final List<String> DEFAULT_BUILDER_CLASS_METHOD_BLACKLIST = Arrays.asList(BUILD_METHOD_NAME,
            "toString", "equals", "hashCode", "notify", "notifyAll", "getClass", "wait");

    private Class<?> targetClass;

    @Setter
    private String builderMethodName;
    private Set<String> attributeBlacklist;
    private Set<String> builderClassMethodBlacklist;

    @Setter
    private AttributeAccessorMode verificationAccessorMode;

    private String prefixForAllMethodsOnBuilder;

    @Setter
    private boolean alsoBuildSuperClasses;

    public static BuilderConfiguration of(Class<?> targetClass) {
        return new BuilderConfiguration(targetClass, DEFAULT_BUILDER_CREATOR_METHOD_NAME, new HashSet<String>(),
                new HashSet<>(DEFAULT_BUILDER_CLASS_METHOD_BLACKLIST), AttributeAccessorMode.GETTER_IF_POSSIBLE, "", true);
    }

    public void setPrefixForAllMethodsOnBuilder(String prefixForAllMethodsOnBuilder) {
        this.prefixForAllMethodsOnBuilder = prefixForAllMethodsOnBuilder;
    }
}
