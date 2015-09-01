package nicojs.boilerplateverifiers.examples.errors;

import nicojs.boilerplateverifiers.examples.lombok.Person;

/**
 * Represents a EmployeeCannotBuildParentAttributes
 * Created by nicojs on 9/1/2015.
 */
public class EmployeeCannotBuildParentAttributes extends Person {
    private String employeeNumber;

    EmployeeCannotBuildParentAttributes(String employeeNumber, String name) {
        super(name, 0);
        this.employeeNumber = employeeNumber;
    }

    public static EmployeeCannotBuildParentAttributesBuilder builderEmployee() {
        return new EmployeeCannotBuildParentAttributesBuilder();
    }

    public static class EmployeeCannotBuildParentAttributesBuilder {
        private String employeeNumber;
        private String name;

        EmployeeCannotBuildParentAttributesBuilder() {
        }

        public EmployeeCannotBuildParentAttributes.EmployeeCannotBuildParentAttributesBuilder employeeNumber(String employeeNumber) {
            this.employeeNumber = employeeNumber;
            return this;
        }

        public EmployeeCannotBuildParentAttributesBuilder name(String name){
            this.name = name;
            return this;
        }

        public EmployeeCannotBuildParentAttributes build() {
            return new EmployeeCannotBuildParentAttributes(employeeNumber, name);
        }
    }
}
