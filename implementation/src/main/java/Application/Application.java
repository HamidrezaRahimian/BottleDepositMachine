package Application;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.Display;
import Items.Item;
import Items.ListOfItems;

import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        // Create a scanner to get user input
        Scanner scanner = new Scanner(System.in);

        Display display = new Display();
        BottleDepositMachine MyBottleDepositMachine = new BottleDepositMachine(display);
        MyBottleDepositMachine.scanCard("employeeID1-Alex");

        // Get the list of items from ListOfItems
        ListOfItems listOfItems = new ListOfItems();
        List<Item> items = listOfItems.getItems();

        //  user to enter the number of bottles however the type is going to be choose randomly  in next 7 lines of code
        System.out.print("Please enter the number of bottles to add: ");
        int numberOfBottles = scanner.nextInt();

        System.out.println("Adding " + numberOfBottles + " bottles:");

        // Allow the user to select bottles to add
        for (int i = 0; i < numberOfBottles; i++) {
            Item randomItem = items.get(i % items.size()); // Loop through items list if user requests more than available items
            MyBottleDepositMachine.inputBottle(randomItem);
            System.out.println("Added bottle: " + randomItem);
        }

        MyBottleDepositMachine.clickOn("Finish");

        //  user choose between printing receipt or making a donation
        System.out.println("Please choose an option: _______________________________________________");
        System.out.println("1. Print the receipt");
        System.out.println("2. Donation");
        System.out.print("Enter your choice (1 or 2): ");
        int choice = scanner.nextInt();

        // Process the user's choice
        if (choice == 1) {
            MyBottleDepositMachine.clickOn("Print the receipt");
        } else if (choice == 2) {
            MyBottleDepositMachine.clickOn("Donation");
        } else {
            System.out.println("Invalid choice. No action taken.");
        }



        // Close the scanner so that next customer can use the machine again from the beginig
        scanner.close();
    }
}
