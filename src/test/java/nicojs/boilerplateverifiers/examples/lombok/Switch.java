package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;
import lombok.Value;
import nicojs.boilerplateverifiers.examples.entities.SwitchValue;

/**
 * Represents a Switch
 * Created by nicojs on 8/14/2015.
 */
@Value
@Builder
public class Switch {

    private SwitchValue value;


}
