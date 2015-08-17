package nicojs.boilerplateverifiers.internals.valuefactories.sets;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.HashSet;

/**
 * Represents a HashsetValueFactory
 * Created by nicojs on 8/17/2015.
 */
public class HashSetValueFactory extends CollectionValueFactory<HashSet> {
    public HashSetValueFactory() {
        super(HashSet.class, new Producer<HashSet>() {
            @Override
            public HashSet produce() {
                return new HashSet();
            }
        });
    }
}
