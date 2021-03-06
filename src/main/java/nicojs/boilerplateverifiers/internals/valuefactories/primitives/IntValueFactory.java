package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Represents a IntValueFactory
 * Created by nicojs on 8/13/2015.
 */
public class IntValueFactory extends ValueFactory<Integer> {
    private static int seed = Integer.MIN_VALUE;

    public IntValueFactory() {
        super(int.class);
    }

    @Override
    public Integer next(GraphCreationContext graphCreationContext){
        return seed++;
    }
}
