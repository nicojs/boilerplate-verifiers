package nicojs.boilerplateverifiers.internals;

import nicojs.boilerplateverifiers.internals.valuefactories.collections.ArrayListValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.CopyOnWriteArrayListValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.LinkedListValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.collections.ListValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.maps.*;
import nicojs.boilerplateverifiers.internals.valuefactories.primitives.*;
import nicojs.boilerplateverifiers.internals.valuefactories.queues.*;
import nicojs.boilerplateverifiers.internals.valuefactories.sets.BitSetValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.sets.CopyOnWriteArraySetValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.sets.EnumSetValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.sets.HashSetValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.sets.NavigableSetValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.sets.SetValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.sets.SortedSetValueFactory;
import nicojs.boilerplateverifiers.internals.valuefactories.sets.TreeSetValueFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a JavaValueFactoryArchitect
 * Created by nicojs on 8/13/2015.
 */
@SuppressWarnings("unchecked")
public class JavaValueFactoryArchitect {
    private JavaValueFactoryArchitect() {
    }

    public static void fill(ValueProvider valueProvider) {
        fillPrimitiveClasses(valueProvider);
        fillCollectionClasses(valueProvider);
        fillMapClasses(valueProvider);
        fillSetClasses(valueProvider);
        fillQueueClasses(valueProvider);
    }

    private static void fillMapClasses(ValueProvider valueProvider) {
        valueProvider.addValueFactory(
                new ConcurrentHashMapValueFactory(),
                new ConcurrentNavigableMapValueFactory(),
                new EnumMapValueFactory(),
                new HashMapValueFactory(),
                new HashtableValueFactory(),
                new LinkedHashMapValueFactory(),
                new NavigableMapValueFactory(),
                new PropertiesValueFactory(),
                new SortedMapValueFactory(),
                new TreeMapValueFactory(),
                new WeakHashMapValueFactory(),
                new MapValueFactory(Map.class, new Producer() {
                    @Override
                    public Object produce() {
                        return new HashMap();
                    }
                })
        );
    }

    private static void fillCollectionClasses(ValueProvider valueProvider) {
        valueProvider.addValueFactory(
                new ArrayListValueFactory(),
                new ListValueFactory(),
                new LinkedListValueFactory(),
                new CopyOnWriteArrayListValueFactory());
    }

    private static void fillPrimitiveClasses(ValueProvider valueProvider) {
        valueProvider.addValueFactory(
                new AtomicIntegerValueFactory(),
                new BooleanValueFactory(),
                new ByteValueFactory(),
                new CharValueFactory(),
                new ClassValueFactory(),
                new DoubleValueFactory(),
                new FloatValueFactory(),
                new IntValueFactory(),
                new LongValueFactory(),
                new ShortValueFactory(),
                new StringValueFactory());
    }

    private static void fillSetClasses(ValueProvider valueProvider) {
        valueProvider.addValueFactory(
                new BitSetValueFactory(),
                new CopyOnWriteArraySetValueFactory(),
                new EnumSetValueFactory(),
                new HashSetValueFactory(),
                new NavigableSetValueFactory(),
                new SetValueFactory(),
                new SortedSetValueFactory(),
                new TreeSetValueFactory()
        );
    }

    private static void fillQueueClasses(ValueProvider valueProvider) {
        valueProvider.addValueFactory(
                new ArrayBlockingQueueValueFactory(),
                new BlockingDequeValueFactory(),
                new BlockingQueueValueFactory(),
                new ConcurrentLinkedQueueValueFactory(),
                new ConcurrentLinkedDequeueValueFactory(),
                new DelayQueueValueFactory(),
                new DequeValueFactory(),
                new LinkedBlockingQueueValueFactory(),
                new PriorityBlockingQueueValueFactory(),
                new QueueValueFactory(),
                new SynchronousQueueValueFactory()
        );
    }
}
