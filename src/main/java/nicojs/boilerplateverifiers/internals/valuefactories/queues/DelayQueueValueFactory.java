package nicojs.boilerplateverifiers.internals.valuefactories.queues;

import nicojs.boilerplateverifiers.ValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

import java.util.concurrent.DelayQueue;

/**
 * Represents a DelayQueueValueFactory
 * Created by nicojs on 8/19/2015.
 */
public class DelayQueueValueFactory extends ValueFactory<DelayQueue> {
    public DelayQueueValueFactory() {
        super(DelayQueue.class);
    }

    @Override
    public DelayQueue next(GraphCreationContext graphCreationContext) {
        return new DelayQueue(); // New DelayQueue is always unique
    }
}
