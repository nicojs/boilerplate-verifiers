package nicojs.boilerplateverifiers.examples.errors;

import lombok.Getter;

/**
 * Represents a BooleansWronglyAssigned
 * Created by nicojs on 8/15/2015.
 */
@Getter
public class BooleansWronglyAssigned {
    private boolean bool1;
    private boolean bool2;
    private boolean toBe;
    private boolean orNotToBe;

    @java.beans.ConstructorProperties({"bool1", "bool2", "toBe", "orNotToBe"})
    BooleansWronglyAssigned(boolean bool1, boolean bool2, boolean toBe, boolean orNotToBe) {
        this.bool1 = bool1;
        this.bool2 = bool2;
        this.toBe = orNotToBe; // Error!! Wrong assignement
        this.orNotToBe = toBe;
    }

    public static BooleansWronglyAssignedBuilder builder() {
        return new BooleansWronglyAssignedBuilder();
    }

    public static class BooleansWronglyAssignedBuilder {
        private boolean bool1;
        private boolean bool2;
        private boolean toBe;
        private boolean orNotToBe;

        BooleansWronglyAssignedBuilder() {
        }

        public BooleansWronglyAssigned.BooleansWronglyAssignedBuilder bool1(boolean bool1) {
            this.bool1 = bool1;
            return this;
        }

        public BooleansWronglyAssigned.BooleansWronglyAssignedBuilder bool2(boolean bool2) {
            this.bool2 = bool2;
            return this;
        }

        public BooleansWronglyAssigned.BooleansWronglyAssignedBuilder toBe(boolean toBe) {
            this.toBe = toBe;
            return this;
        }

        public BooleansWronglyAssigned.BooleansWronglyAssignedBuilder orNotToBe(boolean orNotToBe) {
            this.orNotToBe = orNotToBe;
            return this;
        }

        public BooleansWronglyAssigned build() {
            return new BooleansWronglyAssigned(bool1, bool2, toBe, orNotToBe);
        }

    }
}
