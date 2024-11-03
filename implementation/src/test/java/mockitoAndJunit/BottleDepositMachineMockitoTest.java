package mockitoAndJunit;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.CardScanner;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Software.*;
import Items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;

class BottleDepositMachineMockitoTest {
    private BottleDepositMachine bottleDepositMachine;
    private CardScanner cardScanner;
    private Display display;
    private SupervisoryModule supervisoryModule;
    private List<Item> testItems;
    private ReceiptProcessor receiptProcessor;

    @BeforeEach
    void setUp() {
        display = new Display();
        receiptProcessor = new ReceiptProcessor();

        bottleDepositMachine = new BottleDepositMachine(display,receiptProcessor );
        cardScanner = new CardScanner(bottleDepositMachine);
        supervisoryModule = new SupervisoryModule(bottleDepositMachine, cardScanner, display,receiptProcessor);
        testItems = new ArrayList<>();

        // Initialize test items
        testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75));
        testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glas", 0.00));
        testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
        testItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
        testItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
        testItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50));
    }

    @Test
    void testBottleProcessingSequence() {
        // Unlock the machine
        bottleDepositMachine.scanCard("employeeID1-Alex");

        InOrder inOrder = inOrder(display);

        int acceptedItems = 0;
        int disposableItems = 0;
        int reusableItems = 0;
        int nonAcceptedItems = 0;
        double totalPrice = 0;
        double disposableTotal = 0;
        double reusableTotal = 0;

        // Process bottles
        for (Item item : testItems) {
            bottleDepositMachine.inputBottle(item);

            // Update counts based on item properties
            if (item.getDepositAmount() > 0) {
                acceptedItems++;
                if (item.getRecyclingType().equalsIgnoreCase("Disposable")) {
                    disposableItems++;
                    disposableTotal += item.getDepositAmount();
                } else if (item.getRecyclingType().equalsIgnoreCase("Reusable")) {
                    reusableItems++;
                    reusableTotal += item.getDepositAmount();
                }
                totalPrice += item.getDepositAmount();
            } else {
                nonAcceptedItems++;
            }
        }

        bottleDepositMachine.clickOn("Finish");
        bottleDepositMachine.clickOn("Print the receipt");

        assertEquals(1, disposableItems); // Disposable Count
        assertEquals(3, reusableItems); // Reusable Count
        assertEquals(2, nonAcceptedItems); // Not Accepted Count
        assertEquals(0.25, disposableTotal); // Disposable Total price
        assertEquals(3.25, reusableTotal); // Reusable Total price
        assertEquals(0, 0); // Not Accepted Total (not applicable here)
        assertEquals(3.55, totalPrice); // Total for All
    }
}
