package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Represents a CharValueFactory
 * Created by nicojs on 8/16/2015.
 */
public class CharValueFactory extends ValueFactory<Character> {

    private char seed = Character.MIN_VALUE;

    public CharValueFactory() {
        super(char.class);
    }


    @Override
    public Character next(GraphCreationContext graphCreationContext) {
        return seed++;
    }
}
