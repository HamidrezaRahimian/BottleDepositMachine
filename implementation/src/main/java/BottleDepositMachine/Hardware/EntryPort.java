package BottleDepositMachine.Hardware;

import BottleDepositMachine.Software.SupervisoryModule;
import Items.Item;

public class EntryPort {
    private SupervisoryModule controlUnit;
    public void acceptItem(Item item) {
        System.out.println("item is accepted : " + item.getCurrentLabel());
        controlUnit.processBottle(item);
    }
}
