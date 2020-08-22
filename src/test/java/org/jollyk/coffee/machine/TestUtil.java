package org.jollyk.coffee.machine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jollyk.coffee.machine.model.Beverage;
import org.jollyk.coffee.machine.model.Ingredient;

import java.io.IOException;
import java.util.*;

public class TestUtil {

    /**
     * Parses test json. Returns a new CoffeeMachine object and inserts records in beverage list to test orders
     * NOTE: Please pass an initialized empty ArrayList for beverage
     * @param beverageList
     * @param testFileName
     * @return
     * @throws IOException
     */
    public static CoffeeMachine instantiateMachineAndBeverageTestData(List<Beverage> beverageList, String testFileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(CoffeeMachineTestIT.class.getResourceAsStream("/" + testFileName)).get("machine");

        int numOutlets = json.get("outlets").get("count_n").asInt();

        TypeReference<HashMap<Ingredient, Integer>> typeRef
                = new TypeReference<>() {};
        JsonNode beveragesNode = json.get("beverages");
        Map<Ingredient, Integer> availableIngredients = mapper.convertValue(json.get("total_items_quantity"), typeRef);

        Iterator<Map.Entry<String, JsonNode>> fields = beveragesNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> beverageEntry = fields.next();
            Map<Ingredient, Integer> ingredientsMap =  mapper.convertValue(beverageEntry.getValue(), typeRef);
            beverageList.add(new Beverage(beverageEntry.getKey(), ingredientsMap));
        }

        return new CoffeeMachine(numOutlets, availableIngredients);
    }
}
