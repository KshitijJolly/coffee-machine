package org.jollyk.coffee.machine.exceptions;

import org.jollyk.coffee.machine.model.Beverage;
import org.jollyk.coffee.machine.model.Ingredient;

public class IngredientUnavailableException extends Exception {

    public IngredientUnavailableException(Beverage beverage, Ingredient unavailableIngredient) {
        super(String.format("%s cannot be prepared because %s is not available", beverage, unavailableIngredient));
    }
}
