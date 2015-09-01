package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.internals.BuildPropertyAccessor;
import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static final String BUILD_METHOD_NAME = "build";
    private static final List<String> METHOD_BLACK_LIST = Arrays.asList(BUILD_METHOD_NAME,
            "toString", "equals", "hashCode", "notify", "notifyAll", "getClass", "wait");
    private String builderMethodName = "builder";
    private Class<?> targetClass;
    private ValueFactories valueFactories;
    private List<BuildPropertyAccessor> buildProperties;
    private Object builder;
    private Object buildResult;

    public BuilderVerifier(Class<?> targetClass) {
        this.targetClass = targetClass;
        valueFactories = new ValueFactories();
        buildProperties = new ArrayList<>();
        JavaValueFactoryArchitect.fill(valueFactories);
    }

    public static BuilderVerifier forClass(Class<?> clazz) {
        return new BuilderVerifier(clazz);
    }

    public void verify() {
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
                if (!Modifier.isStatic(field.getModifiers())) {
                    BuildPropertyAccessor matchedBuildProperty = null;
                    for (BuildPropertyAccessor buildProperty : buildProperties) {
                        if (buildProperty.getName().equals(field.getName())) {
                            matchedBuildProperty = buildProperty;
                        }
                    }
                    assertThat(String.format("Missing build method for field \"%s\" (declared in class \"%s\"), add to ignore list if this is by design.",
                            field.getName(), clazz.getSimpleName()), matchedBuildProperty, is(not(nullValue())));
                }
            }
            verifyAllTargetClassAttributesCanBeBuild(clazz.getSuperclass());
        }
    }

    private void verifyBuildResult() {
        for (BuildPropertyAccessor buildProperty : buildProperties) {
            buildProperty.assertValue(buildResult);
        }
    }

    @SuppressWarnings("NullArgumentToVariableArgMethod")
    private void build() {
        try {
            Method build = builder.getClass().getDeclaredMethod(BUILD_METHOD_NAME);
            assertThat(String.format("Expected builder method \"%s\" on \"%s\" to accept no parameters.",
                    BUILD_METHOD_NAME, builder.getClass().getSimpleName()), build.getParameterTypes().length, is(0));
            buildResult = build.invoke(builder, null);
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
                buildProperties.add(new BuildPropertyAccessor(builder, method));
            }
        }
    }

    private void populateBuilder() {
        for (BuildPropertyAccessor buildProperty : buildProperties) {
            Class<?> propertyClass = buildProperty.getPropertyClass();
            Object value = valueFactories.provideNextValue(propertyClass);
            try {
                buildProperty.populate(value);
            } catch (IllegalAccessException e) {
                fail(String.format("Method \"%s\" on builder could not accessed.", buildProperty.getName()));
            } catch (InvocationTargetException e) {
                fail(String.format("Method \"%s\" on builder could not be invoked. Is it static?", buildProperty.getName()));
            }
        }
    }

    private boolean isNotBlacklisted(Method method) {

        if (!METHOD_BLACK_LIST.contains(method.getName())) {
            assertThat(String.format("Builder method \"%s\" should accept exactly one parameter.", method.getName()), method.getParameterTypes().length, is(1));
            return true;
        } else {
            return false;
        }
    }

    public BuilderVerifier usingBuilderMethod(String builderMethodName) {
        this.builderMethodName = builderMethodName;
        return this;
    }
}
