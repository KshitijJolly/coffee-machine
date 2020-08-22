package org.jollyk.coffee.machine;

import org.jollyk.coffee.machine.exceptions.IngredientInsufficientException;
import org.jollyk.coffee.machine.exceptions.IngredientUnavailableException;
import org.jollyk.coffee.machine.model.Beverage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CoffeeMachineTestIT {

    //Integration Test
    @Test
    public void testCoffeeMachineIT() throws Exception {
        List<Beverage> beverageList = new ArrayList<>();
        CoffeeMachine coffeeMachine = TestUtil.instantiateMachineAndBeverageTestData(beverageList, "test_data.json");

        beverageList.parallelStream().forEach(beverage -> {
            try {
                coffeeMachine.serveBeverage(beverage);
            } catch (IngredientUnavailableException e) {
                System.out.println(e.getMessage());
            } catch (IngredientInsufficientException e) {
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
