package BottleDepositMachine.Hardware;

import BottleDepositMachine.Containers.GlassCollectionBin;
import BottleDepositMachine.Containers.MultiPlasticBin;
import BottleDepositMachine.Containers.SingleUsePlasticBin;
import BottleDepositMachine.Software.SupervisoryModule;
import Items.Item;
import lombok.Setter;

public class SortingMachine {
    @Setter
    private Item movedItem;
    private SupervisoryModule controlUnit;
    private SingleUsePlasticBin SingleUsePlasticBin;
    private MultiPlasticBin MultiPlasticBin;
    private GlassCollectionBin GlassCollectionBin;

    public SortingMachine(SupervisoryModule controlUnit) {
        this.controlUnit =  controlUnit;
        this.SingleUsePlasticBin =  new SingleUsePlasticBin();
        this.MultiPlasticBin = new MultiPlasticBin();
        this.GlassCollectionBin = new GlassCollectionBin();
    }


    public void sortMovedItem(String[] scannedItem) {
        String type = scannedItem[2];
        String material = scannedItem[3];

        if ("Disposable".equals(type) && "Metal".equals(material)) {
            System.out.println("bottle moved to Crusher to be crushed");
            controlUnit.crushItem(movedItem);

            return;
        }
        if ("Disposable".equals(type) && "Plastic".equals(material)) {
            SingleUsePlasticBin.addToContainer(movedItem);
            System.out.println("bottle moved to SingleUsePlasticBin");
            return;
        }
        if ("Reusable".equals(type) && "Plastic".equals(material)) {
            MultiPlasticBin.addToContainer(movedItem);
            System.out.println("bottle moved to MultiPlasticBin");
            return;
        }
        if ("Reusable".equals(type) && "Glas".equals(material)) {
            GlassCollectionBin.addToContainer(movedItem);
            System.out.println("bottle moved to GlassCollectionBin");
            return;
        }
        System.out.println("**type** "+ type + "****" + scannedItem[2] );
        System.out.println("**material** "+ material + "****" + scannedItem[3] );
        System.out.println("Invalid Bottle Type in Sorting Machine-----------------------------------------");
    }


}
