package BottleDepositMachine.Hardware;

import BottleDepositMachine.Containers.CompressedBottleBin;
import Items.Item;

public class Compactor extends CardScanner {
    private CompressedBottleBin crushedBottleContainer;

    public Compactor () {
        this.crushedBottleContainer =  new CompressedBottleBin();
    }

    public void crushItem(Item item) {
        item.setCrushed(true);
        crushedBottleContainer.addToContainer(item);

    }
}
