package nicojs.boilerplateverifiers.examples.manual;

import lombok.Builder;

/**
 * Represents a ClassWithWeirdGetter
 * Created by nicojs on 9/2/2015.
 */
@Builder
public class ClassWithWeirdGetter {
    private String var1;

    public String getVar1(){
        return "sdasd";
    }
}
