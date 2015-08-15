package nicojs.boilerplateverifiers.internals;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Represents a BuildProperty
 * Created by nicojs on 8/12/2015.
 */
@AllArgsConstructor
public class BuildProperty {

    private Method builderMethod;
    private Object expectedValue;

    public void assertValue(Object buildResult) {
        ResultValue result = retrieveValueFromGetter(buildResult);
        if (!result.succeeded) {
            result = retrieveValueFromField(buildResult, buildResult.getClass());
        }
        if (!result.succeeded) {
            fail(String.format("Could not find the corresponding field (or getter) for builder method \"%s\".", builderMethod.getName()));
        }
        assertThat(String.format("Value used to build was not equal to value after build for property \"%s\".", builderMethod.getName()),
                result.getValue(), is(expectedValue));
    }

    /**
     * Recursive function to retrieve the actual value from a field
     * This function is recursive, because it might need to retrieve the field from a
     * private attribute of one of its parent classes
     * @param buildResult The actual value of the object that was build
     * @param clazz The class on which the field need to be found
     * @return An object containing the resulting value
     */
    private ResultValue retrieveValueFromField(Object buildResult, Class<?> clazz) {
        ResultValue result = new ResultValue(false);
        if(clazz != null) {
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
            assertThat(String.format("Found getter \"%s\" should not accept parameter(s).", getterName), getter.getParameterCount(), is(0));
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

    @Value
    @AllArgsConstructor
    private static class ResultValue {
        private ResultValue(boolean succeeded) {
            this(succeeded, null);
        }

        private boolean succeeded;
        private Object value;
    }

    private Field tryGetField(Class<?> clazz) {
        try {
            return clazz.getDeclaredField(builderMethod.getName());
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
        String getter = "get" + Character.toUpperCase(builderMethod.getName().charAt(0));
        if (builderMethod.getName().length() > 1) {
            getter += builderMethod.getName().substring(1);
        }
        return getter;
    }
}
