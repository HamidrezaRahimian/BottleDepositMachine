
// WhenStage.java
package BDD;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import BottleDepositMachine.Software.*;
import Items.Item;
import java.util.List;

public class WhenStage extends Stage<WhenStage> {
    @ScenarioState
    private Simulator simulator;
    @ScenarioState
    private List<Item> testItems;
    @ProvidedScenarioState
    private ReceiptProcessor resultReceiptProcessor;

    public WhenStage the_simulation_is_run() {
        resultReceiptProcessor = simulator.runSimulation(testItems);
        return self();
    }
}
