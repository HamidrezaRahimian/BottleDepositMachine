package BottleDepositMachine.Software;

import BottleDepositMachine.BottleDepositMachine;
import Items.Item;

import java.util.List;

public class Simulator {
    private BottleDepositMachine bottleDepositMachine;

    public Simulator(BottleDepositMachine bottleDepositMachine) {
        this.bottleDepositMachine = bottleDepositMachine;
    }

    public ReceiptProcessor runSimulation(List<Item> items) {
        bottleDepositMachine.scanCard("employeeID1-Alex");

        // Counters for processed items
        int disposableItemsCount = 0;
        int reusableItemsCount = 0;
        int nonAcceptedItemsCount = 0;
        double disposableTotal = 0.0;
        double reusableTotal = 0.0;
        double totalForAll = 0.0;

        // Process bottles
        for (Item item : items) {
            bottleDepositMachine.inputBottle(item);

            if (item.getDepositAmount() > 0) {
                totalForAll += item.getDepositAmount();
                if (item.getRecyclingType().equalsIgnoreCase("Disposable")) {
                    disposableItemsCount++;
                    disposableTotal += item.getDepositAmount();
                } else if (item.getRecyclingType().equalsIgnoreCase("Reusable")) {
                    reusableItemsCount++;
                    reusableTotal += item.getDepositAmount();
                }
            } else {
                nonAcceptedItemsCount++;
            }
        }

        // Finalize the transaction
        bottleDepositMachine.clickOn("Finish");
        bottleDepositMachine.clickOn("Print the receipt");

        // Retrieve totals from the receipt processor
        return bottleDepositMachine.getReceiptProcessor();
    }
}