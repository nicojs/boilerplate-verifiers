package nicojs.boilerplateverifiers.internals;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Represents a BuildProperty
 * Created by nicojs on 8/12/2015.
 */
@RequiredArgsConstructor
public class BuildPropertyAccessor {

    private final Object builderInstance;
    private final Method builderMethod;
    private final String builderMethodPrefix;
    private final AttributeAccessorMode mode;

    private Object expectedValue;
    private Class<?> propertyClass;

    public void verifyValue(Object buildResult) {
        ResultValue result = new ResultValue(false);
        if (mode != AttributeAccessorMode.DIRECT) {
            result = retrieveValueFromGetter(buildResult);
        }
        if (!result.succeeded) {
            result = retrieveAttributeValue(buildResult);
        }
        if (!result.succeeded) {
            fail(String.format("Could not find the corresponding field (or getter) for builder method \"%s\".", getName()));
        }
        assertThat(String.format("Value used to build was not equal to value after build for property \"%s\".", getAttributeName()),
                result.getValue(), is(expectedValue));
    }

    public void verifyAttributeAccessibility(Class<?> targetClass) {
        final Field field = getAttributeField(targetClass);
        if (field != null) {
            assertThat(String.format("Field \"%s\" of class \"%s\" is not declared final. Use \"withoutVerifyingAttributeAccessibility()\" to ignore this error.", field.getName(), field.getDeclaringClass().getSimpleName()),
                    Modifier.isFinal(field.getModifiers()), is(true));
        }
    }

    public void verifyPrefix() {
        assertThat(String.format("Expected method \"%s\" on builder class \"%s\" to begin with prefix \"%s\", but it did not. Please use \"allMethodsOnBuilderClassShouldBeUsedExcept(\"%s\") if this method should be ignored.\"",
                getName(), builderMethod.getDeclaringClass().getSimpleName(), builderMethodPrefix, getName()), getName(), startsWith(builderMethodPrefix));
    }

    /**
     * Retrieves the actual value from from the build result for this property accessor
     *
     * @param buildResult The actual value forClass the object that was build
     * @return An object containing the resulting value
     */
    private ResultValue retrieveAttributeValue(Object buildResult) {
        ResultValue result = new ResultValue(false);
        final Field field = getAttributeField(buildResult.getClass());
        if (field != null) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(buildResult);
            } catch (IllegalAccessException e) {
                fail(String.format("Field \"%s\" of class \"%s\" is not accessible (is it public?).", field.getName(), field.getDeclaringClass().getSimpleName()));
            }
            result = new ResultValue(true, value);
        }
        return result;
    }

    /**
     * Retrieves the attribute field for which this property accessor is an abstraction
     * This function is recursive, because it might need to retrieve the field from a
     * private attribute forClass one forClass its parent classes
     *
     * @param clazz the class of the resulting builded object or one of its super classes.
     * @return A field, or null if not found
     */
    private Field getAttributeField(Class<?> clazz) {
        Field field = null;
        if (clazz != null) {
            field = tryGetField(clazz);
            if (field == null) {
                return getAttributeField(clazz.getSuperclass());
            }
        }
        return field;
    }

    private ResultValue retrieveValueFromGetter(Object buildResult) {
        String getterName = getterName();
        Method getter = tryGetGetter(buildResult, getterName);
        ResultValue result;
        Object actualValue = null;
        if (getter == null) {
            result = new ResultValue(false);
        } else {
            assertThat(String.format("Found getter \"%s\" should not accept parameter(s).", getterName), getter.getParameterTypes().length, is(0));
            try {
                actualValue = getter.invoke(buildResult);
            } catch (InvocationTargetException e) {
                fail(String.format("Getter \"%s\" cannot be invoked.", getterName));
            } catch (IllegalAccessException e) {
                fail(String.format("Getter \"%s\" is not accessible (is it public?).", getterName));
            }
            result = new ResultValue(true, actualValue);
        }
        return result;
    }

    public Class<?> getPropertyClass() {
        if (propertyClass == null) {
            propertyClass = builderMethod.getParameterTypes()[0];
        }
        return propertyClass;
    }

    private Field tryGetField(Class<?> clazz) {
        try {
            return clazz.getDeclaredField(getAttributeName());
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private Method tryGetGetter(Object buildResult, String getterName) {
        try {
            return buildResult.getClass().getDeclaredMethod(getterName);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private String getterName() {
        String getter = "get" + Character.toUpperCase(getAttributeName().charAt(0));
        if (getAttributeName().length() > 1) {
            getter += getAttributeName().substring(1);
        }
        return getter;
    }

    public String getName() {
        return builderMethod.getName();
    }

    public String getAttributeName() {
        String name = builderMethod.getName();
        if (name.startsWith(builderMethodPrefix)) {
            name = name.substring(builderMethodPrefix.length(), builderMethodPrefix.length() + 1).toLowerCase() + name.substring(builderMethodPrefix.length() + 1);
        }
        return name;
    }

    public Object populate(Object value) throws InvocationTargetException, IllegalAccessException {
        Object builder = builderMethod.invoke(builderInstance, value);
        expectedValue = value;
        return builder;
    }

    @Value
    @AllArgsConstructor
    private static class ResultValue {
        private boolean succeeded;
        private Object value;

        private ResultValue(boolean succeeded) {
            this(succeeded, null);
        }
    }
}
