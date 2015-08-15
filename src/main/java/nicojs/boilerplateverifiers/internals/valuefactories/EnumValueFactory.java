package nicojs.boilerplateverifiers.internals.valuefactories;

/**
 * Represents a EnumValueFactory
 * Created by nicojs on 8/15/2015.
 */
public class EnumValueFactory<T extends Enum> extends ChoiceValueFactory<T> {

    @SuppressWarnings("unchecked")
    public EnumValueFactory(Class targetClass) {
        super(targetClass, (T[]) targetClass.getEnumConstants());
    }
}
