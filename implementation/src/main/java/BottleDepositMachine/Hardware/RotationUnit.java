package BottleDepositMachine.Hardware;

import Items.Item;

public class RotationUnit {
    private Roller roller1;
    private Roller roller2;

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
        item.setRotation(180);
        System.out.println("Item " + item.getCurrentLabel() + " has been rotated 270 degree");
    }


}
