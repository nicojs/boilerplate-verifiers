package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Represents a LongValueFactory
 * Created by nicojs on 8/16/2015.
 */
public class LongValueFactory extends ValueFactory<Long> {

    public long seed = Long.MIN_VALUE;

    public LongValueFactory() {
        super(long.class);
    }

    @Override
    public Long next(GraphCreationContext graphCreationContext) {
        return seed++;
    }
}
