package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.internals.BuildProperty;
import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.ValueFactories;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Represents a BuilderVerifier
 * Created by nicoj on 8/12/2015.
 */
public class BuilderVerifier {

    private String builderMethodName = "builder";
    private static final String BUILD_METHOD_NAME = "build";
    private static final List<String> METHOD_BLACK_LIST = Arrays.asList(BUILD_METHOD_NAME,
            "toString", "equals", "hashCode", "notify", "notifyAll", "getClass", "wait");

    private Class<?> targetClass;
    private ValueFactories valueFactories;
    private List<BuildProperty> buildProperties;
    private Object builder;
    private Object buildResult;

    public BuilderVerifier(Class<?> targetClass) {
        this.targetClass = targetClass;
        valueFactories = new ValueFactories();
        buildProperties = new ArrayList<>();
        JavaValueFactoryArchitect.fill(valueFactories);
    }

    public void verify() {
        instantiateBuilder();
        populateBuilder();
        build();
        verifyBuildResult();
    }

    private void verifyBuildResult() {
        for (BuildProperty buildProperty : buildProperties) {
            buildProperty.assertValue(buildResult);
        }
    }

    @SuppressWarnings("NullArgumentToVariableArgMethod")
    private void build() {
        try {
            Method build = builder.getClass().getDeclaredMethod(BUILD_METHOD_NAME);
            assertThat(String.format("Expected builder method \"%s\" on \"%s\" to accept no parameters.",
                    BUILD_METHOD_NAME, builder.getClass().getSimpleName()), build.getParameterCount(), is(0));
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
            Method builderMethod = targetClass.getDeclaredMethod(builderMethodName, null);
            builder = builderMethod.invoke(null);

        } catch (NoSuchMethodException e) {
            fail(String.format("No method found called \"%s\", did you call it differently?", builderMethodName));
        } catch (InvocationTargetException e) {
            fail(String.format("Method \"%s\" could not be invoked. Is it static?", builderMethodName));
        } catch (IllegalAccessException e) {
            fail(String.format("Method \"%s\" is not accessible.", builderMethodName));
        }
    }

    private void populateBuilder() {
        for (Method method : builder.getClass().getMethods()) {
            if (isPropertySetter(method)) {
                Class<?> propertyClass = findPropertyClass(method);
                Object value = valueFactories.provideNextValue(propertyClass);
                try {
                    method.setAccessible(true);
                    method.invoke(builder, value);
                } catch (IllegalAccessException e) {
                    fail(String.format("Method \"%s\" on builder could not accessed.", method.getName()));
                } catch (InvocationTargetException e) {
                    fail(String.format("Method \"%s\" on builder could not be invoked. Is it static?", method.getName()));
                }
                buildProperties.add(new BuildProperty(method, value));
            }
        }
    }

    private boolean isPropertySetter(Method method) {

        if (!METHOD_BLACK_LIST.contains(method.getName())) {
            assertThat(String.format("Builder method \"%s\" should accept exactly one parameter.", method.getName()), method.getParameterCount(), is(1));
            return true;
        } else {
            return false;
        }
    }

    private Class<?> findPropertyClass(Method method) {
        return method.getParameterTypes()[0];
    }


    public static BuilderVerifier of(Class<?> clazz) {
        return new BuilderVerifier(clazz);
    }

    public BuilderVerifier usingBuilderMethod(String builderMethodName) {
        this.builderMethodName = builderMethodName;
        return this;
    }
}
