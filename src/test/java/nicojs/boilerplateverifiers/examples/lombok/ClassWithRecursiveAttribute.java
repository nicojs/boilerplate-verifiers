package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;
import nicojs.boilerplateverifiers.examples.entities.RecursiveAttribute;

/**
 * Represents a ClassWithRecursiveAttribute
 * Created by nicojs on 8/21/2015.
 */
@Builder
public class ClassWithRecursiveAttribute {

    private RecursiveAttribute recursiveAttribute;
}
