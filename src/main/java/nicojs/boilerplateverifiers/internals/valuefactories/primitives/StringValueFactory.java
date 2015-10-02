package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Represents a StringValueFactory
 * Created by nicojs on 8/13/2015.
 */
public class StringValueFactory extends ValueFactory<String> {
    private int seed = 0;

    public StringValueFactory() {
        super(String.class);
    }


    @Override
    public String next(GraphCreationContext graphCreationContext) {
        return String.format("String$%s", Integer.toString(seed++));
    }
}
