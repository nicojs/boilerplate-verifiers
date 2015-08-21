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
    private Queue queue;
    private BlockingQueue blockingQueue;
    private Deque deque;
    private ArrayBlockingQueue arrayBlockingQueue;
    private ConcurrentLinkedDeque concurrentLinkedDeque;
    private DelayQueue delayQueue;
    private LinkedBlockingQueue linkedBlockingQueue;
    private PriorityBlockingQueue priorityQueue;
    private SynchronousQueue synchronousQueue;
}
