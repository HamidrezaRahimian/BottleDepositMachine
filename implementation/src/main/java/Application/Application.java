package Application;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Software.LEDState;
import BottleDepositMachine.Software.ReceiptProcessor;
import Items.Item;
import Items.ListOfItems;
import Users.Customer;
import BottleDepositMachine.Software.MersennerTwisterFast;

import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // Create a scanner to get input
        Scanner scanner = new Scanner(System.in);
        LEDState ledStatus;
        Customer customer = new Customer();
        ReceiptProcessor receiptProcessor = new ReceiptProcessor();
        Display display = new Display();
        BottleDepositMachine myBottleDepositMachine = new BottleDepositMachine(display, receiptProcessor);

        // List of employee IDs
        String[] employeeIDs = {"employeeID1-Alex", "employeeID2-Susana", "employeeID3-Tom"};

        // Initialize the custom random generator
        MersennerTwisterFast customRandom = new MersennerTwisterFast();

        // Randomly choose an employee ID
        String randomEmployeeID = employeeIDs[customRandom.nextInt(employeeIDs.length)];
        myBottleDepositMachine.scanCard(randomEmployeeID);
        System.out.println("Scanned employee card: " + randomEmployeeID);

        // Get the list of items from ListOfItems
        ListOfItems listOfItems = new ListOfItems();
        List<Item> items = listOfItems.getItems();

        while (true) { // The bottle deposit machine is always on
            try {
                ledStatus = LEDState.GREEN;
                System.out.print("Please enter the number of bottles to add : " + "\u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m \n");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the application. Thank you Mr. Müller!");
                    break;
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
                    Item randomItem = items.get(customRandom.nextInt(items.size())); // Select a random item from the list
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

                    // Print out the summary line after each bottle
                    System.out.printf("\u001B[34m%s | %s | %.2f € | %d | %d | %d | %d | %d | %.2f €\u001B[0m%n",
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

                String receiptData = String.format(
                        "Total Price: %.2f €\nAccepted Items: %d\nDisposable Items: %d\nReusable Items: %d\nNon-Accepted Items: %d",
                        totalPrice, acceptedItems, disposableItems, reusableItems, nonAcceptedItems
                );

                ledStatus = LEDState.GREEN;
                if (totalPrice == 0) {
                    System.out.println("Total price is zero. Please try again.");
                    continue; // Go back to the start of the loop
                }
                System.out.println("Customer added all the bottles " + "\u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m");
                myBottleDepositMachine.clickOn("Finish");

                // Automatically choose between donation (80%) or printing receipt (20%)
                ledStatus = LEDState.YELLOW;
                System.out.println("Do you like to donate the amount to DRF Luftrettung or get an electronic receipt on your mobile?" + "\u001B[33m(LED : " + ledStatus.name() + ")\u001B[0m");
                int choice = customRandom.nextInt(10); // Generate a random number between 0 and 9
                if (choice < 8) { // 80% chance for the user to donate
                    System.out.println("Automatically choosing donation " + "\u001B[33m(LED : " + ledStatus.name() + ")\u001B[0m");
                    myBottleDepositMachine.clickOn("Donation");
                    ledStatus = LEDState.GREEN;
                } else {
                    ledStatus = LEDState.YELLOW;
                    myBottleDepositMachine.clickOn("Print the receipt");
                    customer.putPhoneNextToReceiptReciver();
                    customer.getSmartphone().setReceipt(receiptData);
                }
                      /*
                // Display countdown message
                System.out.println("Your Receipt data will disappear from the display in 5 seconds.");
                for (int seconds = 5; seconds > 0; seconds--) {
                    Thread.sleep(1000); // Pause for 1 second
                    System.out.println(seconds + "...");
                }

                //disappear the display data as I understand from specification but is this what you mean? to test it just comment it out it should work
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
                */

                System.out.print("______________________________________________________________ \n");

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'exit' to quit.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
}


























// i thought user want to use manually so that is not neccessery anymore because there is 80 precent possibility and...
                /*
                System.out.println("Please choose an option: " + "\u001B[33m(LED : " + ledStatus.name() + ")\u001B[0m");
                System.out.println("1. Print the receipt");
                System.out.println("2. Donation");
                System.out.print("Enter your choice (1 or 2): ");
                */