package nicojs.boilerplateverifiers.examples.tostring;

import lombok.ToString;

@ToString(callSuper = true)
public class InheritanceWithToString extends PojoWithToString {
    private String name;
}
