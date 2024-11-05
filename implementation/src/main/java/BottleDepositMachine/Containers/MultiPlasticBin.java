package BottleDepositMachine.Containers;

import BottleDepositMachine.Software.LEDState;
import BottleDepositMachine.Software.MyStackArray;
import Items.Item;

public class MultiPlasticBin {

    private MyStackArray<Item> container;  // Use MyStackArray instead of Stack
    private LEDState ledStatus;

    public MultiPlasticBin() {
        this.container = new MyStackArray<>(250);  // Set capacity to 250
    }

    public void addToContainer(Item movedItem) {
        this.container.push(movedItem);
        ledStatus = LEDState.GREEN;

        System.out.println("Item is placed in the container successfully" +
                " \u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m");
    }
}

