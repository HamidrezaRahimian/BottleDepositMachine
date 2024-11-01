package BottleDepositMachine.Hardware;

import BottleDepositMachine.Containers.GlassCollectionBin;
import BottleDepositMachine.Containers.MultiPlasticBin;
import BottleDepositMachine.Containers.SingleUsePlasticBin;
import BottleDepositMachine.Software.SupervisoryModule;
import Items.Item;
import lombok.Setter;

import java.util.Objects;

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
        if (Objects.equals(scannedItem[2], "disposable") && Objects.equals(scannedItem[3], "Metal")) {
          controlUnit.crushItem(movedItem);
          return;
        }
        if (Objects.equals(scannedItem[2], "disposable") && Objects.equals(scannedItem[3], "Plastic")) {
            SingleUsePlasticBin.addToContainer(movedItem);
            return;
        }
        if (Objects.equals(scannedItem[2], "reusable") && Objects.equals(scannedItem[3], "Plastic")) {
            MultiPlasticBin.addToContainer(movedItem);
            return;
        }
        if (Objects.equals(scannedItem[2], "disposable") && Objects.equals(scannedItem[3], "Glas")) {
            GlassCollectionBin.addToContainer(movedItem);
            return;
        }else {
            System.out.println("Invalid BottelType");
            return;
        }
    }

}
