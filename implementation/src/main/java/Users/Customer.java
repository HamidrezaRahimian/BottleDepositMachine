package Users;
//this class was not necessary for doing this project however i made it at the beginning to test some logic things with it
public class Customer {
    private String customerId;
    private String name;
    private double totalDepositedAmount;

    // Constructor
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.totalDepositedAmount = 0.0;
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public double getTotalDepositedAmount() {
        return totalDepositedAmount;
    }

    // Method to update total amount deposited by customer
    public void deposit(double amount) {
        this.totalDepositedAmount += amount;
        System.out.println(name + " has deposited a total of: $" + this.totalDepositedAmount);
    }


}
