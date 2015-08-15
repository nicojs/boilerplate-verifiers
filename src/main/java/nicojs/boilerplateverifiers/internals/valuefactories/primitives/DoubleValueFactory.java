package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.internals.ValueFactory;

/**
 * Represents a DoubleValueFactory
 * Created by nicojs on 8/13/2015.
 */
public class DoubleValueFactory extends ValueFactory{
    private double seed = 0;

    public DoubleValueFactory() {
        super(double.class);
    }

    @Override
    public Object next(){
        return seed++;
    }
}
