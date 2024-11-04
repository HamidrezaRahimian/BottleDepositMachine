package BottleDepositMachine.Containers;

import BottleDepositMachine.Software.LEDState;
import Items.Item;

import java.util.Stack;

public class MultiPlasticBin {

    private Stack<Item> container;
    private LEDState ledStatus; // Changed from String to LEDState

    public MultiPlasticBin() {
        this.container = new Stack<>();
    }
    public void addToContainer(Item movedItem) {
        this.container.push(movedItem);
        ledStatus = LEDState.GREEN; // Use LEDState enum

        System.out.println("Item is placed in the container successfully" +
                " \u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m");
    }
}
