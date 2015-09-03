package nicojs.boilerplateverifiers.examples.errors;

/**
 * Represents a NotBuildingAllAttributes
 * Created by nicojs on 9/3/2015.
 */
public class NotBuildingAllAttributes {
    private String value;
    private String notBuildableString;

    NotBuildingAllAttributes(String value) {
        this.value = value;
        this.notBuildableString = "";
    }

    public static NotBuildingAllAttributesBuilder builder() {
        return new NotBuildingAllAttributesBuilder();
    }

    public static class NotBuildingAllAttributesBuilder {
        private String value;

        NotBuildingAllAttributesBuilder() {
        }

        public NotBuildingAllAttributes.NotBuildingAllAttributesBuilder value(String value) {
            this.value = value;
            return this;
        }

        public NotBuildingAllAttributes build() {
            return new NotBuildingAllAttributes(value);
        }
    }
}
