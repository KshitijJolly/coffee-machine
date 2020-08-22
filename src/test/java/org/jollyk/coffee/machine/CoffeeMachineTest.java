package org.jollyk.coffee.machine;

import org.jollyk.coffee.machine.exceptions.IngredientInsufficientException;
import org.jollyk.coffee.machine.exceptions.IngredientUnavailableException;
import org.jollyk.coffee.machine.model.Beverage;
import org.jollyk.coffee.machine.model.Ingredient;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CoffeeMachineTest {

    @Test
    public void testConsumeIngredients() throws Exception {
        Map<Ingredient, Integer> availableIngredients = new HashMap<>() {{
            put(new Ingredient("hot_water"), 1000);
            put(new Ingredient("hot_milk"), 2000);
            put(new Ingredient("elaichi_mix"), 15);
        }};

        Map<Ingredient, Integer> ingredientsForElaichiTea = new HashMap<>() {{
            put(new Ingredient("hot_water"), 100);
            put(new Ingredient("hot_milk"), 200);
            put(new Ingredient("elaichi_mix"), 10);
        }};

        Beverage elaichiTea = new Beverage("elaichi_tea", ingredientsForElaichiTea);
        CoffeeMachine coffeeMachine = new CoffeeMachine(3, availableIngredients);

        Map<Ingredient, Integer> updatedIngredients = coffeeMachine.getAvailableIngredientsMap();
        coffeeMachine.serveBeverage(elaichiTea);
        Assert.assertEquals((Integer)900, updatedIngredients.get(new Ingredient("hot_water")));
        Assert.assertEquals((Integer)1800, updatedIngredients.get(new Ingredient("hot_milk")));
        Assert.assertEquals((Integer)5, updatedIngredients.get(new Ingredient("elaichi_mix")));
    }

    @Test(expected = IngredientUnavailableException.class)
    public void testUnavailableIngredient() throws Exception {
        Map<Ingredient, Integer> availableIngredients = new HashMap<>() {{
            put(new Ingredient("hot_water"), 1000);
            put(new Ingredient("hot_milk"), 2000);
        }};

        Map<Ingredient, Integer> ingredientsForElaichiTea = new HashMap<>() {{
            put(new Ingredient("hot_water"), 100);
            put(new Ingredient("hot_milk"), 200);
            put(new Ingredient("elaichi_mix"), 10);
        }};

        Beverage elaichiTea = new Beverage("elaichi_tea", ingredientsForElaichiTea);
        CoffeeMachine coffeeMachine = new CoffeeMachine(3, availableIngredients);

        coffeeMachine.serveBeverage(elaichiTea);
    }

    @Test(expected = IngredientInsufficientException.class)
    public void testInsufficientIngredient() throws Exception {
        Map<Ingredient, Integer> availableIngredients = new HashMap<>() {{
            put(new Ingredient("hot_water"), 1000);
            put(new Ingredient("hot_milk"), 2000);
            put(new Ingredient("elaichi_mix"), 9);
        }};

        Map<Ingredient, Integer> ingredientsForElaichiTea = new HashMap<>() {{
            put(new Ingredient("hot_water"), 100);
            put(new Ingredient("hot_milk"), 200);
            put(new Ingredient("elaichi_mix"), 10);
        }};

        Beverage elaichiTea = new Beverage("elaichi_tea", ingredientsForElaichiTea);
        CoffeeMachine coffeeMachine = new CoffeeMachine(3, availableIngredients);

        coffeeMachine.serveBeverage(elaichiTea);
    }

}

