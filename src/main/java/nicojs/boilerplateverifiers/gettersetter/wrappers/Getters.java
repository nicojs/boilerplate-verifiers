package nicojs.boilerplateverifiers.gettersetter.wrappers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Getters implements Iterable<GetterDeclaration> {
    private Map<String, GetterDeclaration> map = new HashMap<>();

    public void put(String name, GetterDeclaration declaration) {
        this.map.put(name, declaration);
    }

    @Override
    public Iterator<GetterDeclaration> iterator() {
        return map.values().iterator();
    }
}
