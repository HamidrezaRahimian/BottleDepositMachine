package BottleDepositMachine.Containers;

import Items.Item;

import java.util.Stack;

public class MultiPlasticBin {

    private Stack<Item> container;
    public MultiPlasticBin() {
        this.container = new Stack<>();
    }
    public void addToContainer(Item movedItem) {
        this.container.push(movedItem);
        System.out.println("Item "+movedItem+" is placed in the container successfully");
    }
}
