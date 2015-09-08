package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;

/**
 * Represents a ClassWithAttributeWichHasStaticField
 * Created by nicojs on 8/16/2015.
 */
@Builder
public class ClassWithAttributeWichHasStaticField {

    private final ClassWithStaticField field;

    class ClassWithStaticField{
        final static int A_STATIC_FIELD = 1;
    }
}
