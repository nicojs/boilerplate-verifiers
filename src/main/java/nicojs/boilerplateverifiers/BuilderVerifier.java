package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.internals.AttributeAccessorMode;
import nicojs.boilerplateverifiers.internals.BuildPropertyAccessor;
import nicojs.boilerplateverifiers.internals.BuilderConfiguration;
import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private ValueFactories valueFactories;
    private List<BuildPropertyAccessor> buildProperties;
    private Object builder;
    private Object buildResult;
    private BuilderConfiguration configuration;

    private BuilderVerifier(Class<?> targetClass) {
        configuration = BuilderConfiguration.of(targetClass);
        valueFactories = new ValueFactories();
        buildProperties = new ArrayList<>();
    }

    public static BuilderVerifier forClass(Class<?> clazz) {
        return new BuilderVerifier(clazz);
    }

    public BuilderVerifier usingBuilderMethod(String builderMethodName) {
        this.configuration.setBuilderMethodName(builderMethodName);
        return this;
    }

    public BuilderVerifier withoutUsingGettersForVerification() {
        configuration.setVerificationAccessorMode(AttributeAccessorMode.DIRECT);
        return this;
    }

    public BuilderVerifier withValueFactories(ValueFactory<?>... valueFactoryOverrides) {
        valueFactories.putIfNotExists(valueFactoryOverrides);
        return this;
    }

    public BuilderVerifier allAttributesShouldBeBuildExcept(String... attributeNames) {
        Collections.addAll(configuration.getAttributeBlacklist(), attributeNames);
        return this;
    }

    public BuilderVerifier allMethodsOnBuilderClassShouldBeUsedExcept(String... builderClassMethodNames) {
        Collections.addAll(configuration.getBuilderClassMethodBlacklist(), builderClassMethodNames);
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
        verifyAllTargetClassAttributesCanBeBuild(configuration.getTargetClass());
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
                && !configuration.getAttributeBlacklist().contains(field.getName());
    }

    private void verifyBuildResult() {
        for (BuildPropertyAccessor buildProperty : buildProperties) {
            buildProperty.verifyValue(buildResult);
        }
    }

    private void build() {
        try {
            Method build = builder.getClass().getDeclaredMethod(BuilderConfiguration.BUILD_METHOD_NAME);
            assertThat(String.format("Expected builder method \"%s\" on \"%s\" to accept no parameters.",
                    BuilderConfiguration.BUILD_METHOD_NAME, builder.getClass().getSimpleName()), build.getParameterTypes().length, is(0));
            buildResult = build.invoke(builder);
        } catch (NoSuchMethodException e) {
            fail(String.format("No method called \"%s\" found on \"%s\".", BuilderConfiguration.BUILD_METHOD_NAME, builder.getClass().getSimpleName()));
        } catch (InvocationTargetException e) {
            fail(String.format("Method \"%s\" could not be invoked on builder class \"%s\".", BuilderConfiguration.BUILD_METHOD_NAME, builder.getClass().getSimpleName()));
        } catch (IllegalAccessException e) {
            fail(String.format("Method \"%s\" is not accessible on builder class \"%s\".", BuilderConfiguration.BUILD_METHOD_NAME, builder.getClass().getSimpleName()));
        }
    }

    private void instantiateBuilder() {
        try {
            Method builderMethod = configuration.getTargetClass().getDeclaredMethod(configuration.getBuilderMethodName());
            builder = builderMethod.invoke(null);

        } catch (NoSuchMethodException e) {
            fail(String.format("No method found called \"%s\", did you call it differently?", configuration.getBuilderMethodName()));
        } catch (InvocationTargetException e) {
            fail(String.format("Method \"%s\" could not be invoked. Is it static?", configuration.getBuilderMethodName()));
        } catch (IllegalAccessException e) {
            fail(String.format("Method \"%s\" is not accessible.", configuration.getBuilderMethodName()));
        }
    }

    private void inspectBuilderClass() {
        for (Method method : builder.getClass().getMethods()) {
            if (isNotBlacklisted(method)) {
                buildProperties.add(new BuildPropertyAccessor(builder, method, configuration.getVerificationAccessorMode()));
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
        if (!configuration.getBuilderClassMethodBlacklist().contains(method.getName())) {
            assertThat(String.format("Method \"%s\" on builder class should accept exactly one parameter.", method.getName()), method.getParameterTypes().length, is(1));
            return true;
        } else {
            return false;
        }
    }

}
