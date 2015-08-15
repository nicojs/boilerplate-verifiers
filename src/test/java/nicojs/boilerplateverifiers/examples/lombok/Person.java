package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;
import lombok.Value;

/**
 * Represents a Person
 * Created by nicojs on 8/12/2015.
 */
@Value
@Builder
public class Person {
    private String name;
    private int age;
}
