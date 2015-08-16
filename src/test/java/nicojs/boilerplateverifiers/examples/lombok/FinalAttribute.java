package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;
import lombok.Value;
import nicojs.boilerplateverifiers.examples.entities.FinalPropertyBag;

/**
 * Represents a FinalAttribute
 * Created by nicojs on 8/16/2015.
 */
@Builder
@Value
public class FinalAttribute {
    private FinalPropertyBag finalPropertyBag;
}
