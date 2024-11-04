
// ThenStage.java
package BDD;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import BottleDepositMachine.Software.ReceiptProcessor;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThenStage extends Stage<ThenStage> {
    @ScenarioState
    private ReceiptProcessor resultReceiptProcessor;

    public ThenStage the_reusable_count_should_be(int expected) {
        assertEquals(expected, resultReceiptProcessor.getReusableCount());
        return self();
    }

    public ThenStage the_disposable_count_should_be(int expected) {
        assertEquals(expected, resultReceiptProcessor.getDisposableCount());
        return self();
    }

    public ThenStage the_non_accepted_count_should_be(int expected) {
        assertEquals(expected, resultReceiptProcessor.getNonAcceptedCount());
        return self();
    }

    public ThenStage the_disposable_total_should_be(double expected) {
        assertEquals(expected, resultReceiptProcessor.getDisposableTotal(), 0.01);
        return self();
    }

    public ThenStage the_reusable_total_should_be(double expected) {
        assertEquals(expected, resultReceiptProcessor.getReusableTotal(), 0.01);
        return self();
    }

    public ThenStage the_total_for_all_should_be(double expected) {
        assertEquals(expected, resultReceiptProcessor.getTotalForAll(), 0.01);
        return self();
    }
}