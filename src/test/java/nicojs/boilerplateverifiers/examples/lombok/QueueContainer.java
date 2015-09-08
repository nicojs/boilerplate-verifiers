package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Represents a QueueContainer
 * Created by nicojs on 8/18/2015.
 */
@Builder
public class QueueContainer {
    private final Queue queue;
    private final BlockingQueue blockingQueue;
    private final Deque deque;
    private final ArrayBlockingQueue arrayBlockingQueue;
    private final ConcurrentLinkedDeque concurrentLinkedDeque;
    private final DelayQueue delayQueue;
    private final LinkedBlockingQueue linkedBlockingQueue;
    private final PriorityBlockingQueue priorityQueue;
    private final SynchronousQueue synchronousQueue;
}
