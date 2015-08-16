package nicojs.boilerplateverifiers.examples.errors;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Represents a ErrorMapContainer
 * Created by nicojs on 8/16/2015.
 */
public class ErrorMapContainer {
    private Map map;
    private SortedMap sortedMap;
    private NavigableMap navigableMap;
    private ConcurrentNavigableMap concurrentNavigableMap;
    private EnumMap enumMap;
    private ConcurrentHashMap concurrentHashMap;
    private HashMap hashMap;
    private Hashtable hashtable;
    private LinkedHashMap linkedHashMap;
    private Properties properties;
    private TreeMap treeMap;
    private WeakHashMap weakHashMap;

    @java.beans.ConstructorProperties({"map", "sortedMap", "navigableMap", "concurrentNavigableMap", "enumMap", "concurrentHashMap", "hashMap", "hashtable", "linkedHashMap", "properties", "treeMap", "weakHashMap"})
    ErrorMapContainer(Map map, SortedMap sortedMap, NavigableMap navigableMap, ConcurrentNavigableMap concurrentNavigableMap, EnumMap enumMap, ConcurrentHashMap concurrentHashMap, HashMap hashMap, Hashtable hashtable, LinkedHashMap linkedHashMap, Properties properties, TreeMap treeMap, WeakHashMap weakHashMap) {
        this.map = map;
        this.sortedMap = sortedMap;
        this.navigableMap = navigableMap;
        this.concurrentNavigableMap = concurrentNavigableMap;
        this.enumMap = enumMap;
        this.concurrentHashMap = concurrentHashMap;
        this.hashMap = hashMap;
        this.hashtable = hashtable;
        this.linkedHashMap = linkedHashMap;
        this.properties = properties;
        this.treeMap = treeMap;
        this.weakHashMap = weakHashMap;
    }

    public static ErrorMapContainerBuilder builder() {
        return new ErrorMapContainerBuilder();
    }

    public static class ErrorMapContainerBuilder {
        private Map map;
        private SortedMap sortedMap;
        private NavigableMap navigableMap;
        private ConcurrentNavigableMap concurrentNavigableMap;
        private EnumMap enumMap;
        private ConcurrentHashMap concurrentHashMap;
        private HashMap hashMap;
        private Hashtable hashtable;
        private LinkedHashMap linkedHashMap;
        private Properties properties;
        private TreeMap treeMap;
        private WeakHashMap weakHashMap;

        ErrorMapContainerBuilder() {
        }

        public ErrorMapContainer.ErrorMapContainerBuilder map(Map map) {
            this.map = map;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder sortedMap(SortedMap sortedMap) {
            this.sortedMap = sortedMap;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder navigableMap(NavigableMap navigableMap) {
            this.navigableMap = navigableMap;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder concurrentNavigableMap(ConcurrentNavigableMap concurrentNavigableMap) {
            this.concurrentNavigableMap = concurrentNavigableMap;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder enumMap(EnumMap enumMap) {
            this.enumMap = enumMap;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder concurrentHashMap(ConcurrentHashMap concurrentHashMap) {
            this.concurrentHashMap = concurrentHashMap;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder hashMap(HashMap hashMap) {
            this.map = hashMap; // Error! Wrong assignment
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder hashtable(Hashtable hashtable) {
            this.hashtable = hashtable;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder linkedHashMap(LinkedHashMap linkedHashMap) {
            this.linkedHashMap = linkedHashMap;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder treeMap(TreeMap treeMap) {
            this.treeMap = treeMap;
            return this;
        }

        public ErrorMapContainer.ErrorMapContainerBuilder weakHashMap(WeakHashMap weakHashMap) {
            this.weakHashMap = weakHashMap;
            return this;
        }

        public ErrorMapContainer build() {
            return new ErrorMapContainer(map, sortedMap, navigableMap, concurrentNavigableMap, enumMap, concurrentHashMap, hashMap, hashtable, linkedHashMap, properties, treeMap, weakHashMap);
        }

        public String toString() {
            return "nicojs.boilerplateverifiers.examples.errors.ErrorMapContainer.ErrorMapContainerBuilder(map=" + this.map + ", sortedMap=" + this.sortedMap + ", navigableMap=" + this.navigableMap + ", concurrentNavigableMap=" + this.concurrentNavigableMap + ", enumMap=" + this.enumMap + ", concurrentHashMap=" + this.concurrentHashMap + ", hashMap=" + this.hashMap + ", hashtable=" + this.hashtable + ", linkedHashMap=" + this.linkedHashMap + ", properties=" + this.properties + ", treeMap=" + this.treeMap + ", weakHashMap=" + this.weakHashMap + ")";
        }
    }
}
