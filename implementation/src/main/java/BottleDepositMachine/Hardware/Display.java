package BottleDepositMachine.Hardware;


import BottleDepositMachine.Software.SupervisoryModule;
import lombok.Setter;
public class Display {


    @Setter
    private boolean finishButton = false;
    @Setter
    private SupervisoryModule controlUnit;
    @Setter
    private boolean donationButton = false;
    @Setter
    private boolean recieptButton = false;




    public void showMessage(String message) {
        System.out.println( message);
    }

    public void pushFinishButton() {
        if (finishButton) {
            finishButton = false;
            controlUnit.finishProcess();
            System.out.println("Finished processing|Displaying Buttons");
        }else{
            System.out.println("No Bottles have been inserted");
        }
    }

    public void pushDonationButton() {
        if (donationButton) {
            donationButton = false;
            recieptButton = false;
            System.out.println( "clicked on Donation button");
            controlUnit.donate();
        }else{
            System.out.println("donation is not possible , please make sure to insert the bottles first");
        }
    }
    public void pushRecieptButton() {
        if (recieptButton) {
            recieptButton = false;
            donationButton = false;
            System.out.println("receipt has been printed");
            controlUnit.printReceipt();
        }else{
            System.out.println("printing receipt is not possible , please make sure to insert the bottles first");
        }
    }


    public void setControllUnit(SupervisoryModule SupervisoryModule) {
        this.controlUnit = SupervisoryModule;
    }
}
