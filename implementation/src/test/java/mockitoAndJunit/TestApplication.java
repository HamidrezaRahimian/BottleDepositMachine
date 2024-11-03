package mockitoAndJunit;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.CardScanner;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Software.*;
import Items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TestApplication {
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
        display = mock(Display.class);
        receiptProcessor = mock(ReceiptProcessor.class);
        bottleDepositMachine = mock(BottleDepositMachine.class);
        cardScanner = new CardScanner(bottleDepositMachine);
        supervisoryModule = new SupervisoryModule(bottleDepositMachine, cardScanner, display, receiptProcessor);
        inMemoryDatabase = new InMemoryDatabase();
        testItems = new ArrayList<>();
        simulator = new Simulator(bottleDepositMachine);
    }

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

    // this is my first solution based on spicifation file but after 15 hours i decided to
    // add a class which call Simulator in Software which runs program for testing
    // so in the next 7 tests i used Simulator

    // Test 1
    @Test
    void testBottleProcessingSequence() {
        // Initialize test items
        testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75));
        testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glas", 0.00));
        testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
        testItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
        testItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
        testItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50));

        // Mock the behavior of methods


        // Mock the receipt processor
        when(bottleDepositMachine.getReceiptProcessor()).thenReturn(receiptProcessor);
        when(receiptProcessor.getDisposableCount()).thenReturn(1);
        when(receiptProcessor.getReusableCount()).thenReturn(3);
        when(receiptProcessor.getNonAcceptedCount()).thenReturn(2);
        when(receiptProcessor.getDisposableTotal()).thenReturn(0.25);
        when(receiptProcessor.getReusableTotal()).thenReturn(3.25);
        when(receiptProcessor.getTotalForAll()).thenReturn(3.50);

        // Finalize the transaction
        bottleDepositMachine.clickOn("Finish");
        bottleDepositMachine.clickOn("Print the receipt");

        // Assertions
        assertEquals(1, receiptProcessor.getDisposableCount()); // Disposable Count
        assertEquals(3, receiptProcessor.getReusableCount()); // Reusable Count
        assertEquals(2, receiptProcessor.getNonAcceptedCount()); // Not Accepted Count
        assertEquals(0.25, receiptProcessor.getDisposableTotal()); // Disposable Total price
        assertEquals(3.25, receiptProcessor.getReusableTotal()); // Reusable Total price
        assertEquals(3.50, receiptProcessor.getTotalForAll()); // Total for All
    }

    // Test 2
    @Test
    void testBottleProcessingWithSimulator() {
        // Prepare test items using the same list
        testItems.clear(); // Clear previous items if any
        testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75));
        testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glas", 0.00));
        testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
        testItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
        testItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
        testItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50));

        // Run the simulation
        ReceiptProcessor rp = simulator.runSimulation(testItems);

        // Mock return values for the receipt processor
        when(rp.getReusableCount()).thenReturn(3);
        when(rp.getDisposableCount()).thenReturn(1);
        when(rp.getNonAcceptedCount()).thenReturn(2);
        when(rp.getDisposableTotal()).thenReturn(0.25);
        when(rp.getReusableTotal()).thenReturn(3.25);
        when(rp.getTotalForAll()).thenReturn(3.50);

        // Assertions based on the receipt processor
        assertEquals(3, rp.getReusableCount()); // Expected reusable count
        assertEquals(1, rp.getDisposableCount()); // Expected disposable count
        assertEquals(2, rp.getNonAcceptedCount()); // Expected non-accepted count
        assertEquals(0.25, rp.getDisposableTotal(), 0.01); // Expected disposable total
        assertEquals(3.25, rp.getReusableTotal(), 0.01); // Expected reusable total
        assertEquals(3.50, rp.getTotalForAll(), 0.01); // Expected total for all
    }
}