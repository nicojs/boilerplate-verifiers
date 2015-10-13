package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a AtomicIntegerValueFactory
 * Created by nicojs on 8/16/2015.
 */
public class AtomicIntegerValueFactory extends ValueFactory<AtomicInteger> {

    private int seed = Integer.MIN_VALUE;

    public AtomicIntegerValueFactory() {
        super(AtomicInteger.class);
    }

    @Override
    public AtomicInteger next(GraphCreationContext graphCreationContext) {
        return new AtomicInteger(seed++);
    }
}
