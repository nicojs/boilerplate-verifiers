package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;
import nicojs.boilerplateverifiers.examples.entities.SwitchValue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a ListContainer
 * Created by nicojs on 8/15/2015.
 */
@Builder
public class CollectionContainer {
    private final List<String> names;
    private final LinkedList<Integer> numbers;
    private final CopyOnWriteArrayList<Person> persons;
    private final ArrayList<SwitchValue> switches;
}

