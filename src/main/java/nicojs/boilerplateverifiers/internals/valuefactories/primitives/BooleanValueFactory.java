package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.internals.valuefactories.ChoiceValueFactory;

/**
 * Represents a BooleanValueFactory
 * Created by nicojs on 8/15/2015.
 */
public class BooleanValueFactory extends ChoiceValueFactory<Boolean> {
    public BooleanValueFactory() {
        super(boolean.class, new Boolean[]{true, false});
    }
}
