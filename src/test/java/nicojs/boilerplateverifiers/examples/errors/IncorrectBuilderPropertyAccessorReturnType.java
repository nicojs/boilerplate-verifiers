package nicojs.boilerplateverifiers.examples.errors;

/**
 * Represents a IncorrectBuilderPropertyAccessorReturnType
 * Created by nicojs on 9/1/2015.
 */
public class IncorrectBuilderPropertyAccessorReturnType {
    private String theAttribute;

    IncorrectBuilderPropertyAccessorReturnType(String theAttribute) {
        this.theAttribute = theAttribute;
    }

    public static IncorrectBuilderPropertyAccessorReturnTypeBuilder builder() {
        return new IncorrectBuilderPropertyAccessorReturnTypeBuilder();
    }

    public static class IncorrectBuilderPropertyAccessorReturnTypeBuilder {
        private String theAttribute;

        IncorrectBuilderPropertyAccessorReturnTypeBuilder() {
        }

        public void /*Error! Method should return itself*/ theAttribute(String theAttribute) {
            this.theAttribute = theAttribute;
        }

        public IncorrectBuilderPropertyAccessorReturnType build() {
            return new IncorrectBuilderPropertyAccessorReturnType(theAttribute);
        }
    }
}
