package nicojs.boilerplateverifiers.internals.valuefactories.sets;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Represents a SortedSetValueFactory
 * Created by nicojs on 8/17/2015.
 */
public class SortedSetValueFactory extends CollectionValueFactory<SortedSet> {
    public SortedSetValueFactory() {
        super(SortedSet.class, new Producer<SortedSet>() {
            @Override
            public SortedSet produce() {
                return new TreeSet();
            }
        });
    }
}
