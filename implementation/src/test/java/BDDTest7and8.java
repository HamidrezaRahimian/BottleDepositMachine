import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit5.SimpleScenarioTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import BottleDepositMachine.*;
import BottleDepositMachine.Hardware.*;
import BottleDepositMachine.Software.*;
import Items.Item;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Bottle Deposit Machine BDD Tests")
public class BDDTest7and8 extends SimpleScenarioTest<BDDTest7and8.TestStage> {

    @Test
    @DisplayName("Bottle deposit machine simulation test 7")
    public void bottle_deposit_machine_simulation_test_7() {
        given().the_machine_is_ready_with_test_items_for_test_7();
        when().the_simulation_is_run();
        then().all_counts_and_totals_should_be_correct_for_test_7(2, 2, 3, 0.50, 1.75, 2.25);
    }

    @Test
    @DisplayName("Bottle deposit machine simulation test 8")
    public void bottle_deposit_machine_simulation_test_8() {
        given().the_machine_is_ready_with_test_items_for_test_8();
        when().the_simulation_is_run();
        then().all_counts_and_totals_should_be_correct_for_test_8(3, 1, 4, 0.25, 3.05, 3.30);
    }

    static class TestStage extends Stage<TestStage> {
        @ProvidedScenarioState
        private BottleDepositMachine bottleDepositMachine;
        @ProvidedScenarioState
        private Simulator simulator;
        @ProvidedScenarioState
        private List<Item> testItems;
        @ProvidedScenarioState
        private ReceiptProcessor resultReceiptProcessor;

        public TestStage the_machine_is_ready_with_test_items_for_test_7() {
            // Setup
            ReceiptProcessor receiptProcessor = new ReceiptProcessor();
            Display display = new Display();
            bottleDepositMachine = new BottleDepositMachine(display, receiptProcessor);
            simulator = new Simulator(bottleDepositMachine);
            testItems = new ArrayList<>();

            testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
            testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glass", 0.00));
            testItems.add(new Item("FG Bottle | 0.5L", "js92hp13rp", "Reusable", "Plastic", 1.00));
            testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glass", 0.75));
            testItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
            testItems.add(new Item("ABC Can | 0.33L", "r8yz7clkz4", "Disposable", "Metal", 0.25));
            testItems.add(new Item("Y Bottle | 0.5L", "447ds7u9j4", "Not Accepted", "Glass", 0.00));

            return self();
        }

        public TestStage the_simulation_is_run() {
            resultReceiptProcessor = simulator.runSimulation(testItems);
            return self();
        }

        public TestStage all_counts_and_totals_should_be_correct_for_test_7(int expectedReusableCount, int expectedDisposableCount,
                                                                 int expectedNonAcceptedCount, double expectedDisposableTotal,
                                                                 double expectedReusableTotal, double expectedTotalForAll) {
            assertNotNull(resultReceiptProcessor, "ReceiptProcessor should not be null");

            assertEquals(expectedReusableCount, resultReceiptProcessor.getReusableCount(), "Reusable count does not match");
            assertEquals(expectedDisposableCount, resultReceiptProcessor.getDisposableCount(), "Disposable count does not match");
            assertEquals(expectedNonAcceptedCount, resultReceiptProcessor.getNonAcceptedCount(), "Non-accepted count does not match");

            assertEquals(expectedDisposableTotal, resultReceiptProcessor.getDisposableTotal(), 0.01, "Disposable total does not match");
            assertEquals(expectedReusableTotal, resultReceiptProcessor.getReusableTotal(), 0.01, "Reusable total does not match");
            assertEquals(expectedTotalForAll, resultReceiptProcessor.getTotalForAll(), 0.01, "Total for all does not match");

            return self();
        }


        public TestStage the_machine_is_ready_with_test_items_for_test_8() {
            // Setup
            ReceiptProcessor receiptProcessor = new ReceiptProcessor();
            Display display = new Display();
            bottleDepositMachine = new BottleDepositMachine(display, receiptProcessor);
            simulator = new Simulator(bottleDepositMachine);
            testItems = new ArrayList<>();

            testItems.add(new Item("Y Bottle | 1L", "5hqoa0ean4", "Not Accepted", "Glass", 0.00));
            testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
            testItems.add(new Item("DE Bottle | 0.5L", "3jdwml7w52", "Reusable", "Glass", 0.30));
            testItems.add(new Item("Z Bottle | 1L", "pv4og90ymi", "Not Accepted", "Plastic", 0.00));
            testItems.add(new Item("FG Bottle | 1L", "8hgij9rqv5", "Reusable", "Plastic", 2.00));
            testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glass", 0.75));
            testItems.add(new Item("X Can | 0.5L", "3a6mr6o7sl", "Not Accepted", "Metal", 0.00));
            testItems.add(new Item("Y Bottle | 0.33L", "ha3pven757", "Not Accepted", "Glass", 0.00));

            return self();
        }

        public TestStage all_counts_and_totals_should_be_correct_for_test_8(int expectedReusableCount, int expectedDisposableCount,
                                                                            int expectedNonAcceptedCount, double expectedDisposableTotal,
                                                                            double expectedReusableTotal, double expectedTotalForAll) {
            assertNotNull(resultReceiptProcessor, "ReceiptProcessor should not be null");

            assertEquals(expectedReusableCount, resultReceiptProcessor.getReusableCount(), "Reusable count does not match");
            assertEquals(expectedDisposableCount, resultReceiptProcessor.getDisposableCount(), "Disposable count does not match");
            assertEquals(expectedNonAcceptedCount, resultReceiptProcessor.getNonAcceptedCount(), "Non-accepted count does not match");

            assertEquals(expectedDisposableTotal, resultReceiptProcessor.getDisposableTotal(), 0.01, "Disposable total does not match");
            assertEquals(expectedReusableTotal, resultReceiptProcessor.getReusableTotal(), 0.01, "Reusable total does not match");
            assertEquals(expectedTotalForAll, resultReceiptProcessor.getTotalForAll(), 0.01, "Total for all does not match");

            return self();
        }
    }
}
