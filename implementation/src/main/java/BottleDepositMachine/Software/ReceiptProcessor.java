package BottleDepositMachine.Software;

import lombok.Getter;

import java.util.ArrayList;

public class ReceiptProcessor {
    @Getter
    private ArrayList<String> reciept;
    private double total;
    public ReceiptProcessor() {
         reciept = new ArrayList<>();
        total = 0;

    }

    public void addItemToReceipt(String[] item) {
        String recieptEntry = item[0]+","+item[2]+","+item[3]+","+item[4];
        reciept.add(recieptEntry);
        total += Double.parseDouble(item[4]);
    }

    public void finishReceipt() {
        reciept.add("Total: "+total);
    }

    public void clearStorage() {
        reciept.clear();
    }
}
