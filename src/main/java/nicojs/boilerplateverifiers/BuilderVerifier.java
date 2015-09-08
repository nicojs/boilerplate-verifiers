package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.internals.AttributeAccessorMode;
import nicojs.boilerplateverifiers.internals.BuildPropertyAccessor;
import nicojs.boilerplateverifiers.internals.BuilderConfiguration;
import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.lessThan;
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

    public BuilderVerifier withPrefixForAllMethodsOnBuilder(String prefix) {
        configuration.setPrefixForAllMethodsOnBuilder(prefix);
        return this;
    }

    public BuilderVerifier withoutBuildingSuperClasses() {
        configuration.setAlsoBuildSuperClasses(false);
        return this;
    }

    public void verify() {
        JavaValueFactoryArchitect.fill(valueFactories);
        instantiateBuilder();
        inspectBuilderClass();
        verifyBuilderConstructor();
        verifyBuildProperties();
        verifyAllTargetClassAttributesCanBeBuild();
        populateBuilder();
        build();
        verifyBuildResult();
    }

    private void verifyBuilderConstructor() {
        final Class<?> builderClass = builder.getClass();
        final Constructor<?>[] constructors = builderClass.getConstructors();
        assertThat(String.format("Expected the builder class \"%s\" to only have one constructor, found %d.", builderClass.getSimpleName(), constructors.length), constructors.length, lessThan(2));
        assertThat(String.format("The constructor for builder \"%s\" should not be declared public.", builderClass.getSimpleName()),
                constructors.length, is(0));
    }

    private void verifyBuildProperties() {
        for (BuildPropertyAccessor buildProperty : buildProperties) {
            buildProperty.verifyPrefix();
        }
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
                        if (buildProperty.getAttributeName().equals(field.getName())) {
                            matchedBuildProperty = buildProperty;
                        }
                    }
                    String additionalErrorMessage = "";
                    if (clazz != this.configuration.getTargetClass()) {
                        additionalErrorMessage = " or withoutBuildingSuperClasses()";
                    }
                    assertThat(String.format("Missing build method for field \"%s\" (declared in class \"%s\"), use allAttributesShouldBeBuildExcept(\"%s\")%s to specify that this attribute should not be build.",
                            field.getName(), clazz.getSimpleName(), field.getName(), additionalErrorMessage), matchedBuildProperty, is(not(nullValue())));
                }
            }
            if (configuration.isAlsoBuildSuperClasses()) {
                verifyAllTargetClassAttributesCanBeBuild(clazz.getSuperclass());
            }
        }
    }

    private boolean isValidAttribute(Field field) {
        return !Modifier.isStatic(field.getModifiers())
                && !configuration.getAttributeBlacklist().contains(field.getName());
    }

    private void verifyBuildResult() {
        for (BuildPropertyAccessor buildProperty : buildProperties) {
            buildProperty.verifyValue(buildResult);
            buildProperty.verifyAttributeAccessibility(configuration.getTargetClass());
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
            if (Modifier.isStatic(builderMethod.getModifiers())) {
                builder = builderMethod.invoke(null);
            } else {
                fail(String.format("Method \"%s\" of class \"%s\" is not declared static.", configuration.getBuilderMethodName(), configuration.getTargetClass().getSimpleName()));
            }
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
                buildProperties.add(new BuildPropertyAccessor(builder, method, configuration.getPrefixForAllMethodsOnBuilder(), configuration.getVerificationAccessorMode()));
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
