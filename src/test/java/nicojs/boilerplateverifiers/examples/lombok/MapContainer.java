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
    private final Map map;
    private final SortedMap sortedMap;
    private final NavigableMap navigableMap;
    private final ConcurrentNavigableMap concurrentNavigableMap;
    private final EnumMap enumMap;
    private final ConcurrentHashMap concurrentHashMap;
    private final HashMap hashMap;
    private final Hashtable hashtable;
    private final LinkedHashMap linkedHashMap;
    private final Properties properties;
    private final TreeMap treeMap;
    private final WeakHashMap weakHashMap;

}
