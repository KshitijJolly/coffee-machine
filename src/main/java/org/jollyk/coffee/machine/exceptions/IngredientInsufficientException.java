package org.jollyk.coffee.machine.exceptions;

import org.jollyk.coffee.machine.model.Beverage;
import org.jollyk.coffee.machine.model.Ingredient;

public class IngredientInsufficientException extends Exception {

    public IngredientInsufficientException(Beverage beverage, Ingredient insufficientIngredient) {
        super(String.format("%s cannot be prepared because %s is not sufficient", beverage, insufficientIngredient));
    }
}
