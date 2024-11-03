package mockitoAndJunit;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.CardScanner;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Software.*;
import Items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Bottle Deposit Machine Simulator Tests")
class TestApplicationSimulator {
    private BottleDepositMachine bottleDepositMachine;
    private CardScanner cardScanner;
    private Display display;
    private SupervisoryModule supervisoryModule;
    private InMemoryDatabase inMemoryDatabase;
    private ReceiptProcessor receiptProcessor;
    private List<Item> testItems;
    private Simulator simulator;

    @BeforeEach
    void setUp() {
        receiptProcessor = new ReceiptProcessor();
        display = new Display();
        bottleDepositMachine = new BottleDepositMachine(display,receiptProcessor);
        cardScanner = new CardScanner(bottleDepositMachine);
        supervisoryModule = new SupervisoryModule(bottleDepositMachine, cardScanner, display,receiptProcessor);
        inMemoryDatabase = new InMemoryDatabase();
        testItems = new ArrayList<>();
        simulator = new Simulator(bottleDepositMachine);


    }


    // this is my first solutuin based on spicifation file but after 15 hours i decided to
    // add a class which call Simulator in Software which runs program for testing
    // so in the next 7 tests i used Simulator

    @Test
    void testInMemoryDatabaseRetrieval() {
        Item item = inMemoryDatabase.getItemByBarcode("r8yz7clkz4");
        assertNotNull(item);
        assertEquals("ABC Can | 0.33L", item.getFrontLabel());
    }

    @Test
    void testReceiptProcessing() {
        String[] itemData = {"ABC Can | 0.33L", "r8yz7clkz4", "Disposable", "Metal", "0.25"};
        receiptProcessor.addItemToReceipt(itemData);
        assertEquals("0.25", receiptProcessor.getTotal());
    }

    @Test
    void testStateChangeToReady() {
        bottleDepositMachine.changeStateToReady();
        assertEquals(State.READY, bottleDepositMachine.getCurrentState());
    }
    //Test 1
    @Test
    void testBottleProcessingSequence() {
        // Initialize test items
        testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75));
        testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glas", 0.00));
        testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
        testItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
        testItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
        testItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50));

        // Unlock the machine
        bottleDepositMachine.scanCard("employeeID1-Alex");

        // Counters for processed items
        int disposableItemsCount = 0;
        int reusableItemsCount = 0;
        int nonAcceptedItemsCount = 0;
        double disposableTotal = 0.0;
        double reusableTotal = 0.0;
        double totalForAll = 0.0;

        // Process bottles
        for (Item item : testItems) {
            bottleDepositMachine.inputBottle(item);
            // Update counters based on the item type
            if (item.getDepositAmount() > 0) {
                totalForAll += item.getDepositAmount();
                if (item.getRecyclingType().equalsIgnoreCase("Disposable")) {
                    disposableItemsCount++;
                    disposableTotal += item.getDepositAmount();
                } else if (item.getRecyclingType().equalsIgnoreCase("Reusable")) {
                    reusableItemsCount++;
                    reusableTotal += item.getDepositAmount();
                }
            } else {
                nonAcceptedItemsCount++;
            }
        }

        // Finalize the transaction
        bottleDepositMachine.clickOn("Finish");
        bottleDepositMachine.clickOn("Print the receipt");

        // Retrieve totals from the receipt processor
        ReceiptProcessor rp = bottleDepositMachine.getReceiptProcessor();

        // Assertions
        assertEquals(1, rp.getDisposableCount()); // Disposable Count
        assertEquals(3, rp.getReusableCount()); // Reusable Count
        assertEquals(2, rp.getNonAcceptedCount()); // Not Accepted Count
       // assertEquals(0, 0); // Not Accepted Total is always 0.00
        assertEquals(0.25, rp.getDisposableTotal()); // Disposable Total price
        assertEquals(3.25, rp.getReusableTotal()); // Reusable Total price
        assertEquals(3.50, rp.getTotalForAll()); // Total for All
    }


    // Test 2
    @Test
    void testBottleProcessingWithSimulator() {
        // Prepare test items
        List<Item> testItems = new ArrayList<>();
        testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75));
        testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glas", 0.00));
        testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
        testItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
        testItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
        testItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50));

        // Run the simulation
        ReceiptProcessor rp = simulator.runSimulation(testItems);

        // Assertions based on the receipt processor
        assertEquals(3, rp.getReusableCount()); // Expected reusable count
        assertEquals(1, rp.getDisposableCount()); // Expected disposable count
        assertEquals(2, rp.getNonAcceptedCount()); // Expected non-accepted count
        assertEquals(0.25, rp.getDisposableTotal(), 0.01); // Expected disposable total
        assertEquals(3.25, rp.getReusableTotal(), 0.01); // Expected reusable total
        assertEquals(3.50, rp.getTotalForAll(), 0.01); // Expected total for all
    }
}



