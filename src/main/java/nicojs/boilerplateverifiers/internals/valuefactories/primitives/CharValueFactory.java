package nicojs.boilerplateverifiers.internals.valuefactories.primitives;

import nicojs.boilerplateverifiers.internals.ValueFactory;

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
    public Character next() {
        return seed++;
    }
}
