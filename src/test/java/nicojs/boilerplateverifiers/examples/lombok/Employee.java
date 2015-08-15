package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;

/**
 * Represents a Employee
 * Created by nicojs on 8/15/2015.
 */
public class Employee extends Person {
    private final String employeeNumber;

    @Builder(builderMethodName = "buildEmployee")
    public Employee(String name, int age, String employeeNumber) {
        super(name, age);
        this.employeeNumber = employeeNumber;
    }
}
