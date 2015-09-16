package nicojs.boilerplateverifiers.gettersetter.wrappers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Fields implements Iterable<FieldDeclaration> {
    private Map<String, FieldDeclaration> fieldMap = new HashMap<>();

    public void put(String fieldName, FieldDeclaration declaration) {
        fieldMap.put(fieldName, declaration);
    }

    public boolean hasFieldWithName(String fieldName) {
        return fieldMap.containsKey(fieldName);
    }

    public FieldDeclaration getFieldByName(String fieldName) {
        return fieldMap.get(fieldName);
    }

    @Override
    public Iterator<FieldDeclaration> iterator() {
        return fieldMap.values().iterator();
    }
}
