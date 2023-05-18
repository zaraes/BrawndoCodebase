package au.edu.sydney.brawndo.erp.spfea.products.flyweight;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProductFlyweightFactory {
    private static Map<String, Flyweight> productFlyweights = new HashMap<>();

    public static Flyweight getProductFlyweight(String name,
                                                       double cost,
                                                       double[] manufacturingData,
                                                       double[] recipeData,
                                                       double[] marketingData,
                                                       double[] safetyData,
                                                       double[] licensingData) {
        String flyweightKey =   name +
                                String.valueOf(cost) +
                                Arrays.hashCode(manufacturingData) +
                                Arrays.hashCode(recipeData) +
                                Arrays.hashCode(marketingData) +
                                Arrays.hashCode(safetyData) +
                                Arrays.hashCode(licensingData);

        Flyweight productFlyweight = productFlyweights.get(flyweightKey);

        if (productFlyweight == null) {
            productFlyweight = new ProductFlyweight(name, cost, manufacturingData,
                                                    recipeData, marketingData,
                                                    safetyData, licensingData);
            productFlyweights.put(flyweightKey, productFlyweight);
        }
        return productFlyweight;
    }
}
