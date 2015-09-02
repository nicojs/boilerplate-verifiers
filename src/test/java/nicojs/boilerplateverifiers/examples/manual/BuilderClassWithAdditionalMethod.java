package nicojs.boilerplateverifiers.examples.manual;

/**
 * Represents a BuilderClassWithAdditionalMethods
 * Created by nicojs on 9/2/2015.
 */
public class BuilderClassWithAdditionalMethod {
    private String first;

    BuilderClassWithAdditionalMethod(String first) {
        this.first = first;
    }

    public static BuilderClassWithAdditionalMethodsBuilder builder() {
        return new BuilderClassWithAdditionalMethodsBuilder();
    }

    public static class BuilderClassWithAdditionalMethodsBuilder {
        private String first;

        public BuilderClassWithAdditionalMethod.BuilderClassWithAdditionalMethodsBuilder first(String first) {
            this.first = first;
            return this;
        }

        public BuilderClassWithAdditionalMethod build() {
            return new BuilderClassWithAdditionalMethod(first);
        }

        public void doSomething(){}
    }
}
