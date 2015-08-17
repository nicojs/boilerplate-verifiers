package nicojs.boilerplateverifiers.internals.valuefactories.sets;

import nicojs.boilerplateverifiers.internals.valuefactories.ChoiceValueFactory;

import java.util.EnumSet;

/**
 * Represents a EnumSetValueFactory
 * Created by nicojs on 8/17/2015.
 */
public class EnumSetValueFactory extends ChoiceValueFactory {
    private static final EnumSet[] ENUM_SETS = new EnumSet[]{
            EnumSet.of(Number.ONE),
            EnumSet.of(Number.TWO),
            EnumSet.of(Number.THREE),
            EnumSet.of(Number.FOUR),
            EnumSet.of(Number.FIVE),
            EnumSet.of(Number.SIX),
            EnumSet.of(Number.SEVEN),
            EnumSet.of(Number.EIGHT),
            EnumSet.of(Number.NINE),
            EnumSet.of(Number.TEN),
    };

    public EnumSetValueFactory() {
        super(EnumSet.class, ENUM_SETS);
    }

    private enum Number{
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN
    }
}
