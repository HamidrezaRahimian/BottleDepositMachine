package BottleDepositMachine.Hardware;

import BottleDepositMachine.Software.LEDState;
import Items.Item;

public class RotationUnit {
    private Roller roller1;
    private Roller roller2;
    private LEDState ledStatus; // Changed from String to LEDState

    public RotationUnit() {
        roller1 = new Roller();
        roller2 = new Roller();
    }
    public Item checkItemRotaion(Item item) {
        if(item.getRotation() == 180) {
            return item;
        }else{
            rotateTo270(item);
            return item;
        }
    }
    public void rotateTo270(Item item) {
        // roatate that bottle for 270 degree (obviously look at the method name)
        ledStatus = LEDState.RED; // Use LEDState enum

        item.setRotation(180);
        System.out.println("Item " + item.getCurrentLabel() + " has been rotated 270 degrees " +
                "\u001B[31m(LED : " + ledStatus.name() + ")\u001B[0m");

    }


}
