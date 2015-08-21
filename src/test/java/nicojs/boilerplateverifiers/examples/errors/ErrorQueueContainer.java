package nicojs.boilerplateverifiers.examples.errors;

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
public class ErrorQueueContainer {
    private Queue queue;
    private BlockingQueue blockingQueue;
    private Deque deque;
    private ArrayBlockingQueue arrayBlockingQueue;
    private ConcurrentLinkedDeque concurrentLinkedDeque;
    private DelayQueue delayQueue;
    private LinkedBlockingQueue linkedBlockingQueue;
    private PriorityBlockingQueue priorityQueue;
    private SynchronousQueue synchronousQueue;

    @java.beans.ConstructorProperties({"queue", "blockingQueue", "deque", "arrayBlockingQueue", "concurrentLinkedDeque", "delayQueue", "linkedBlockingQueue", "priorityQueue", "synchronousQueue"})
    ErrorQueueContainer(Queue queue, BlockingQueue blockingQueue, Deque deque, ArrayBlockingQueue arrayBlockingQueue, ConcurrentLinkedDeque concurrentLinkedDeque, DelayQueue delayQueue, LinkedBlockingQueue linkedBlockingQueue, PriorityBlockingQueue priorityQueue, SynchronousQueue synchronousQueue) {
        this.queue = deque; // ERROR! Wrong assignment
        this.blockingQueue = blockingQueue;
        this.deque = deque;
        this.arrayBlockingQueue = arrayBlockingQueue;
        this.concurrentLinkedDeque = concurrentLinkedDeque;
        this.delayQueue = delayQueue;
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.priorityQueue = priorityQueue;
        this.synchronousQueue = synchronousQueue;
    }

    public static ErrorQueueContainerBuilder builder() {
        return new ErrorQueueContainerBuilder();
    }

    public static class ErrorQueueContainerBuilder {
        private Queue queue;
        private BlockingQueue blockingQueue;
        private Deque deque;
        private ArrayBlockingQueue arrayBlockingQueue;
        private ConcurrentLinkedDeque concurrentLinkedDeque;
        private DelayQueue delayQueue;
        private LinkedBlockingQueue linkedBlockingQueue;
        private PriorityBlockingQueue priorityQueue;
        private SynchronousQueue synchronousQueue;

        ErrorQueueContainerBuilder() {
        }

        public ErrorQueueContainer.ErrorQueueContainerBuilder queue(Queue queue) {
            this.queue = queue;
            return this;
        }

        public ErrorQueueContainer.ErrorQueueContainerBuilder blockingQueue(BlockingQueue blockingQueue) {
            this.blockingQueue = blockingQueue;
            return this;
        }

        public ErrorQueueContainer.ErrorQueueContainerBuilder deque(Deque deque) {
            this.deque = deque;
            return this;
        }

        public ErrorQueueContainer.ErrorQueueContainerBuilder arrayBlockingQueue(ArrayBlockingQueue arrayBlockingQueue) {
            this.arrayBlockingQueue = arrayBlockingQueue;
            return this;
        }

        public ErrorQueueContainer.ErrorQueueContainerBuilder concurrentLinkedDeque(ConcurrentLinkedDeque concurrentLinkedDeque) {
            this.concurrentLinkedDeque = concurrentLinkedDeque;
            return this;
        }

        public ErrorQueueContainer.ErrorQueueContainerBuilder delayQueue(DelayQueue delayQueue) {
            this.delayQueue = delayQueue;
            return this;
        }

        public ErrorQueueContainer.ErrorQueueContainerBuilder linkedBlockingQueue(LinkedBlockingQueue linkedBlockingQueue) {
            this.linkedBlockingQueue = linkedBlockingQueue;
            return this;
        }

        public ErrorQueueContainer.ErrorQueueContainerBuilder priorityQueue(PriorityBlockingQueue priorityQueue) {
            this.priorityQueue = priorityQueue;
            return this;
        }

        public ErrorQueueContainer.ErrorQueueContainerBuilder synchronousQueue(SynchronousQueue synchronousQueue) {
            this.synchronousQueue = synchronousQueue;
            return this;
        }

        public ErrorQueueContainer build() {
            return new ErrorQueueContainer(queue, blockingQueue, deque, arrayBlockingQueue, concurrentLinkedDeque, delayQueue, linkedBlockingQueue, priorityQueue, synchronousQueue);
        }

        public String toString() {
            return "nicojs.boilerplateverifiers.examples.errors.ErrorQueueContainer.ErrorQueueContainerBuilder(queue=" + this.queue + ", blockingQueue=" + this.blockingQueue + ", deque=" + this.deque + ", arrayBlockingQueue=" + this.arrayBlockingQueue + ", concurrentLinkedDeque=" + this.concurrentLinkedDeque + ", delayQueue=" + this.delayQueue + ", linkedBlockingQueue=" + this.linkedBlockingQueue + ", priorityQueue=" + this.priorityQueue + ", synchronousQueue=" + this.synchronousQueue + ")";
        }
    }
}
