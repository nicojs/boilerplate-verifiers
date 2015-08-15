package nicojs.boilerplateverifiers.internals;

import nicojs.boilerplateverifiers.internals.valuefactories.collections.ArrayListValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CopyOnWriteArrayListValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.LinkedListValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.ListValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.primitives.*;

/**
 * Represents a JavaValueFactoryArchitect
 * Created by nicojs on 8/13/2015.
 */
public class JavaValueFactoryArchitect {
    private JavaValueFactoryArchitect(){}

    public static void fill(ValueFactories valueFactories){
        fillPrimitiveClasses(valueFactories);
        fillCollectionClasses(valueFactories);
    }

    private static void fillCollectionClasses(ValueFactories valueFactories) {
        valueFactories.putIfNotExists(
                new ArrayListValueFactory(),
                new ListValueFactory(),
                new LinkedListValueFactory(),
                new CopyOnWriteArrayListValueFactory());
    }

    private static void fillPrimitiveClasses(ValueFactories valueFactories) {
        valueFactories.putIfNotExists(
                new BooleanValueFactory(),
                new ByteValueFactory(),
                new CharValueFactory(),
                new ClassValueFactory(),
                new DoubleValueFactory(),
                new FloatValueFactory(),
                new IntValueFactory(),
                new LongValueFactory(),
                new ShortValueFactory(),
                new StringValueFactory());
    }
}
