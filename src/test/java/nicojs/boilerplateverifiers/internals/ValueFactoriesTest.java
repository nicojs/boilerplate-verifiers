package nicojs.boilerplateverifiers.internals;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Represents a ValueFactoriesTest
 * Created by nicojs on 8/17/2015.
 */
public class ValueFactoriesTest {

    private ValueFactories sut;

    @Before
    public void initializeSut() {
        sut = new ValueFactories();
        JavaValueFactoryArchitect.fill(sut);
    }

    @Test
    public void provideNextValue_first100ValuesOfListClasses_areUnique() throws Exception {
        for (Class clazz : new Class[]{
                ArrayList.class,
                List.class,
                CopyOnWriteArrayList.class,
                LinkedList.class,
        }) {
            assertFirst100ValuesAreUnique(clazz);
        }
    }

    @Test
    public void provideNextValue_first2ValuesOfBoolean_areUnique(){
        assertValuesAreUnique(boolean.class, 2);
    }

    @Test
    public void provideNextValue_first10ValuesOfClass_areUnique(){
        assertValuesAreUnique(Class.class, 10);
    }

    @Test
    public void provideNextValue_first100ValuesOfPrimitiveClasses_areUnique() throws Exception {
        for (Class clazz : new Class[]{
                AtomicInteger.class,
                char.class,
                Character.class,
                Double.class,
                double.class,
                float.class,
                Float.class,
                Integer.class,
                int.class,
                Long.class,
                long.class,
                Short.class,
                short.class,
                String.class
        }) {
            assertFirst100ValuesAreUnique(clazz);
        }

    }

    @Test
    public void provideNextValue_first100ValuesOfMapClasses_areUnique() {
        for (Class clazz : new Class[]{ConcurrentHashMap.class,
                EnumMap.class,
                HashMap.class,
                Hashtable.class,
                LinkedHashMap.class,
                Map.class,
                NavigableMap.class,
                Properties.class,
                SortedMap.class,
                TreeMap.class,
                WeakHashMap.class}) {
            assertFirst100ValuesAreUnique(clazz);
        }
    }

    private void assertFirst100ValuesAreUnique(Class clazz) {
        assertValuesAreUnique(clazz, 100);
    }

    @SuppressWarnings("unchecked")
    private void assertValuesAreUnique(Class clazz, int n) {
        Set valueSet = new HashSet();
        for (int i = 0; i < n; i++) {
            Object newValue = sut.provideNextValue(clazz);
            assertThat(String.format("Value \"%s\" double created for class \"%s\"", newValue, clazz.getSimpleName()), valueSet.add(newValue), is(true));
        }
    }
}