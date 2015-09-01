package nicojs.boilerplateverifiers.examples.entities;

/**
 * Represents a AllAttributesBeingUsedExcept
 * Created by nicojs on 9/1/2015.
 */
public class AllAttributesBeingUsedExcept {

    private String var1;
    private int var2;
    private boolean var3;

    AllAttributesBeingUsedExcept(String var1) {
        this.var1 = var1;
    }

    public static AllAttributesBeingUsedExceptBuilder builder() {
        return new AllAttributesBeingUsedExceptBuilder();
    }

    public static class AllAttributesBeingUsedExceptBuilder {
        private String var1;

        AllAttributesBeingUsedExceptBuilder() {
        }

        public AllAttributesBeingUsedExcept.AllAttributesBeingUsedExceptBuilder var1(String var1) {
            this.var1 = var1;
            return this;
        }

        public AllAttributesBeingUsedExcept build() {
            return new AllAttributesBeingUsedExcept(var1);
        }
    }
}
