package nicojs.boilerplateverifiers.examples.tostring;

import lombok.Getter;
import lombok.ToString;

/**
 * Represents a PojoWithToString
 * Created by nicojs
 */
@ToString
@Getter
public class PojoWithToString {
    private int age;
    private String value;
    private double theDouble;
}
