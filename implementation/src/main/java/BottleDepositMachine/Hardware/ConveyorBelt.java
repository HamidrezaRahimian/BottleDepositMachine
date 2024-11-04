package BottleDepositMachine.Hardware;

import BottleDepositMachine.Software.LEDState;
import Items.Item;

public class ConveyorBelt {
    private SortingMachine SortingMachine;
    private LEDState ledStatus; // Changed from String to LEDState

    public ConveyorBelt(SortingMachine SortingMachine) {
        this.SortingMachine = SortingMachine;
    }
    public void moveItem(Item item) {
        ledStatus = LEDState.RED; // Use LEDState enum

        System.out.println("Item has been transferred to Sorting Machine: " + item.getCurrentLabel() +
                " \u001B[31m(LED : " + ledStatus.name() + ")\u001B[0m");
        SortingMachine.setMovedItem(item);

    }
}
