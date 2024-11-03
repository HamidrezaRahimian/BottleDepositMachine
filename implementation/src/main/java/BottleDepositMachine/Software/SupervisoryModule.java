package BottleDepositMachine.Software;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.*;
import Items.Item;

public class SupervisoryModule {
    private BottleDepositMachine bottleDepositMachine;
    private CardScanner cardScanner;
    private BarcodeScanner barcodeScanner;
    private RotationUnit rotationUnit;
    private Display display;
    private ReceiptProcessor receiptProcessor;
    private String[] scannedItem;
    private ConveyorBelt conveyorBelt;
    private SortingMachine SortingMachine;
    private Compactor crusher;
    private DonationDatabase donationDatabase;
    private Printer printer;

    public SupervisoryModule(BottleDepositMachine bottleDepositMachine, CardScanner cardScanner, Display display) {
        this.bottleDepositMachine = bottleDepositMachine;
        this.cardScanner = cardScanner;
        this.barcodeScanner = new BarcodeScanner(this);
        this.rotationUnit = new RotationUnit();
        this.display = display;
        display.setControllUnit(this);
        this.receiptProcessor = new ReceiptProcessor();
        this.scannedItem = new String[5];
        this.SortingMachine = new SortingMachine(this);
        this.conveyorBelt = new ConveyorBelt(SortingMachine);
        this.crusher = new Compactor();
        this.donationDatabase = new DonationDatabase();
        this.printer = new Printer();
    }

    public void scanIDCard(String employeeId) {
        cardScanner.handleEmployeeCardScan(employeeId);
    }

    public void processBottle(Item item) {
        if (bottleDepositMachine.getCurrentState() == State.READY) {
            // displayMessage("Processing item | please wait.");
            scannedItem = barcodeScanner.scan(item);
            if (scannedItem == null) {
                displayMessage("Cannot deposit the item, please remove item.");
                receiptProcessor.addNonAcceptedItem();
                bottleDepositMachine.changeStateToRequireAction();
                displayMessage("User removed the bottle from the Entry port");
                bottleDepositMachine.changeStateToReady();
            } else {
                receiptProcessor.addItemToReceipt(scannedItem);
                conveyorBelt.moveItem(item);
                System.out.println("Type: " + item.getRecyclingType() + ", Material: " + item.getMaterialType());
                SortingMachine.sortMovedItem(scannedItem);
                display.setFinishButton(true);
            }
        } else {
            System.out.println("Bottle deposit machine is locked");
        }
    }

    public void finishProcess() {
        display.setDonationButton(true);
        display.setRecieptButton(true);
        receiptProcessor.finishReceipt(bottleDepositMachine.hashCode());
    }

    public void donate() {
        donationDatabase.addDonations(receiptProcessor.getReceipt().toString());
        receiptProcessor.clearStorage();
        System.out.println("Donation was successful");
    }

    public void printReceipt() {
        printer.print(receiptProcessor.getReceipt().toString());
        receiptProcessor.clearStorage();
        System.out.println("Session was successful");
    }

    public void displayMessage(String message) {
        display.showMessage(message);
    }

    public Item checkRotation(Item item) {
        Item rotatedItem = rotationUnit.checkItemRotaion(item);

        return rotatedItem;
    }
    public void crushItem(Item item) {
        crusher.crushItem(item);
        System.out.println("Crushed item: " + item.getFrontLabel());
    }
}
