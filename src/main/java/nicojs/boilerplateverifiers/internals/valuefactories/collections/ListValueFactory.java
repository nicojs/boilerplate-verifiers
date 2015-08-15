package nicojs.boilerplateverifiers.internals.valuefactories.collections;

import nicojs.boilerplateverifiers.internals.Producer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ListValueFactory
 * Created by nicojs on 8/15/2015.
 */
public class ListValueFactory extends CollectionValueFactory<List> {

    public ListValueFactory() {
        super(List.class, new Producer<List>() {
            @Override
            public List produce() {
                return new ArrayList(1);
            }
        });
    }
}
