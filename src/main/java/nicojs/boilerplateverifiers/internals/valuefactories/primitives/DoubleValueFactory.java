package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Represents a DoubleValueFactory
 * Created by nicojs on 8/13/2015.
 */
public class DoubleValueFactory extends ValueFactory<Double>{
    private double seed = Double.MIN_VALUE;

    public DoubleValueFactory() {
        super(double.class);
    }

    @Override
    public Double next(GraphCreationContext graphCreationContext){
        return seed++;
    }
}
