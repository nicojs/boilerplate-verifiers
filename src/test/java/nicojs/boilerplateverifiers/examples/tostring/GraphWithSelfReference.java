package nicojs.boilerplateverifiers.examples.tostring;

/**
 * Represents a GraphWithSelfReference
 * Created by nicojs
 */
public class GraphWithSelfReference {
    private String string;
    private GraphWithSelfReference self;

    public String toString() {
        return "nicojs.boilerplateverifiers.examples.tostring.GraphWithSelfReference(string=" + this.string + ")";
    }
}
