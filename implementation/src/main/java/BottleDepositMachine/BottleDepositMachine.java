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
    private String ledState;
    private EntryPort inputSlot;
    private SupervisoryModule SupervisoryModule;


    public BottleDepositMachine(Display display) {
        this.currentState = State.LOCKED;
        this.ledState = "Rot";
        this.display = display;
        this.cardScanner = new CardScanner(this);
        this.SupervisoryModule = new SupervisoryModule (this, cardScanner,display);
    }

    public void changeStateToReady() {
        currentState = State.READY;
        ledState = "GREEN";
        System.out.println("The Bottle Deposit Machine is  ready to scan  " + ledState + ").");
    }

    public void changeStateToLocked() {
        currentState = State.LOCKED;
        ledState = "RED";
        System.out.println("The Bottle Deposit Machine is  unable for service  " + ledState + ").");
    }
    public void changeStateToRequireAction() {
        currentState = State.LOCKED;
        ledState = "YELLOW";
        System.out.println("action required: you need to pick up the bottle  " + ledState + ").");
    }
    public void inputBottle(Item item){
        SupervisoryModule.processBottle(item);

    }
    public void scanCard(String employeeId){
        SupervisoryModule.scanIDCard(employeeId);
    }
    public void clickOn(String button){

        if(button == "Finish"){
            display.pushFinishButton();
        } else if (button == "Print the receipt") {
            display.pushRecieptButton();
        } else if (button == "Donation") {
            display.pushDonationButton();
        }else{
            System.out.println("invalid Input"); // it basically cant happen cause there is no other option to choose , however i use it for testing and this stuffs haha
        }
    }

}
