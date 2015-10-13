package nicojs.boilerplateverifiers.examples.tostring;

import lombok.ToString;

/**
 * Represents a GraphWithToStringAndNodeWithoutToString
 * Created by nicojs
 */
@ToString
public class GraphWithToStringAndNodeWithoutToString {
    private PojoWithoutToString pojoWithoutToString;
    private String value;
}
