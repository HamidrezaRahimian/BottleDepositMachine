package BottleDepositMachine.Hardware;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Software.State;
import Items.Item;

public class CardScanner {
    private BottleDepositMachine bottleDepositMachine;


    public CardScanner(BottleDepositMachine bottleDepositMachine) {
        this.bottleDepositMachine = bottleDepositMachine;
    }

      //here you can define new worker username
    public void handleEmployeeCardScan(String employeeId) {
        if (employeeId.equals("employeeID1-Alex") || employeeId.equals("employeeID2-Susana") || employeeId.equals("employeeID3-Tom")) {
            if (bottleDepositMachine.getCurrentState() == State.LOCKED) {
                bottleDepositMachine.changeStateToReady();
            } else {
                bottleDepositMachine.changeStateToLocked();
            }
        } else {
            System.out.println("Unknown employee ID. Access denied.");
        }
    }

    public boolean rotateAndScanItem(Item item) {
        System.out.println("please rotate the bottle " + item.getCurrentLabel() + " to scan the barcode");

        //simulating different cases : on what side of the bottle the barcodes can be
        if (item.getRotation() == 180) {
            System.out.println("Barcode scanned successfully: " + item.getBackLabel().getBarcode());
            return true;
        }


        if (item.getRotation() == 0) {
            System.out.println("was not able to scan the barcode (0 degree side).");
            //so we need to rotate the bottle on 90 degree side and try agaiiin
            item.setRotation(90);
            System.out.println("item rotated  on 90 degrees side .");
        }


        if (item.getRotation() == 90) {
            System.out.println("was not able to scan the barcode (90 degree side).");
            //so we need to rotate the bottle on 270 degree side
            item.setRotation(270);
            System.out.println("item rotated  on 270 degrees side .");
        }

        //on the right side of bottle
        if (item.getRotation() == 270) {
            System.out.println("Barcode scanned successfully:  " + item.getBackLabel().getBarcode());
            return true;
        }

        System.out.println("unable to scan teh barcode (Rotation: " + item.getRotation() + " degree).");
        return false;
    }
}
