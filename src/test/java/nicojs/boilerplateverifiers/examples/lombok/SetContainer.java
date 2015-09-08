package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;

import java.util.BitSet;
import java.util.EnumSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Represents a SetContainer
 * Created by nicojs on 8/17/2015.
 */
@Builder
public class SetContainer {
    private final Set set;
    private final SortedSet sortedSet;
    private final NavigableSet navigableSet;
    private final CopyOnWriteArraySet copyOnWriteArraySet;
    private final TreeSet treeSet;
    private final EnumSet enumSet;
    private final BitSet bitSet;
}
