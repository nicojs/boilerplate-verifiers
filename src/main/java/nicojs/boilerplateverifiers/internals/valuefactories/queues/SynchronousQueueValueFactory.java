package nicojs.boilerplateverifiers.internals.valuefactories.queues;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

import java.util.concurrent.SynchronousQueue;

/**
 * Represents a SynchronousQueueValueFactory
 * Created by nicojs on 8/19/2015.
 */
public class SynchronousQueueValueFactory extends ValueFactory<SynchronousQueue> {
    public SynchronousQueueValueFactory() {
        super(SynchronousQueue.class);
    }

    @Override
    public SynchronousQueue next(GraphCreationContext graphCreationContext) {
        return new SynchronousQueue(); // Always unique
    }
}
