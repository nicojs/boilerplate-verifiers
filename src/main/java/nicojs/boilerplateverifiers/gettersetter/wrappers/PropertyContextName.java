package nicojs.boilerplateverifiers.gettersetter.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nicojs.boilerplateverifiers.gettersetter.helpers.JavaBeansNameParser;

@AllArgsConstructor
@Getter
public class PropertyContextName {
    private String fieldName;
    private String setterName;
    private String getterName;

    public static PropertyContextName fromFieldName(String fieldName) {
        String getter = JavaBeansNameParser.fieldToGetter(fieldName);
        String setter = JavaBeansNameParser.fieldToSetter(fieldName);
        return new PropertyContextName(fieldName, setter, getter);
    }
}
