package BottleDepositMachine;

import BottleDepositMachine.Hardware.CardScanner;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Hardware.EntryPort;
import BottleDepositMachine.Software.State;
import BottleDepositMachine.Software.SupervisoryModule;
import Items.Item;
import lombok.Getter;

public class BottleDepositMachine {

    @Getter
    private CardScanner cardScanner;
    private State state;
    private Display display;
    @Getter
    private State currentState;
    private String ledStatus;
    private EntryPort inputSlot;
    private SupervisoryModule controlUnit;

    public BottleDepositMachine(Display display) {
        this.currentState = State.LOCKED;
        this.ledStatus = "RED";
        this.display = display;
        this.cardScanner = new CardScanner(this);
        this.controlUnit = new SupervisoryModule(this, cardScanner, display);
    }

    public void changeStateToReady() {
        currentState = State.READY;
        ledStatus = "GREEN";
        System.out.println("The Bottle Deposit Machine is ready to scan (" + ledStatus + ").");
    }

    public void changeStateToLocked() {
        currentState = State.LOCKED;
        ledStatus = "RED";
        System.out.println("The Bottle Deposit Machine is unable for service ( " + ledStatus + ").");
    }

    public void changeStateToRequireAction() {
        currentState = State.LOCKED;
        ledStatus = "YELLOW";
        System.out.println("Action required: you need to pick up the bottle ( " + ledStatus + ").");
    }

    public void inputBottle(Item item) {
        controlUnit.processBottle(item);
        System.out.println("________________________________________________________________________________________________");
        System.out.println("Processing new bottle | please wait ");
    }

    public void scanCard(String employeeId) {
        controlUnit.scanIDCard(employeeId);
    }

    public void clickOn(String button) {
        if (button.equals("Finish")) {
            display.pushFinishButton();
        } else if (button.equals("Print the receipt")) {
            display.pushRecieptButton();
        } else if (button.equals("Donation")) {
            display.pushDonationButton();
        } else {
            System.out.println("Invalid Input");
        }
    }
}
