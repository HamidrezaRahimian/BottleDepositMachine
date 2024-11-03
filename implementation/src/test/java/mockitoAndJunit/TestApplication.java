package mockitoAndJunit;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.CardScanner;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Software.*;
import Items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestApplication {
    private BottleDepositMachine bottleDepositMachine;
    private CardScanner cardScanner;
    private Display display;
    private SupervisoryModule supervisoryModule;
    private InMemoryDatabase inMemoryDatabase;
    private ReceiptProcessor receiptProcessor;
    private DonationDatabase donationDatabase;
    @BeforeEach
    void setUp() {
        display = new Display();
        cardScanner = new CardScanner(bottleDepositMachine);
        bottleDepositMachine = new BottleDepositMachine(display);
        supervisoryModule = new SupervisoryModule(bottleDepositMachine, cardScanner, display);
        inMemoryDatabase = new InMemoryDatabase();
        receiptProcessor = new ReceiptProcessor();
        donationDatabase = new DonationDatabase();
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
        assertEquals(bottleDepositMachine.getCurrentState(), State.READY);
    }



}
