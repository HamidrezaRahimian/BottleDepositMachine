package BottleDepositMachine.Hardware;

import BottleDepositMachine.Software.InMemoryDatabase;
import BottleDepositMachine.Software.SupervisoryModule;
import Items.Item;

public class BarcodeScanner {
    private InMemoryDatabase database;
    private SupervisoryModule SupervisoryModule;
    private Item item;

    public BarcodeScanner(SupervisoryModule SupervisoryModule) {
        database = new InMemoryDatabase();
        this.SupervisoryModule = SupervisoryModule;
    }
    public String[] scan(Item item) {
        this.item = SupervisoryModule.checkRotation(item);
        System.out.println(item);
        String barcode = item.getBackLabel().getBarcode();

        Item foundItem = database.getItemByBarcode(barcode);


        if (foundItem != null) {  // if there is a barcode
          //  System.out.println("Barcode : " + barcode + "has been scanned");
            String[] scannedItem = new String[5];
            scannedItem[0] = item.getCurrentLabel();
            scannedItem[1] = item.getBackLabel().getBarcode();
            scannedItem[2] = item.getRecyclingType();
            scannedItem[3] = item.getMaterialType();
            scannedItem[4] =  Double.toString(item.getDepositAmount());

            return scannedItem;
        } else {
            System.out.println("there is no item with this barcode:  " + barcode);
            return null;
        }
    }

}

