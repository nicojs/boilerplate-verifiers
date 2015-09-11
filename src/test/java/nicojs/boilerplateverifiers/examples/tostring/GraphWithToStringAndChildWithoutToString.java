package nicojs.boilerplateverifiers.examples.tostring;

import lombok.ToString;

/**
 * Represents a GraphWithToStringAndChildWithoutToString
 * Created by nicojs
 */
@ToString
public class GraphWithToStringAndChildWithoutToString {
    private PojoWithoutToString pojoWithoutToString;
    private String value;
}
