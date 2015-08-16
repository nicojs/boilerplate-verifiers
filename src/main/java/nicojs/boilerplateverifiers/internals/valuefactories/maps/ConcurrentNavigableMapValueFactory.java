package nicojs.boilerplateverifiers.internals.valuefactories.maps;

import nicojs.boilerplateverifiers.internals.Producer;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Represents a ConcurrentNavigableMapValueFactory
 * Created by nicojs on 8/16/2015.
 */
public class ConcurrentNavigableMapValueFactory extends MapValueFactory<ConcurrentNavigableMap> {

    public ConcurrentNavigableMapValueFactory() {
        super(ConcurrentNavigableMap.class, new Producer<ConcurrentNavigableMap>() {
            @Override
            public ConcurrentNavigableMap produce() {
                return new ConcurrentSkipListMap();
            }
        });
    }
}
