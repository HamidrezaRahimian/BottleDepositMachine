package BottleDepositMachine.Hardware;

import BottleDepositMachine.Containers.CompressedBottleBin;
import Items.Item;

public class Compactor  {
    private CompressedBottleBin crushedBottelContainer;

    public Compactor () {
        this.crushedBottelContainer =  new CompressedBottleBin();
    }

    public void crushItem(Item item) {
        item.setCrushed(true);
        crushedBottelContainer.addToContainer(item);

    }
}
