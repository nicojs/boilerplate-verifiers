package nicojs.boilerplateverifiers.examples.manual;

import lombok.Builder;
import nicojs.boilerplateverifiers.examples.entities.SuperClass;

/**
 * Represents a SubClass
 * Created by nicojs on 9/3/2015.
 */
@Builder
public class SubClass extends SuperClass {
    private String value2;

    public SubClass(String value2){
        super("value1");
        this.value2 = value2;
    }
}
