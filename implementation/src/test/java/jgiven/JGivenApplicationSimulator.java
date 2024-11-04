package jgiven;

import BottleDepositMachine.BottleDepositMachine;
import BottleDepositMachine.Hardware.CardScanner;
import BottleDepositMachine.Hardware.Display;
import BottleDepositMachine.Software.*;
import Items.Item;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Bottle Deposit Machine Simulator Tests")
class JGivenApplicationSimulator {
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
        bottleDepositMachine = new BottleDepositMachine(display, receiptProcessor);
        cardScanner = new CardScanner(bottleDepositMachine);
        supervisoryModule = new SupervisoryModule(bottleDepositMachine, cardScanner, display, receiptProcessor);
        inMemoryDatabase = new InMemoryDatabase();
        testItems = new ArrayList<>();
        simulator = new Simulator(bottleDepositMachine);
    }

    @Nested
    @DisplayName("In-Memory Database Tests")
    class InMemoryDatabaseTests {

        @Order(1)
        @Test
        void testInMemoryDatabaseRetrieval() {
            Item item = inMemoryDatabase.getItemByBarcode("r8yz7clkz4");
            assertNotNull(item);
            assertEquals("ABC Can | 0.33L", item.getFrontLabel());
        }
    }

    @Nested
    @DisplayName("Receipt Processing Tests")
    class ReceiptProcessingTests {

        @Order(2)
        @Test
        void testReceiptProcessing() {
            String[] itemData = {"ABC Can | 0.33L", "r8yz7clkz4", "Disposable", "Metal", "0.25"};
            receiptProcessor.addItemToReceipt(itemData);
            assertEquals("0.25", receiptProcessor.getTotal());
        }
    }

    @Nested
    @DisplayName("Bottle Processing Tests")
    class BottleProcessingTests {

        @Order(3)
        @Test
        void testStateChangeToReady() {
            bottleDepositMachine.changeStateToReady();
            assertEquals(State.READY, bottleDepositMachine.getCurrentState());
        }

        @ParameterizedTest
        @MethodSource("provideTestItems")
        void testBottleProcessingWithParameterizedInput(Item item) {
            // Unlock the machine
            bottleDepositMachine.scanCard("employeeID1-Alex");
            // Process the bottle
            bottleDepositMachine.inputBottle(item);
        }

        private static Stream<Item> provideTestItems() {
            return Stream.of(
                    new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glas", 0.75),
                    new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glas", 0.00),
                    new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25),
                    new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00),
                    new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00),
                    new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glas", 0.50)
            );
        }

        @TestFactory
        Stream<DynamicTest> testBottleProcessingSequence() {
            return Stream.of(
                    DynamicTest.dynamicTest("Process bottles and assert totals", () -> {
                        // Unlock the machine
                        bottleDepositMachine.scanCard("employeeID1-Alex");

                        // Counters for processed items
                        int disposableItemsCount = 0;
                        int reusableItemsCount = 0;
                        int nonAcceptedItemsCount = 0;
                        double disposableTotal = 0.0;
                        double reusableTotal = 0.0;
                        double totalForAll = 0.0;

                        // Process predefined test items
                        for (Item item : provideTestItems().toArray(Item[]::new)) {
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
                        assertEquals(0.25, rp.getDisposableTotal()); // Disposable Total price
                        assertEquals(3.25, rp.getReusableTotal()); // Reusable Total price
                        assertEquals(3.50, rp.getTotalForAll()); // Total for All
                    })
            );
        }

        @Test
        void testBottleProcessingWithSimulator() {
            // Prepare test items
            List<Item> testItems = new ArrayList<>();
            testItems.add(new Item("DE Bottle | 0.33L", "bzfi339nsy", "Reusable", "Glass", 0.25));
            testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glass", 0.00));
            testItems.add(new Item("DE Bottle | 0.5L", "3jdwml7w52", "Reusable", "Glass", 0.30));
            testItems.add(new Item("Y Bottle | 1L", "5hqoa0ean4", "Not Accepted", "Glass", 0.00));
            testItems.add(new Item("FG Bottle | 0.5L", "js92hp13rp", "Reusable", "Plastic", 1.00));
            testItems.add(new Item("ABC Can | 0.33L", "r8yz7clkz4", "Disposable", "Metal", 0.25));
            testItems.add(new Item("Y Bottle | 0.5L", "447ds7u9j4", "Not Accepted", "Glass", 0.00));

            // Run the simulation
            ReceiptProcessor rp = simulator.runSimulation(testItems);

            // Assertions based on the receipt processor
            assertEquals(3, rp.getReusableCount()); // Expected reusable count
            assertEquals(1, rp.getDisposableCount()); // Expected disposable count
            assertEquals(3, rp.getNonAcceptedCount()); // Expected non-accepted count
            assertEquals(0.25, rp.getDisposableTotal(), 0.01); // Expected disposable total
            assertEquals(1.55, rp.getReusableTotal(), 0.01); // Expected reusable total
            assertEquals(1.80, rp.getTotalForAll(), 0.01); // Expected total for all
        }


        @Test
        void test3() {
            // Prepare test items
            testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
            testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glass", 0.75));
            testItems.add(new Item("Z Bottle | 1L", "pv4og90ymi", "Not Accepted", "Plastic", 0.00));
            testItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glass", 0.50));
            testItems.add(new Item("Y Bottle | 0.33L", "ha3pven757", "Not Accepted", "Glass", 0.00));
            testItems.add(new Item("FG Bottle | 0.5L", "js92hp13rp", "Reusable", "Plastic", 1.00));
            testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glass", 0.00));

            // Run the simulation
            ReceiptProcessor rp = simulator.runSimulation(testItems);

            // Assertions based on the receipt processor
            assertEquals(3, rp.getReusableCount()); // Expected reusable count
            assertEquals(1, rp.getDisposableCount()); // Expected disposable count
            assertEquals(3, rp.getNonAcceptedCount()); // Expected non-accepted count
            assertEquals(0.25, rp.getDisposableTotal(), 0.01); // Expected disposable total
            assertEquals(2.25, rp.getReusableTotal(), 0.01); // Expected reusable total
            assertEquals(2.50, rp.getTotalForAll(), 0.01); // Expected total for all
        }

    }
}
