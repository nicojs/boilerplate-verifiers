package nicojs.boilerplateverifiers.examples.tostring;

import lombok.ToString;

@ToString(callSuper = true)
public class InheritanceParentWithoutToString extends PojoWithoutToString {
    private String name;
}
