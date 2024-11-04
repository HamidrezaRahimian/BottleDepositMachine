package BottleDepositMachine.Containers;

import BottleDepositMachine.Software.LEDState;
import Items.Item;

import java.util.Stack;

public class SingleUsePlasticBin {
    private Stack<Item> Container;
    private LEDState ledStatus; // Changed from String to LEDState

    public SingleUsePlasticBin() {
        this.Container = new Stack<>();
    }
    public void addToContainer(Item movedItem) {
        this.Container.push(movedItem);
        ledStatus = LEDState.GREEN; // Use LEDState enum

        System.out.println("Item is placed in the container successfully" +
                " \u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m");

    }
}
