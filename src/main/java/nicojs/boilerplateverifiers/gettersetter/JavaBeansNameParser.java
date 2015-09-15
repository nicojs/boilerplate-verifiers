package nicojs.boilerplateverifiers.gettersetter;

public class JavaBeansNameParser {

    //since method is the same for both a Get and Set
    public static String propertyMethodToField(String methodToField) {
        String firstLetter = methodToField.substring(3, 4).toLowerCase();
        return firstLetter.concat(methodToField.substring(4));
    }

    public static String fieldToGetter(String field) {
        return "get" + capitalizeFieldName(field);
    }

    public static String fieldToSetter(String field) {
        return "set" + capitalizeFieldName(field);
    }

    private static String capitalizeFieldName(String field) {
        String firstLetter = field.substring(0, 1).toUpperCase();
        return firstLetter.concat(field.substring(1));
    }
}
