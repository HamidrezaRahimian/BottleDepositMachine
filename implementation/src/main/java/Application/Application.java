package Application;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.Display;
import Items.Item;
import Items.ListOfItems;

import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // Create a scanner to get  input
        Scanner scanner = new Scanner(System.in);


        Display display = new Display();
        BottleDepositMachine myBottleDepositMachine = new BottleDepositMachine(display);
        myBottleDepositMachine.scanCard("employeeID1-Alex");

        // Get the list of items from ListOfItems ( for testing , the database of Items is InMemoryDatabase)
        ListOfItems listOfItems = new ListOfItems();
        List<Item> items = listOfItems.getItems();

        while (true) { // we use while because the bottle deposit Machine is always on
            try {
                // Prompt user for the number of bottles to add
                System.out.print("Please enter the number of bottles to add : ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the application. Thank you!");
                    break; // Exit the loop if user types 'exit'
                }

                // Reset counters at the beginning of each new session
                int insertedItems = 0;
                int acceptedItems = 0;
                int disposableItems = 0;
                int reusableItems = 0;
                int nonAcceptedItems = 0;
                double totalPrice = 0;

                int numberOfBottles = Integer.parseInt(input);
                System.out.println("Adding " + numberOfBottles + " bottles:");

                // Allow the user to add the specified number of bottles
                for (int i = 0; i < numberOfBottles; i++) {
                    Item randomItem = items.get(i % items.size()); // Loop through items list if user requests more than available items
                    myBottleDepositMachine.inputBottle(randomItem);

                    // Update values
                    insertedItems++;
                    if (randomItem.getDepositAmount() > 0) {
                        acceptedItems++;
                        if (randomItem.getRecyclingType().equalsIgnoreCase("Disposable")) {
                            disposableItems++;
                        } else if (randomItem.getRecyclingType().equalsIgnoreCase("Reusable")) {
                            reusableItems++;
                        }
                        totalPrice += randomItem.getDepositAmount();
                    } else {
                        nonAcceptedItems++;
                    }

                    // Print out the summary line after each bottle like hte spesificaitn example
                    System.out.printf("%s | %s | %.2f € | %d | %d | %d | %d | %d | %.2f €%n",
                            randomItem.getFrontLabel(),
                            randomItem.getRecyclingType(),
                            randomItem.getDepositAmount(),
                            insertedItems,
                            acceptedItems,
                            disposableItems,
                            reusableItems,
                            nonAcceptedItems,
                            totalPrice);
                }

                myBottleDepositMachine.clickOn("Finish");

                // User chooses between printing receipt or making a donation
                System.out.println("Please choose an option: ");
                System.out.println("1. Print the receipt");
                System.out.println("2. Donation");
                System.out.print("Enter your choice (1 or 2): \n");
                System.out.print("");

                int choice = Integer.parseInt(scanner.nextLine());

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
