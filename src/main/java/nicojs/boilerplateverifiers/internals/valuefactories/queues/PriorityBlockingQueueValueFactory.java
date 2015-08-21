package nicojs.boilerplateverifiers.internals.valuefactories.queues;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Represents a PriorityBlockingQueueValueFactory
 * Created by nicojs on 8/19/2015.
 */
public class PriorityBlockingQueueValueFactory extends CollectionValueFactory<PriorityBlockingQueue> {
    public PriorityBlockingQueueValueFactory() {
        super(PriorityBlockingQueue.class, new Producer<PriorityBlockingQueue>() {
            @Override
            public PriorityBlockingQueue produce() {
                return new PriorityBlockingQueue(1);
            }
        });
    }
}
