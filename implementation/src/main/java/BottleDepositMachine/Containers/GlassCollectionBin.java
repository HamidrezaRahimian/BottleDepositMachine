package BottleDepositMachine.Containers;

import Items.Item;

import java.util.Stack;

public class GlassCollectionBin {
    private Stack<Item> Container;
    public GlassCollectionBin() {
        this.Container = new Stack<>();
    }
    public void addToContainer(Item movedItem) {
        this.Container.push(movedItem);
        System.out.println("Item "+movedItem+" is placed in the container successfully");

    }
}
