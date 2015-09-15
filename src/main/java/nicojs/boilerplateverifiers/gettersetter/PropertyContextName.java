package nicojs.boilerplateverifiers.gettersetter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by tomco on 10-9-2015.
 */
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
