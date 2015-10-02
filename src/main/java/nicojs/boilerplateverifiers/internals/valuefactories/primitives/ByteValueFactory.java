package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Represents a ByteValueFactory
 * Created by nicojs on 8/16/2015.
 */
public class ByteValueFactory extends ValueFactory<Byte> {

    private byte seed = Byte.MIN_VALUE;

    public ByteValueFactory() {
        super(byte.class);
    }

    @Override
    public Byte next(GraphCreationContext graphCreationContext) {
        return seed++;
    }
}
