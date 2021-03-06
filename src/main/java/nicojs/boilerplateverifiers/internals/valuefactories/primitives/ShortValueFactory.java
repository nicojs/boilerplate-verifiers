package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Represents a ShortValueFactory
 * Created by nicojs on 8/16/2015.
 */
public class ShortValueFactory extends ValueFactory<Short> {

    private short seed = Short.MIN_VALUE;

    public ShortValueFactory() {
        super(short.class);
    }

    @Override
    public Short next(GraphCreationContext graphCreationContext) {
        return seed++;
    }
}
