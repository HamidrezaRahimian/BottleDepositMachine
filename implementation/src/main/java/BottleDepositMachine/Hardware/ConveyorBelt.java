package BottleDepositMachine.Hardware;

import Items.Item;

public class ConveyorBelt {
    private SortingMachine SortingMachine;
    public ConveyorBelt(SortingMachine SortingMachine) {
        this.SortingMachine = SortingMachine;
    }
    public void moveItem(Item item) {
        System.out.println("item has been transferred to Sorting machine : " + item.getCurrentLabel());
        SortingMachine.setMovedItem(item);

    }
}
