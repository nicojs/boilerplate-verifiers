package nicojs.boilerplateverifiers.examples.errors;

import java.util.BitSet;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Represents a SetContainer
 * Created by nicojs on 8/17/2015.
 */
public class ErrorSetContainer {
    private final Set set;
    private final HashSet hashSet;
    private final SortedSet sortedSet;
    private final NavigableSet navigableSet;
    private final CopyOnWriteArraySet copyOnWriteArraySet;
    private final TreeSet treeSet;
    private final EnumSet enumSet;
    private final BitSet bitSet;

    @java.beans.ConstructorProperties({"set", "hashSet", "sortedSet", "navigableSet", "copyOnWriteArraySet", "treeSet", "enumSet", "bitSet"})
    ErrorSetContainer(Set set, HashSet hashSet, SortedSet sortedSet, NavigableSet navigableSet, CopyOnWriteArraySet copyOnWriteArraySet, TreeSet treeSet, EnumSet enumSet, BitSet bitSet) {
        this.set = hashSet; // Error! Wrong assignment
        this.hashSet = hashSet;
        this.sortedSet = sortedSet;
        this.navigableSet = navigableSet;
        this.copyOnWriteArraySet = copyOnWriteArraySet;
        this.treeSet = treeSet;
        this.enumSet = enumSet;
        this.bitSet = bitSet;
    }

    public static ErrorSetContainerBuilder builder() {
        return new ErrorSetContainerBuilder();
    }

    public static class ErrorSetContainerBuilder {
        private Set set;
        private HashSet hashSet;
        private SortedSet sortedSet;
        private NavigableSet navigableSet;
        private CopyOnWriteArraySet copyOnWriteArraySet;
        private TreeSet treeSet;
        private EnumSet enumSet;
        private BitSet bitSet;

        ErrorSetContainerBuilder() {
        }

        public ErrorSetContainer.ErrorSetContainerBuilder set(Set set) {
            this.set = set;
            return this;
        }

        public ErrorSetContainer.ErrorSetContainerBuilder hashSet(HashSet hashSet) {
            this.hashSet = hashSet;
            return this;
        }

        public ErrorSetContainer.ErrorSetContainerBuilder sortedSet(SortedSet sortedSet) {
            this.sortedSet = sortedSet;
            return this;
        }

        public ErrorSetContainer.ErrorSetContainerBuilder navigableSet(NavigableSet navigableSet) {
            this.navigableSet = navigableSet;
            return this;
        }

        public ErrorSetContainer.ErrorSetContainerBuilder copyOnWriteArraySet(CopyOnWriteArraySet copyOnWriteArraySet) {
            this.copyOnWriteArraySet = copyOnWriteArraySet;
            return this;
        }

        public ErrorSetContainer.ErrorSetContainerBuilder treeSet(TreeSet treeSet) {
            this.treeSet = treeSet;
            return this;
        }

        public ErrorSetContainer.ErrorSetContainerBuilder enumSet(EnumSet enumSet) {
            this.enumSet = enumSet;
            return this;
        }

        public ErrorSetContainer.ErrorSetContainerBuilder bitSet(BitSet bitSet) {
            this.bitSet = bitSet;
            return this;
        }

        public ErrorSetContainer build() {
            return new ErrorSetContainer(set, hashSet, sortedSet, navigableSet, copyOnWriteArraySet, treeSet, enumSet, bitSet);
        }

        public String toString() {
            return "nicojs.boilerplateverifiers.examples.errors.ErrorSetContainer.ErrorSetContainerBuilder(set=" + this.set + ", hashSet=" + this.hashSet + ", sortedSet=" + this.sortedSet + ", navigableSet=" + this.navigableSet + ", copyOnWriteArraySet=" + this.copyOnWriteArraySet + ", treeSet=" + this.treeSet + ", enumSet=" + this.enumSet + ", bitSet=" + this.bitSet + ")";
        }
    }
}
