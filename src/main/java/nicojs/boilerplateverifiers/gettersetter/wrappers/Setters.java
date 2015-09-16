package nicojs.boilerplateverifiers.gettersetter.wrappers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Setters implements Iterable<SetterDeclaration> {
    private Map<String, SetterDeclaration> map = new HashMap<>();

    public void put(String name, SetterDeclaration declaration) {
        this.map.put(name, declaration);
    }

    @Override
    public Iterator<SetterDeclaration> iterator() {
        return map.values().iterator();
    }
}
