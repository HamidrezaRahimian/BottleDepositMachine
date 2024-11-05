package BottleDepositMachine.Containers;

import BottleDepositMachine.Software.LEDState;
import BottleDepositMachine.Software.MyStackArray;
import Items.Item;

public class SingleUsePlasticBin {
    private MyStackArray<Item> container;  // Use MyStackArray instead of Stack
    private LEDState ledStatus; // Changed from String to LEDState

    public SingleUsePlasticBin() {
        this.container = new MyStackArray<>(100);  // Set capacity to 100 (or any desired value)
    }

    public void addToContainer(Item movedItem) {
        this.container.push(movedItem);
        ledStatus = LEDState.GREEN; // Use LEDState enum

        System.out.println("Item is placed in the container successfully" +
                " \u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m");
    }
}
