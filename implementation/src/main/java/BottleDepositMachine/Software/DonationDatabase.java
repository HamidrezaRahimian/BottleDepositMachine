package BottleDepositMachine.Software;

import java.util.ArrayList;

public class DonationDatabase {
    private ArrayList<String> donations;

    // Initialize the donations list in the constructor
    public DonationDatabase() {
        donations = new ArrayList<>();
    }

    public void addDonations(String donation) {
        donations.add(donation);
        System.out.println("Deposit receipt " + donation + " is added to the donation database");
    }
}
