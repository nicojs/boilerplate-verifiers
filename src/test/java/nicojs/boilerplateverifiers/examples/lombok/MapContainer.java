package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Represents a MapContainer
 * Created by nicojs on 8/16/2015.
 */
@Builder
public class MapContainer {
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

}
