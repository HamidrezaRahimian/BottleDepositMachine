package BottleDepositMachine.Containers;

import Items.Item;

import java.util.Stack;

public class CompressedBottleBin {
    private Stack<Item> container;
    public CompressedBottleBin() {
        this.container = new Stack<>();
    }
    public void addToContainer(Item movedItem) {
        this.container.push(movedItem);
        System.out.println("Item "+movedItem+" is placed in the container successfully");

    }
}
