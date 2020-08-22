package org.jollyk.coffee.machine.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Beverage {
    private String name;    //Name of beverage

    private Map<Ingredient, Integer> ingredients;   //Map of ingredient to quantity needed (in ml)

    public Beverage(String name, Map<Ingredient, Integer> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
