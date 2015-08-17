package nicojs.boilerplateverifiers.internals.valuefactories.sets;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a SetValueFactory
 * Created by nicojs on 8/17/2015.
 */
public class SetValueFactory extends CollectionValueFactory<Set> {
    public SetValueFactory() {
        super(Set.class, new Producer<Set>() {
            @Override
            public Set produce() {
                return new HashSet();
            }
        });
    }
}
