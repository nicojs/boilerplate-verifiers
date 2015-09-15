package nicojs.boilerplateverifiers.gettersetter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;

public class VerificationContextBuilder<T> {
    private static List<String> defaultExcludedFields = Arrays.asList("class", "this$0");
    private PropertyContextNames excludedProperties = fillDefaultExcludedProperties();
    private Class<T> classToTest;

    VerificationContextBuilder(Class<T> classToTest) {
        this.classToTest = classToTest;
    }

    public static <T> VerificationContextBuilder forClass(Class<T> tClass) {
        return new VerificationContextBuilder(tClass);
    }

    public GetSetVerificationContext<T> build() {
        return GetSetVerificationContext.<T>builder()
                .classToTest(classToTest)
                .fields(determineFieldsToTest())
                .methods(determineMethodsToTest())
                .build();
    }

    private static PropertyContextNames fillDefaultExcludedProperties() {
        PropertyContextNames excludedProperties = new PropertyContextNames();
        for (String excludedField : defaultExcludedFields) {
            excludedProperties.add(PropertyContextName.fromFieldName(excludedField));
        }
        return excludedProperties;
    }

    Fields determineFieldsToTest() {
        Fields filteredFields = new Fields();
        Field[] declaredFields = classToTest.getDeclaredFields();
        for (Field field : declaredFields) {
            if (!excludedProperties.fieldNames().contains(field.getName())) {
                filteredFields.put(field.getName(), field);
            }
        }
        return filteredFields;
    }

    private BeanInfo getBeanInfo() {
        try {
            return Introspector.getBeanInfo(classToTest);
        } catch (IntrospectionException e) {
            fail();
            throw new IllegalStateException(e);
        }
    }

    Methods determineMethodsToTest() {
        Methods filteredMethods = new Methods();
        Method[] methods = classToTest.getDeclaredMethods();
        for (Method descriptor : methods) {
            if (descriptor.getName().startsWith("get") || descriptor.getName().startsWith("set"))
                addMethodIfNotNullOrExcluded(filteredMethods, descriptor);
        }
        return filteredMethods;
    }

    private void addMethodIfNotNullOrExcluded(Methods filteredMethods, Method method) {
        if (method != null && !excludedProperties.methodNames().contains(method.getName())) {
            filteredMethods.put(method.getName(), method);
        }
    }

    public VerificationContextBuilder<T> excludeField(String fieldName) {
        excludedProperties.add(PropertyContextName.fromFieldName(fieldName));
        return this;
    }
}
