package nicojs.boilerplateverifiers.examples.errors;

import lombok.Getter;
import nicojs.boilerplateverifiers.examples.entities.SwitchValue;

/**
 * Represents a Switches
 * Created by nicojs on 8/15/2015.
 */
@Getter
public class Switches {
    private SwitchValue switch1;
    private SwitchValue switch2;
    private SwitchValue switch3;
    private SwitchValue switch4;

    @java.beans.ConstructorProperties({"switch1", "switch2", "switch3", "switch4"})
    Switches(SwitchValue switch1, SwitchValue switch2, SwitchValue switch3, SwitchValue switch4) {
        this.switch1 = switch1;
        this.switch2 = switch2;
        this.switch3 = switch4; // Error: switch 3 and 4 are wrongly assigned
        this.switch4 = switch3;
    }

    public static SwitchesBuilder builder() {
        return new SwitchesBuilder();
    }

    public static class SwitchesBuilder {
        private SwitchValue switch1;
        private SwitchValue switch2;
        private SwitchValue switch3;
        private SwitchValue switch4;

        SwitchesBuilder() {
        }

        public Switches.SwitchesBuilder switch1(SwitchValue switch1) {
            this.switch1 = switch1;
            return this;
        }

        public Switches.SwitchesBuilder switch2(SwitchValue switch2) {
            this.switch2 = switch2;
            return this;
        }

        public Switches.SwitchesBuilder switch3(SwitchValue switch3) {
            this.switch3 = switch3;
            return this;
        }

        public Switches.SwitchesBuilder switch4(SwitchValue switch4) {
            this.switch4 = switch4;
            return this;
        }

        public Switches build() {
            return new Switches(switch1, switch2, switch3, switch4);
        }

    }
}
