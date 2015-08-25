package nicojs.boilerplateverifiers.examples.errors;

public class NotAllAtributesCanBeBuild {
    private String attribute1;
    private String attribute2;
    private String attribute3;

    @java.beans.ConstructorProperties({"attribute1", "attribute2"})
    NotAllAtributesCanBeBuild(String attribute1, String attribute2) {
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = "";
    }

    public static NotAllAtributesCanBeBuildBuilder builder() {
        return new NotAllAtributesCanBeBuildBuilder();
    }

    public static class NotAllAtributesCanBeBuildBuilder {
        private String attribute1;
        private String attribute2;

        NotAllAtributesCanBeBuildBuilder() {
        }

        public NotAllAtributesCanBeBuild.NotAllAtributesCanBeBuildBuilder attribute1(String attribute1) {
            this.attribute1 = attribute1;
            return this;
        }

        public NotAllAtributesCanBeBuild.NotAllAtributesCanBeBuildBuilder attribute2(String attribute2) {
            this.attribute2 = attribute2;
            return this;
        }

        public NotAllAtributesCanBeBuild build() {
            return new NotAllAtributesCanBeBuild(attribute1, attribute2);
        }

        public String toString() {
            return "nicojs.boilerplateverifiers.examples.errors.NotAllAtributesCanBeBuild.NotAllAtributesCanBeBuildBuilder(attribute1=" + this.attribute1 + ", attribute2=" + this.attribute2 + ")";
        }
    }
}
