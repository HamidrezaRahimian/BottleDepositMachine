package Users;

import Items.Smartphone;

public class Customer {
    private double totalDepositedAmount;
    private Smartphone smartphone;

    // Constructor
    public Customer() {
        this.totalDepositedAmount = 0.0;
        this.smartphone = new Smartphone();
    }

    public Smartphone getSmartphone() {
        return smartphone; // Return the smartphone instance
    }

    public void putPhoneNextToReceiptReciver() {
        System.out.println("Customer moved the Smartphone next to the Receipt receiver");
    }
}
