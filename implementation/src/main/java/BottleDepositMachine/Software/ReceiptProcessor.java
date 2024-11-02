package BottleDepositMachine.Software;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReceiptProcessor {
    private List<String> receipt;
    private double total;
    private int sequenceNumber;
    private int acceptedItemsCount;
    private int nonAcceptedItemsCount;
    private int disposableItemsCount;
    private int reusableItemsCount;
    private double disposableTotal;
    private double reusableTotal;

    public ReceiptProcessor() {
        receipt = new ArrayList<>();
        total = 0;
        sequenceNumber =1 ;
        acceptedItemsCount = 0;
        nonAcceptedItemsCount = 0;
        disposableItemsCount = 0;
        reusableItemsCount = 0;
        disposableTotal = 0;
        reusableTotal = 0;
    }

    public void addItemToReceipt(String[] item) {
        // Parse item data to decide on the category (disposable/reusable) and add to totals
        String type = item[2];
        double price = Double.parseDouble(item[4]);

        if (type.equalsIgnoreCase("Disposable")) {
            disposableItemsCount++;
            disposableTotal += price;
        } else if (type.equalsIgnoreCase("Reusable")) {
            reusableItemsCount++;
            reusableTotal += price;
        }

        acceptedItemsCount++;
        total += price;

        // Format and add the item entry to the receipt with each entry on a new line
        String receiptEntry = String.format("%s, %s, %s, %.2f €", item[0], item[2], item[3], price);
        receipt.add(receiptEntry);
    }

    public void addNonAcceptedItem() {
        nonAcceptedItemsCount++;
    }

    public void finishReceipt(int machineHashCode) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String timestamp = LocalDateTime.now().format(dtf);


        receipt.add(" ");
        receipt.add("Receipt");
        receipt.add("Seq: " + sequenceNumber++);
        receipt.add(timestamp);
        receipt.add("Serialnumber: " + machineHashCode);
        receipt.add("#inserted items: " + (acceptedItemsCount + nonAcceptedItemsCount));
        receipt.add("#accepted items: " + acceptedItemsCount);
        receipt.add("#disposable: " + disposableItemsCount + " (" + String.format("%.2f", disposableTotal) + " €)");
        receipt.add("#reusable: " + reusableItemsCount + " (" + String.format("%.2f", reusableTotal) + " €)");
        receipt.add("#non-accepted items: " + nonAcceptedItemsCount);
        receipt.add("> total: " + String.format("%.2f", total) + " €");
        receipt.add(" ");

    }

    public void clearStorage() {
        receipt.clear();
        total = 0;
        acceptedItemsCount = 0;
        nonAcceptedItemsCount = 0;
        disposableItemsCount = 0;
        reusableItemsCount = 0;
        disposableTotal = 0;
        reusableTotal = 0;
    }

    public String getReceipt() {
        StringBuilder receiptBuilder = new StringBuilder();
        for (String line : receipt) {
            receiptBuilder.append(line).append("\n"); // Ensure each line is on a new line (for hte receipt)
        }
        return receiptBuilder.toString().trim(); // Trim any trailing newline
    }
}
