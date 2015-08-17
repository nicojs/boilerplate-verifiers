package nicojs.boilerplateverifiers.internals.valuefactories.sets;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.TreeSet;

/**
 * Represents a TreeSetValueFactory
 * Created by nicojs on 8/17/2015.
 */
public class TreeSetValueFactory extends CollectionValueFactory<TreeSet> {
    public TreeSetValueFactory() {
        super(TreeSet.class, new Producer<TreeSet>() {
            @Override
            public TreeSet produce() {
                return new TreeSet();
            }
        });
    }
}
