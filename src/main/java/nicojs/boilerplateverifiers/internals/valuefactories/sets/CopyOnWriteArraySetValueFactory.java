package nicojs.boilerplateverifiers.internals.valuefactories.sets;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Represents a CopyOnWriteArraySetValueFactory
 * Created by nicojs on 8/17/2015.
 */
public class CopyOnWriteArraySetValueFactory extends CollectionValueFactory<CopyOnWriteArraySet> {
    public CopyOnWriteArraySetValueFactory() {
        super(CopyOnWriteArraySet.class, new Producer<CopyOnWriteArraySet>() {
            @Override
            public CopyOnWriteArraySet produce() {
                return new CopyOnWriteArraySet();
            }
        });
    }
}
