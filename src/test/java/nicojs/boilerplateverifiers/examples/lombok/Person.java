package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a Person
 * Created by nicojs on 8/12/2015.
 */
@Getter
public class Person {
    private final String name;
    private final int age;

    @Builder
    protected Person(String name, int age){
        this.name = name;
        this.age = age;
    }
}
