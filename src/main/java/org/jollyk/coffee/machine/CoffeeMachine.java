package org.jollyk.coffee.machine;

import lombok.Getter;
import lombok.Setter;
import org.jollyk.coffee.machine.exceptions.IngredientInsufficientException;
import org.jollyk.coffee.machine.exceptions.IngredientUnavailableException;
import org.jollyk.coffee.machine.model.Beverage;
import org.jollyk.coffee.machine.model.Ingredient;
import org.jollyk.coffee.machine.model.Outlets;

import java.util.Map;

@Getter
@Setter
public class CoffeeMachine {
    private Map<Ingredient, Integer> availableIngredientsMap;

    private Outlets outlets;

    private Indicator indicator;

    public CoffeeMachine(Integer numOutlets, Map<Ingredient, Integer> availableIngredients) {
        this.outlets = new Outlets(numOutlets);
        this.availableIngredientsMap = availableIngredients;
        indicator = new Indicator();
    }

    /**
     * Main method to serve beverage
     * @param beverage
     * @throws InterruptedException
     */
    public void serveBeverage(Beverage beverage) throws InterruptedException, IngredientUnavailableException, IngredientInsufficientException {
        //Check if outlet is available to dispense current beverage request
        if(!isOutletAvailable()) {
            System.out.println(String.format("%s cannot be prepared as no outlet is available", beverage));
            return;
        }

        //Check availability of ingredients
        checkUnavailableIngredient(beverage);

        //Consume ingredients and prepare beverage
        consumeIngredientsForBeverage(beverage);
        prepareBeverage(beverage);

        //use outlet to dispense beverage
        this.outlets.dispense(beverage);
    }

    /**
     * Prepare beverage (Assumes equal preparation time for all types of beverages)
     * @param beverage
     * @throws InterruptedException
     */
    private void prepareBeverage(Beverage beverage) throws InterruptedException {
        Thread.sleep(1);    //preparation time
        System.out.println(String.format("%s is prepared", beverage));
    }

    /**
     * Consume ingredients and remove ingredient where quantity falls to 0
     * @param beverage
     */
    private synchronized void consumeIngredientsForBeverage(Beverage beverage) {
        beverage.getIngredients()
                .forEach((ingredient, quantity) -> availableIngredientsMap.put(ingredient, availableIngredientsMap.get(ingredient) - quantity));
        indicator.indicateIfQuantityLow(availableIngredientsMap);
        beverage.getIngredients().values().removeIf(quantity -> quantity==0);
    }

    /**
     * Checks if available ingredients are sufficient to prepare beverage
     * @param beverage
     * @return
     * @throws IngredientUnavailableException
     * @throws IngredientInsufficientException
     */
    private synchronized void checkUnavailableIngredient(Beverage beverage) throws IngredientUnavailableException, IngredientInsufficientException {
        for(Ingredient ingredient: beverage.getIngredients().keySet()) {
            if(!availableIngredientsMap.containsKey(ingredient) ) {
                throw new IngredientUnavailableException(beverage, ingredient);
            } else if(beverage.getIngredients().get(ingredient) > availableIngredientsMap.get(ingredient)) {
                throw new IngredientInsufficientException(beverage, ingredient);
            }
        }
    }

    /**
     * Checks if outlet is available
     * @return
     */
    private boolean isOutletAvailable() {
        return this.outlets.isOutletAvailable();
    }



}
