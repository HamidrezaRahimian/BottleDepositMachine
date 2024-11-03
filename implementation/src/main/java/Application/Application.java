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

        // Display and Bottle Deposit Machine setup
        Display display = new Display();
        BottleDepositMachine myBottleDepositMachine = new BottleDepositMachine(display);
        myBottleDepositMachine.scanCard("employeeID1-Alex");

        // Get the list of items from ListOfItems
        ListOfItems listOfItems = new ListOfItems();
        List<Item> items = listOfItems.getItems();

        while (true) { // Loop to allow repeated operations
            try {
                // Prompt user for the number of bottles to add
                System.out.print("Please enter the number of bottles to add (or type 'exit' to quit): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the application. Thank you!");
                    break; // Exit the loop if user types 'exit'
                }

                int numberOfBottles = Integer.parseInt(input);
                System.out.println("Adding " + numberOfBottles + " bottles:");

                // Allow the user to add the specified number of bottles
                for (int i = 0; i < numberOfBottles; i++) {
                    Item randomItem = items.get(i % items.size()); // Loop through items list if user requests more than available items
                    myBottleDepositMachine.inputBottle(randomItem);
                //    System.out.println("Added bottle:  " + randomItem);
                }

                myBottleDepositMachine.clickOn("Finish");

                // User chooses between printing receipt or making a donation
                System.out.println("Please choose an option: ");
                System.out.println("1. Print the receipt");
                System.out.println("2. Donation");
                System.out.print("Enter your choice (1 or 2): \n");

                //int choice = Integer.parseInt(scanner.nextLine());
                int choice = 1;

                // Process the user's choice
                if (choice == 1) {
                    myBottleDepositMachine.clickOn("Print the receipt");
                } else if (choice == 2) {
                    myBottleDepositMachine.clickOn("Donation");
                } else {
                    System.out.println("Invalid choice. No action taken.");
                }


            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'exit' to quit.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        // Close the scanner to free up resources
        scanner.close();
    }
}
