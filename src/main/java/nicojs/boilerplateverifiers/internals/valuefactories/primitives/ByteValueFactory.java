package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;

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
    public Byte next() {
        return seed++;
    }
}
