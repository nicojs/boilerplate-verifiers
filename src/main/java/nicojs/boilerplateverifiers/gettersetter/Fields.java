package nicojs.boilerplateverifiers.gettersetter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Fields extends HashMap<String, Field> {
      public boolean hasFieldWithName(String fieldName) {
          return this.containsKey(fieldName);
      }
}
