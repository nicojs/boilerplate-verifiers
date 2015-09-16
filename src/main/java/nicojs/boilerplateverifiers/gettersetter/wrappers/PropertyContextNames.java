package nicojs.boilerplateverifiers.gettersetter.wrappers;

import java.util.ArrayList;
import java.util.List;

public class PropertyContextNames extends ArrayList<PropertyContextName> {

    public List<String> fieldNames() {
        List<String> fieldNames = new ArrayList<>();
        for (PropertyContextName propertyContext : this) {
            fieldNames.add(propertyContext.getFieldName());
        }
        return fieldNames;
    }

    public List<String> methodNames() {
        List<String> fieldNames = new ArrayList<>();
        for (PropertyContextName propertyContext : this) {
            fieldNames.add(propertyContext.getGetterName());
            fieldNames.add(propertyContext.getSetterName());
        }
        return fieldNames;
    }
}
