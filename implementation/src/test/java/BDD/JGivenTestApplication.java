
// JGivenTestApplication.java
package BDD;

import com.tngtech.jgiven.junit5.ScenarioTest;
import org.junit.jupiter.api.Test;

public class JGivenTestApplication extends ScenarioTest<GivenStage, WhenStage, ThenStage> {

    @Test
    public void bottle_deposit_machine_simulation_test() {
        given().the_machine_is_ready_with_test_items();
        when().the_simulation_is_run();
        then().the_reusable_count_should_be(3)
                .and().the_disposable_count_should_be(1)
                .and().the_non_accepted_count_should_be(3)
                .and().the_disposable_total_should_be(0.25)
                .and().the_reusable_total_should_be(2.25)
                .and().the_total_for_all_should_be(2.50);
    }
}