package BottleDepositMachine.Containers;

import BottleDepositMachine.Software.LEDState;
import BottleDepositMachine.Software.MyStackArray;
import Items.Item;

public class CompressedBottleBin {
    private MyStackArray<Item> container;
    private LEDState ledStatus;

    public CompressedBottleBin() {
        this.container = new MyStackArray<>(250);
    }

    public void addToContainer(Item movedItem) {
        this.container.push(movedItem);
        ledStatus = LEDState.GREEN; // Use LEDState enum
        System.out.println("Item is placed in the container successfully" +
                " \u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m");
    }
}
