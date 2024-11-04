package Users;

import BottleDepositMachine.BottleDepositMachine;
//this class was not necessary for doing this project however i made it at the beginning to test some logic things with it
    public class Employee {
    private String employeeId;
    private String name;
    private String position;

    // Constructor
    public Employee(String employeeId, String name, String position) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
    }

    // Getters
    public String getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    // Method for employee to unlock or lock the machine
    public void scanForAccess(BottleDepositMachine machine) {
        System.out.println(name + " is scanning the card for machine access.");
        machine.scanCard(employeeId);
    }
}
