package nicojs.boilerplateverifiers.internals.valuefactories.maps;

import nicojs.boilerplateverifiers.internals.Producer;

import java.util.Hashtable;

/**
 * Represents a HashtableValueFactory
 * Created by nicojs on 8/16/2015.
 */
public class HashtableValueFactory extends MapValueFactory<Hashtable> {

    public HashtableValueFactory() {
        super(Hashtable.class, new Producer<Hashtable>() {
            @Override
            public Hashtable produce() {
                return new Hashtable();
            }
        });
    }
}
