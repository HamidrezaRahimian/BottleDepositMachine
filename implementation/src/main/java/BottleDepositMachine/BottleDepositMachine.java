package BottleDepositMachine;

import BottleDepositMachine.Hardware.CardScanner;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Hardware.EntryPort;
import BottleDepositMachine.Software.LEDState;
import BottleDepositMachine.Software.ReceiptProcessor;
import BottleDepositMachine.Software.State;
import BottleDepositMachine.Software.SupervisoryModule;
import Items.Item;
import Users.Customer;
import lombok.Getter;

public class BottleDepositMachine {

    @Getter
    private CardScanner cardScanner;
    private State state;
    private Display display;
    @Getter
    private State currentState;
    private LEDState ledStatus; // Changed from String to LEDState
    private EntryPort inputSlot;
    private SupervisoryModule controlUnit;
    private ReceiptProcessor receiptProcessor;
    private Customer currentCustomer;



    public BottleDepositMachine(Display display, ReceiptProcessor receiptProcessor) {
        this.currentState = State.LOCKED;
        this.receiptProcessor = receiptProcessor;
        this.ledStatus = LEDState.RED; // Initialize with LEDState
        this.display = display;
        this.cardScanner = new CardScanner(this);
        this.controlUnit = new SupervisoryModule(this, cardScanner, display , receiptProcessor);
    }

    public void changeStateToReady() {
        currentState = State.READY;
        ledStatus = LEDState.GREEN; // Use LEDState enum
        System.out.println("The Bottle Deposit Machine is ready to scan (" + ledStatus.getDescription() + "). " +
                "\u001B[32m(LED : " + ledStatus.name() + ")\u001B[0m");
    }

    public void changeStateToLocked() {
        currentState = State.LOCKED;
        ledStatus = LEDState.RED; // Use LEDState enum
        System.out.println("The Bottle Deposit Machine is unable for service (" +
                ledStatus.getDescription() + "). " +
                "\u001B[31m(LED : " + ledStatus.name() + ")\u001B[0m");
    }

    public void changeStateToRequireAction() {
        currentState = State.LOCKED; // You might want to change this logic if "RequireAction" should not be locked
        ledStatus = LEDState.YELLOW; // Use LEDState enum
        System.out.println("Action required: you need to pick up the bottle (" +
                ledStatus.getDescription() + "). " +
                "\u001B[33m(LED : " + ledStatus.name() + ")\u001B[0m");
    }

    public void inputBottle(Item item) {
        controlUnit.processBottle(item);
        System.out.println("________________________________________________________________________________________________");
        System.out.println("Customer inserted a bottle");
        System.out.println("Processing new bottle | please wait " +
                "\u001B[31m(LED : " + ledStatus.name() + ")\u001B[0m");


    }

    public void scanCard(String employeeId) {
        controlUnit.scanIDCard(employeeId);
    }

    public void clickOn(String button) {
        if (button.equals("Finish")) {
            System.out.println("Customer clicked on Finish");
            display.pushFinishButton();
        } else if (button.equals("Print the receipt")) {
            display.pushRecieptButton();
        } else if (button.equals("Donation")) {
            display.pushDonationButton();
        } else {
            System.out.println("Invalid Input");
        }
    }


    public ReceiptProcessor getReceiptProcessor() {
        return this.receiptProcessor;
    }
}
