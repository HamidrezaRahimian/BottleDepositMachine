package BottleDepositMachine.Containers;

import BottleDepositMachine.Software.LEDState;
import Items.Item;

import BottleDepositMachine.Software.MyStackArray;

public class GlassCollectionBin {
    private MyStackArray<Item> container;
    private LEDState ledStatus;

    public GlassCollectionBin() {
        this.container = new MyStackArray<>(250);
    }

    public void addToContainer(Item movedItem) {
        this.container.push(movedItem);
        ledStatus = LEDState.GREEN;

        System.out.println("Item is placed in the container successfully" +
                " \u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m");
    }
}
