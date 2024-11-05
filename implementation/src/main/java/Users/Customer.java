package Users;

import Items.Smartphone;

//this class was not necessary for doing this project however i made it at the beginning to test some logic things with it
public class Customer {
    private double totalDepositedAmount;
    private Smartphone smartphone;

    // Constructor
    public Customer() {
        this.totalDepositedAmount = 0.0;

    }
    public Smartphone getSmartphone() {
        return smartphone; // Return the  smartphone instance
    }

    public void putPhoneNextToReceiptReciver() {
        System.out.println("customer moved the Smartphone next to the Receipt reciever");

    }


}
