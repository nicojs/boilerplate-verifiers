package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.internals.AttributeAccessorMode;
import nicojs.boilerplateverifiers.internals.BuildPropertyAccessor;
import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Represents a BuilderVerifier
 * Created by nicoj on 8/12/2015.
 */
public class BuilderVerifier {

    private static final String BUILD_METHOD_NAME = "build";
    private static final List<String> DEFAULT_BUILDER_CLASS_METHOD_BLACKLIST = Arrays.asList(BUILD_METHOD_NAME,
            "toString", "equals", "hashCode", "notify", "notifyAll", "getClass", "wait");
    private String builderMethodName = "builder";
    private Class<?> targetClass;
    private ValueFactories valueFactories;
    private List<BuildPropertyAccessor> buildProperties;
    private Object builder;
    private Object buildResult;
    private Set<String> attributeBlacklist;
    private Set<String> builderClassMethodBlacklist;
    private AttributeAccessorMode verificationAccessorMode;

    public BuilderVerifier(Class<?> targetClass) {
        this.targetClass = targetClass;
        verificationAccessorMode = AttributeAccessorMode.GETTER_IF_POSSIBLE;
        valueFactories = new ValueFactories();
        buildProperties = new ArrayList<>();
        builderClassMethodBlacklist = new HashSet<>(DEFAULT_BUILDER_CLASS_METHOD_BLACKLIST);
        attributeBlacklist = new HashSet<>();
    }

    public static BuilderVerifier forClass(Class<?> clazz) {
        return new BuilderVerifier(clazz);
    }

    public BuilderVerifier allAttributesShouldBeBuildExcept(String... attributeNames) {
        Collections.addAll(attributeBlacklist, attributeNames);
        return this;
    }

    public BuilderVerifier allMethodsOnBuilderClassShouldBeUsedExcept(String... builderClassMethodNames) {
        Collections.addAll(builderClassMethodBlacklist, builderClassMethodNames);
        return this;
    }

    public void verify() {
        JavaValueFactoryArchitect.fill(valueFactories);
        instantiateBuilder();
        inspectBuilderClass();
        verifyAllTargetClassAttributesCanBeBuild();
        populateBuilder();
        build();
        verifyBuildResult();
    }

    private void verifyAllTargetClassAttributesCanBeBuild() {
        verifyAllTargetClassAttributesCanBeBuild(targetClass);
    }

    private void verifyAllTargetClassAttributesCanBeBuild(Class clazz) {
        if (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (isValidAttribute(field)) {
                    BuildPropertyAccessor matchedBuildProperty = null;
                    for (BuildPropertyAccessor buildProperty : buildProperties) {
                        if (buildProperty.getName().equals(field.getName())) {
                            matchedBuildProperty = buildProperty;
                        }
                    }
                    assertThat(String.format("Missing build method for field \"%s\" (declared in class \"%s\"), use allAttributesShouldBeBuildExcept to ignore this attribute if this is by design.",
                            field.getName(), clazz.getSimpleName()), matchedBuildProperty, is(not(nullValue())));
                }
            }
            verifyAllTargetClassAttributesCanBeBuild(clazz.getSuperclass());
        }
    }

    private boolean isValidAttribute(Field field) {
        return !Modifier.isStatic(field.getModifiers())
                && !attributeBlacklist.contains(field.getName());
    }

    private void verifyBuildResult() {
        for (BuildPropertyAccessor buildProperty : buildProperties) {
            buildProperty.verifyValue(buildResult);
        }
    }

    private void build() {
        try {
            Method build = builder.getClass().getDeclaredMethod(BUILD_METHOD_NAME);
            assertThat(String.format("Expected builder method \"%s\" on \"%s\" to accept no parameters.",
                    BUILD_METHOD_NAME, builder.getClass().getSimpleName()), build.getParameterTypes().length, is(0));
            buildResult = build.invoke(builder);
        } catch (NoSuchMethodException e) {
            fail(String.format("No method called \"%s\" found on \"%s\".", BUILD_METHOD_NAME, builder.getClass().getSimpleName()));
        } catch (InvocationTargetException e) {
            fail(String.format("Method \"%s\" could not be invoked on builder class \"%s\".", builderMethodName, builder.getClass().getSimpleName()));
        } catch (IllegalAccessException e) {
            fail(String.format("Method \"%s\" is not accessible on builder class \"%s\".", BUILD_METHOD_NAME, builder.getClass().getSimpleName()));
        }
    }

    private void instantiateBuilder() {
        try {
            Method builderMethod = targetClass.getDeclaredMethod(builderMethodName);
            builder = builderMethod.invoke(null);

        } catch (NoSuchMethodException e) {
            fail(String.format("No method found called \"%s\", did you call it differently?", builderMethodName));
        } catch (InvocationTargetException e) {
            fail(String.format("Method \"%s\" could not be invoked. Is it static?", builderMethodName));
        } catch (IllegalAccessException e) {
            fail(String.format("Method \"%s\" is not accessible.", builderMethodName));
        }
    }

    private void inspectBuilderClass() {
        for (Method method : builder.getClass().getMethods()) {
            if (isNotBlacklisted(method)) {
                buildProperties.add(new BuildPropertyAccessor(builder, method, verificationAccessorMode));
            }
        }
    }

    private void populateBuilder() {
        for (BuildPropertyAccessor buildProperty : buildProperties) {
            Class<?> propertyClass = buildProperty.getPropertyClass();
            Object value = valueFactories.provideNextValue(propertyClass);
            try {
                Object builderInstance = buildProperty.populate(value);
                assertThat(String.format("Builder method for \"%s\" does not return the instance of \"%s\". Add 'return this' as a final statement of the method.",
                                buildProperty.getName(), this.builder.getClass().getSimpleName()),
                        builderInstance, is(builder));
            } catch (IllegalAccessException e) {
                fail(String.format("Method \"%s\" on builder could not accessed.", buildProperty.getName()));
            } catch (InvocationTargetException e) {
                fail(String.format("Method \"%s\" on builder could not be invoked. Is it static?", buildProperty.getName()));
            }
        }
    }

    private boolean isNotBlacklisted(Method method) {
        if (!builderClassMethodBlacklist.contains(method.getName())) {
            assertThat(String.format("Method \"%s\" on builder class should accept exactly one parameter.", method.getName()), method.getParameterTypes().length, is(1));
            return true;
        } else {
            return false;
        }
    }

    public BuilderVerifier usingBuilderMethod(String builderMethodName) {
        this.builderMethodName = builderMethodName;
        return this;
    }

    public BuilderVerifier withoutUsingGettersForVerification() {
        verificationAccessorMode = AttributeAccessorMode.DIRECT;
        return this;
    }

    public BuilderVerifier withValueFactories(ValueFactory<?>... valueFactories) {
        this.valueFactories.putIfNotExists(valueFactories);
        return this;
    }
}
