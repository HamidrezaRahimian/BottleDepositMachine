package BottleDepositMachine.Containers;

import BottleDepositMachine.Software.LEDState;
import BottleDepositMachine.Software.MyStackArray;
import Items.Item;

public class WasteBin {
    private MyStackArray<Item> bin;  // Use MyStackArray instead of Stack
    private LEDState ledStatus;

    public WasteBin() {
        this.bin = new MyStackArray<>(100);
    }

    public void throwAwayBottle(Item movedItem) {
        this.bin.push(movedItem);
        ledStatus = LEDState.GREEN;

        System.out.println("Customer throw away the un accepted bottle" +
                " \u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m");
    }
}
