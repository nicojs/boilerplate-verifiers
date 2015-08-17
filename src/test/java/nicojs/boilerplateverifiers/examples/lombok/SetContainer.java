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
    private Set set;
    private SortedSet sortedSet;
    private NavigableSet navigableSet;
    private CopyOnWriteArraySet copyOnWriteArraySet;
    private TreeSet treeSet;
    private EnumSet enumSet;
    private BitSet bitSet;
}
