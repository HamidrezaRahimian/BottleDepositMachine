package mockito;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.CardScanner;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Software.ReceiptProcessor;
import BottleDepositMachine.Software.Simulator;
import Items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MockitoTestApplicationSimulator {

    private BottleDepositMachine bottleDepositMachine;
    private CardScanner cardScanner;
    private Display display;
    private ReceiptProcessor receiptProcessor;
    private List<Item> testItems;
    private Simulator simulator;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        display = mock(Display.class);
        receiptProcessor = mock(ReceiptProcessor.class);
        bottleDepositMachine = new BottleDepositMachine(display, receiptProcessor);
        cardScanner = mock(CardScanner.class);
        simulator = new Simulator(bottleDepositMachine);
        testItems = new ArrayList<>();
    }

    @Test
    void test3() {
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
}
