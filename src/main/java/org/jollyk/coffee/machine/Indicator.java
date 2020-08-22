package org.jollyk.coffee.machine;

import org.jollyk.coffee.machine.model.Ingredient;

import java.util.Map;

public class Indicator {

    private int LOW_QUANTITY = 5;

    public void indicateIfQuantityLow(Map<Ingredient, Integer> availableIngredients) {
        availableIngredients.entrySet().forEach(ingredientEntry -> {
            if(ingredientEntry.getValue() < LOW_QUANTITY) {
                System.out.println(String.format("Quantity of %s is low. Please refill.", ingredientEntry.getKey()));
            }
        });
    }
}
