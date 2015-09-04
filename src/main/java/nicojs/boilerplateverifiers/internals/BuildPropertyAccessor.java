package nicojs.boilerplateverifiers.internals;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        if(mode != AttributeAccessorMode.DIRECT) {
            result = retrieveValueFromGetter(buildResult);
        }
        if (!result.succeeded) {
            result = retrieveValueFromField(buildResult, buildResult.getClass());
        }
        if (!result.succeeded) {
            fail(String.format("Could not find the corresponding field (or getter) for builder method \"%s\".", getName()));
        }
        assertThat(String.format("Value used to build was not equal to value after build for property \"%s\".", getAttributeName()),
                result.getValue(), is(expectedValue));
    }

    /**
     * Recursive function to retrieve the actual value from a field
     * This function is recursive, because it might need to retrieve the field from a
     * private attribute forClass one forClass its parent classes
     *
     * @param buildResult The actual value forClass the object that was build
     * @param clazz       The class on which the field need to be found
     * @return An object containing the resulting value
     */
    private ResultValue retrieveValueFromField(Object buildResult, Class<?> clazz) {
        ResultValue result = new ResultValue(false);
        if (clazz != null) {
            Field field = tryGetField(clazz);
            if (field != null) {
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(buildResult);
                } catch (IllegalAccessException e) {
                    fail(String.format("Field \"%s\" of class \"%s\" is not accessible (is it public?).", field.getName(), clazz.getSimpleName()));
                }
                result = new ResultValue(true, value);
            }
            if (!result.succeeded) {
                result = retrieveValueFromField(buildResult, clazz.getSuperclass());
            }
        }
        return result;
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
        if(propertyClass == null){
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

    public String getAttributeName(){
        String name = builderMethod.getName();
        if(name.startsWith(builderMethodPrefix)){
            name = name.substring(builderMethodPrefix.length(), builderMethodPrefix.length()+1).toLowerCase() + name.substring(builderMethodPrefix.length()+1);
        }
        return name;
    }

    public Object populate(Object value) throws InvocationTargetException, IllegalAccessException {
        Object builder = builderMethod.invoke(builderInstance, value);
        expectedValue = value;
        return builder;
    }

    public void verifyPrefix() {
        assertThat(String.format("Expected method \"%s\" on builder class \"%s\" to begin with prefix \"%s\", but it did not. Please use \"allMethodsOnBuilderClassShouldBeUsedExcept(\"%s\") if this method should be ignored.\"",
                getName(), builderMethod.getDeclaringClass().getSimpleName(), builderMethodPrefix, getName()), getName(), startsWith(builderMethodPrefix));
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
