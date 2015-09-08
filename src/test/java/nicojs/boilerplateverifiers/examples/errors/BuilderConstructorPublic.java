package nicojs.boilerplateverifiers.examples.errors;

/**
 * Represents a BuilderConstructorPublic
 * Created by nicojs
 */
public class BuilderConstructorPublic {
    private String val1;

    BuilderConstructorPublic(String val1) {
        this.val1 = val1;
    }

    public static BuilderConstructorPublicBuilder builder() {
        return new BuilderConstructorPublicBuilder();
    }

    public static class BuilderConstructorPublicBuilder {
        private String val1;

        public BuilderConstructorPublicBuilder() {
        }

        public BuilderConstructorPublic.BuilderConstructorPublicBuilder val1(String val1) {
            this.val1 = val1;
            return this;
        }

        public BuilderConstructorPublic build() {
            return new BuilderConstructorPublic(val1);
        }
    }
}
