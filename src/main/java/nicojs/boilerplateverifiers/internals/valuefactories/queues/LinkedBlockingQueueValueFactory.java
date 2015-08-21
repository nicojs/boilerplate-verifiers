package nicojs.boilerplateverifiers.internals.valuefactories.queues;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents a LinkedBlockingQueueValueFactory
 * Created by nicojs on 8/19/2015.
 */
public class LinkedBlockingQueueValueFactory extends CollectionValueFactory<LinkedBlockingQueue> {
    public LinkedBlockingQueueValueFactory() {
        super(LinkedBlockingQueue.class, new Producer<LinkedBlockingQueue>() {
            @Override
            public LinkedBlockingQueue produce() {
                return new LinkedBlockingQueue(1);
            }
        });
    }
}
