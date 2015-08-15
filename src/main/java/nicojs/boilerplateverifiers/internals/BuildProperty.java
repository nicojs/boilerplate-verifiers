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
        Result result = retrieveValueFromGetter(buildResult);
        if (!result.succeeded) {
            result = retrieveValueFromField(buildResult);
        }
        if (!result.succeeded) {
            fail("Could not find the corresponding field (or getter) for builder method \"%s\".");
        }
        assertThat(String.format("Value used to build was not equal to value after build for property \"%s\".", builderMethod.getName()),
                result.getValue(), is(expectedValue));
    }

    private Result retrieveValueFromField(Object buildResult) {
        Result result;
        Field field = tryGetField(buildResult);
        if (field == null) {
            result = new Result(false);
        } else {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(buildResult);
            } catch (IllegalAccessException e) {
                fail(String.format("Field \"%s\" is not accessible (is it public?).", field.getName()));
            }
            result = new Result(true, value);
        }

        return result;
    }

    private Result retrieveValueFromGetter(Object buildResult) {
        String getterName = getterName();
        Method getter = tryGetGetter(buildResult, getterName);
        Result result;
        Object actualValue = null;
        if (getter == null) {
            result = new Result(false);
        } else {
            assertThat(String.format("Found getter \"%s\" should not accept parameter(s).", getterName), getter.getParameterCount(), is(0));
            try {
                actualValue = getter.invoke(buildResult);
            } catch (InvocationTargetException e) {
                fail(String.format("Getter \"%s\" cannot be invoked.", getterName));
            } catch (IllegalAccessException e) {
                fail(String.format("Getter \"%s\" is not accessible (is it public?).", getterName));
            }
            result = new Result(true, actualValue);
        }
        return result;
    }

    @Value
    @AllArgsConstructor
    private static class Result {
        private Result(boolean succeeded) {
            this(succeeded, null);
        }

        private boolean succeeded;
        private Object value;
    }

    private Field tryGetField(Object buildResult) {
        try {
            return buildResult.getClass().getDeclaredField(builderMethod.getName());
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
