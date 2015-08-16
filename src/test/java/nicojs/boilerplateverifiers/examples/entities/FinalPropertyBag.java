package nicojs.boilerplateverifiers.examples.entities;

import lombok.Value;

/**
 * Represents a Final
 * Created by nicojs on 8/16/2015.
 */
@Value
public final class FinalPropertyBag {
    private final String string;
    private final int number;
}
