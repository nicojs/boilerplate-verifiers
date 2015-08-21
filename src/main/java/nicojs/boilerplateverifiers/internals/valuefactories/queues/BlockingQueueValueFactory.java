package nicojs.boilerplateverifiers.internals.valuefactories.queues;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Represents a BlockingQueueValueFactory
 * Created by nicojs on 8/19/2015.
 */
public class BlockingQueueValueFactory extends CollectionValueFactory<BlockingQueue> {
    public BlockingQueueValueFactory() {
        super(BlockingQueue.class, new Producer<BlockingQueue>() {
            @Override
            public BlockingQueue produce() {
                return new ArrayBlockingQueue(1);
            }
        });
    }
}
