package mockito;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.CardScanner;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Software.InMemoryDatabase;
import BottleDepositMachine.Software.ReceiptProcessor;
import BottleDepositMachine.Software.Simulator;
import Items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class MockitoTestApplicationSimulator {

    private BottleDepositMachine bottleDepositMachine;
    private CardScanner cardScanner;
    private Display display;
    private ReceiptProcessor receiptProcessor;
    private List<Item> testItems;
    private Simulator simulator;
    private InMemoryDatabase inMemoryDatabase;
    private InMemoryDatabase mockDatabase;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        display = mock(Display.class);
        receiptProcessor = mock(ReceiptProcessor.class);
        bottleDepositMachine = new BottleDepositMachine(display, receiptProcessor);
        cardScanner = mock(CardScanner.class);
        simulator = new Simulator(bottleDepositMachine);
        testItems = new ArrayList<>();
        inMemoryDatabase = new InMemoryDatabase();
        mockDatabase = mock(InMemoryDatabase.class);

    }

    @Test
    void testNeverCalledMethods() {
        // Prepare test items
        testItems.add(new Item("DE Bottle | 0.5L", "3jdwml7w52", "Reusable", "Glass", 0.30));

        // Run the simulation without calling non-accepted count methods
        simulator.runSimulation(testItems);

        // Verify methods that should never have been called
        verify(receiptProcessor, never()).getNonAcceptedCount();
        verify(receiptProcessor, never()).getDisposableTotal();
    }

    @Test
    public void testGetItemByBarcode_InvalidBarcode() {
        // Given an invalid barcode
        String invalidBarcode = "invalid_barcode";

        // When retrieving the item
        Item item = inMemoryDatabase.getItemByBarcode(invalidBarcode);

        // Then the retrieved item should be null
        assertNull(item, "Item should not be found for an invalid barcode");
    }


    @Test
    public void testInOrderCalls() {
        // Create a mock instance of the Item class
        Item mockedItem1 = mock(Item.class);
        Item mockedItem2 = mock(Item.class);

        // When getItemByBarcode is called with specific barcodes, return the mocked items
        when(mockDatabase.getItemByBarcode("barcode1")).thenReturn(mockedItem1);
        when(mockDatabase.getItemByBarcode("barcode2")).thenReturn(mockedItem2);

        // When calling getItemByBarcode in a specific order
        mockDatabase.getItemByBarcode("barcode1");
        mockDatabase.getItemByBarcode("barcode2");

        // Verify the calls in order
        InOrder inOrder = inOrder(mockDatabase);
        inOrder.verify(mockDatabase).getItemByBarcode("barcode1");
        inOrder.verify(mockDatabase).getItemByBarcode("barcode2");
    }

    @Test
    public void testGetItemByBarcodeUsingAny() {
        // Prepare a test item
        Item testItem = new Item("Test Item", "barcode123", "Reusable", "Plastic", 0.5);

        // Define the behavior of the mock to return the test item for any barcode
        when(mockDatabase.getItemByBarcode(anyString())).thenReturn(testItem);

        // Call the method on the mock with any barcode
        Item retrievedItem1 = mockDatabase.getItemByBarcode("barcode123");
        Item retrievedItem2 = mockDatabase.getItemByBarcode("barcode456");

        // Verify the interaction and the result
        verify(mockDatabase, times(2)).getItemByBarcode(anyString());
        assertEquals(testItem, retrievedItem1);
        assertEquals(testItem, retrievedItem2);
    }

    @Test
    public void testGetItemByBarcodeCalledMultipleTimesWithAny() {
        // Prepare a test item
        Item testItem = new Item("Another Test Item", "barcode456", "Disposable", "Metal", 0.75);

        // Set up the mock to return the test item when called with any barcode
        when(mockDatabase.getItemByBarcode(anyString())).thenReturn(testItem);

        // Call the method on the mock three times with different barcodes
        mockDatabase.getItemByBarcode("randomBarcode1"); // First call
        mockDatabase.getItemByBarcode("randomBarcode2"); // Second call
        mockDatabase.getItemByBarcode("randomBarcode3"); // Third call

        // Verify that getItemByBarcode was called exactly three times with any barcode
        verify(mockDatabase, times(3)).getItemByBarcode(anyString());
    }

    @Test
    void test4() {
        // Prepare test items
        testItems.add(new Item("DE Bottle | 0.5L", "3jdwml7w52", "Reusable", "Glass", 0.30));
        testItems.add(new Item("ABC Can | 0.33L", "r8yz7clkz4", "Disposable", "Metal", 0.25));
        testItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
        testItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
        testItems.add(new Item("Z Bottle | 0.5L", "5jrsqeg201", "Not Accepted", "Plastic", 0.00));
        testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glass", 0.75));
        testItems.add(new Item("Y Bottle | 1L", "5hqoa0ean4", "Not Accepted", "Glass", 0.00));
        testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));

        // Set up mock behavior for the receipt processor
        when(receiptProcessor.getReusableCount()).thenReturn(3);
        when(receiptProcessor.getDisposableCount()).thenReturn(2);
        when(receiptProcessor.getNonAcceptedCount()).thenReturn(3);
        when(receiptProcessor.getDisposableTotal()).thenReturn(0.50);
        when(receiptProcessor.getReusableTotal()).thenReturn(3.05);
        when(receiptProcessor.getTotalForAll()).thenReturn(3.55);

        // Run the simulation
        ReceiptProcessor rp = simulator.runSimulation(testItems);

        // Assertions based on the mocked receipt processor
        assertEquals(3, rp.getReusableCount()); // Expected reusable count
        assertEquals(2, rp.getDisposableCount()); // Expected disposable count
        assertEquals(3, rp.getNonAcceptedCount()); // Expected non-accepted count
        assertEquals(0.50, rp.getDisposableTotal(), 0.01); // Expected disposable total
        assertEquals(3.05, rp.getReusableTotal(), 0.01); // Expected reusable total
        assertEquals(3.55, rp.getTotalForAll(), 0.01); // Expected total for all

        // Verify interactions with mocks
        verify(receiptProcessor, times(1)).getReusableCount();
        verify(receiptProcessor, times(1)).getDisposableCount();
        verify(receiptProcessor, times(1)).getNonAcceptedCount();
        verify(receiptProcessor, times(1)).getDisposableTotal();
        verify(receiptProcessor, times(1)).getReusableTotal();
        verify(receiptProcessor, times(1)).getTotalForAll();
    }


    @Test
    void test5() {
        // Prepare test items
        testItems.add(new Item("ABC Can | 0.33L", "r8yz7clkz4", "Disposable", "Metal", 0.25));
        testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glass", 0.00));
        testItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
        testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glass", 0.75));
        testItems.add(new Item("Y Bottle | 0.5L", "447ds7u9j4", "Not Accepted", "Glass", 0.00));
        testItems.add(new Item("DE Bottle | 0.5L", "3jdwml7w52", "Reusable", "Glass", 0.30));

        // Set up mock behavior for the receipt processor
        when(receiptProcessor.getReusableCount()).thenReturn(3);
        when(receiptProcessor.getDisposableCount()).thenReturn(1);
        when(receiptProcessor.getNonAcceptedCount()).thenReturn(2);
        when(receiptProcessor.getDisposableTotal()).thenReturn(0.25);
        when(receiptProcessor.getReusableTotal()).thenReturn(3.05);
        when(receiptProcessor.getTotalForAll()).thenReturn(3.30);

        // Run the simulation
        ReceiptProcessor rp = simulator.runSimulation(testItems);

        // Assertions based on the mocked receipt processor
        assertEquals(3, rp.getReusableCount()); // Expected reusable count
        assertEquals(1, rp.getDisposableCount()); // Expected disposable count
        assertEquals(2, rp.getNonAcceptedCount()); // Expected non-accepted count
        assertEquals(0.25, rp.getDisposableTotal(), 0.01); // Expected disposable total
        assertEquals(3.05, rp.getReusableTotal(), 0.01); // Expected reusable total
        assertEquals(3.30, rp.getTotalForAll(), 0.01); // Expected total for all

        // Verify interactions with mocks
        verify(receiptProcessor, times(1)).getReusableCount();
        verify(receiptProcessor, times(1)).getDisposableCount();
        verify(receiptProcessor, times(1)).getNonAcceptedCount();
        verify(receiptProcessor, times(1)).getDisposableTotal();
        verify(receiptProcessor, times(1)).getReusableTotal();
        verify(receiptProcessor, times(1)).getTotalForAll();
    }

    @Test
    void test6() {
        // Prepare test items
        testItems.add(new Item("DE Bottle | 0.33L", "bzfi339nsy", "Reusable", "Glass", 0.25));
        testItems.add(new Item("FG Bottle | 0.5L", "js92hp13rp", "Reusable", "Plastic", 1.00));
        testItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
        testItems.add(new Item("Y Bottle | 1L", "5hqoa0ean4", "Not Accepted", "Glass", 0.00));
        testItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glass", 0.50));
        testItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
        testItems.add(new Item("Z Bottle | 1L", "pv4og90ymi", "Not Accepted", "Plastic", 0.00));

        // Set up mock behavior for the receipt processor
        when(receiptProcessor.getReusableCount()).thenReturn(4);
        when(receiptProcessor.getDisposableCount()).thenReturn(0);
        when(receiptProcessor.getNonAcceptedCount()).thenReturn(3);
        when(receiptProcessor.getDisposableTotal()).thenReturn(0.00);
        when(receiptProcessor.getReusableTotal()).thenReturn(3.75);
        when(receiptProcessor.getTotalForAll()).thenReturn(3.75);

        // Run the simulation
        ReceiptProcessor rp = simulator.runSimulation(testItems);

        // Assertions based on the mocked receipt processor
        assertEquals(4, rp.getReusableCount()); // Expected reusable count
        assertEquals(0, rp.getDisposableCount()); // Expected disposable count
        assertEquals(3, rp.getNonAcceptedCount()); // Expected non-accepted count
        assertEquals(0.00, rp.getDisposableTotal(), 0.01); // Expected disposable total
        assertEquals(3.75, rp.getReusableTotal(), 0.01); // Expected reusable total
        assertEquals(3.75, rp.getTotalForAll(), 0.01); // Expected total for all

        // Verify interactions with mocks
        verify(receiptProcessor, times(1)).getReusableCount();
        verify(receiptProcessor, times(1)).getDisposableCount();
        verify(receiptProcessor, times(1)).getNonAcceptedCount();
        verify(receiptProcessor, times(1)).getDisposableTotal();
        verify(receiptProcessor, times(1)).getReusableTotal();
        verify(receiptProcessor, times(1)).getTotalForAll();
    }
}
