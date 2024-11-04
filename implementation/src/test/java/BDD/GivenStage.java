package BDD;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import BottleDepositMachine.*;
import BottleDepositMachine.Hardware.*;
import BottleDepositMachine.Software.*;
import Items.Item;
import java.util.ArrayList;
import java.util.List;

public class GivenStage extends Stage<GivenStage> {
    @ProvidedScenarioState
    private BottleDepositMachine bottleDepositMachine;
    @ProvidedScenarioState
    private CardScanner cardScanner;
    @ProvidedScenarioState
    private Display display;
    @ProvidedScenarioState
    private SupervisoryModule supervisoryModule;
    @ProvidedScenarioState
    private InMemoryDatabase inMemoryDatabase;
    @ProvidedScenarioState
    private ReceiptProcessor receiptProcessor;
    @ProvidedScenarioState
    private List<Item> testItems;
    @ProvidedScenarioState
    private Simulator simulator;

    @BeforeStage
    public void setUp() {
        receiptProcessor = new ReceiptProcessor();
        display = new Display();
        bottleDepositMachine = new BottleDepositMachine(display, receiptProcessor);
        cardScanner = new CardScanner(bottleDepositMachine);
        supervisoryModule = new SupervisoryModule(bottleDepositMachine, cardScanner, display, receiptProcessor);
        inMemoryDatabase = new InMemoryDatabase();
        testItems = new ArrayList<>();
        simulator = new Simulator(bottleDepositMachine);
    }

    public GivenStage the_machine_is_ready_with_test_items() {
        testItems.add(new Item("ABC Can | 0.5L", "tmvkrw69le", "Disposable", "Metal", 0.25));
        testItems.add(new Item("DE Bottle | 1L", "xvjix0xaue", "Reusable", "Glass", 0.75));
        testItems.add(new Item("Z Bottle | 1L", "pv4og90ymi", "Not Accepted", "Plastic", 0.00));
        testItems.add(new Item("DE Bottle | 0.75L", "4xpokcvb7c", "Reusable", "Glass", 0.50));
        testItems.add(new Item("Y Bottle | 0.33L", "ha3pven757", "Not Accepted", "Glass", 0.00));
        testItems.add(new Item("FG Bottle | 0.5L", "js92hp13rp", "Reusable", "Plastic", 1.00));
        testItems.add(new Item("Z Bottle | 0.33L", "j03eiqtm2t", "Not Accepted", "Glass", 0.00));
        return self();
    }
}
