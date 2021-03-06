package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Represents a FloatValueFactory
 * Created by nicojs on 8/16/2015.
 */
public class FloatValueFactory extends ValueFactory<Float> {
    private float seed = Float.MIN_VALUE;

    public FloatValueFactory() {
        super(float.class);
    }

    @Override
    public Float next(GraphCreationContext graphCreationContext) {
        return seed++;
    }
}
