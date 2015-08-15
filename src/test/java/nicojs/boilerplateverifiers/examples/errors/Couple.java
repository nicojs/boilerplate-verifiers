package nicojs.boilerplateverifiers.examples.errors;

import lombok.Getter;
import nicojs.boilerplateverifiers.examples.lombok.Person;

/**
 * Represents a Couple
 * Created by nicojs on 8/14/2015.
 */
@Getter
public class Couple {
    private Person man;
    private Person woman;

    @java.beans.ConstructorProperties({"man", "woman"})
    Couple(Person man, Person woman) {
        this.man = man;
        this.woman = woman;
    }

    public static CoupleBuilder builder() {
        return new CoupleBuilder();
    }

    public static class CoupleBuilder {
        private Person man;
        private Person woman;

        CoupleBuilder() {
        }

        public Couple.CoupleBuilder man(Person man) {
            this.woman = man;
            return this;
        }

        public Couple.CoupleBuilder woman(Person woman) {
            this.man = woman;
            return this;
        }

        public Couple build() {
            return new Couple(man, woman);
        }
    }
}
