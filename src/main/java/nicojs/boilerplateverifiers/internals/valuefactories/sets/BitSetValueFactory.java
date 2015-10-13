package nicojs.boilerplateverifiers.internals.valuefactories.sets;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

import java.util.BitSet;
import java.util.Random;

/**
 * Represents a BitSetValueFactory
 * Created by nicojs on 8/17/2015.
 */
public class BitSetValueFactory extends ValueFactory<BitSet> {

    public static final int MAX_BITS = 100;
    private int seed;
    private static final Random RANDOM = new Random();

    public BitSetValueFactory() {
        super(BitSet.class);
        seed = 0;
    }

    @Override
    public BitSet next(GraphCreationContext graphCreationContext) {
        BitSet next = new BitSet(100);
        if (seed < 100) {
            next.set(seed);
            seed++;
        } else {
            next.set(RANDOM.nextInt(MAX_BITS));
        }
        return next;
    }
}
