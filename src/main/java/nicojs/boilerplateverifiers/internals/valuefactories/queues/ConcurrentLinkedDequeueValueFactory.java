package nicojs.boilerplateverifiers.internals.valuefactories.queues;

import nicojs.boilerplateverifiers.internals.Producer;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CollectionValueFactory;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Represents a ConcurrentLinkedQueueValueFactory
 * Created by nicojs on 8/19/2015.
 */
public class ConcurrentLinkedDequeueValueFactory extends CollectionValueFactory<ConcurrentLinkedDeque> {
    public ConcurrentLinkedDequeueValueFactory() {
        super(ConcurrentLinkedDeque.class, new Producer<ConcurrentLinkedDeque>() {
            @Override
            public ConcurrentLinkedDeque produce() {
                return new ConcurrentLinkedDeque();
            }
        });
    }
}
