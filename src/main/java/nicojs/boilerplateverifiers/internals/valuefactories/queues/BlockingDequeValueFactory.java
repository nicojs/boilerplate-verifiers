package nicojs.boilerplateverifiers.internals.valuefactories.queues;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Represents a BlockingDequeValueFactory
 * Created by nicojs on 8/19/2015.
 */
public class BlockingDequeValueFactory extends CollectionValueFactory<BlockingDeque> {
    public BlockingDequeValueFactory() {
        super(BlockingDeque.class, new Producer<BlockingDeque>() {
            @Override
            public BlockingDeque produce() {
                return new LinkedBlockingDeque(1);
            }
        });
    }
}
