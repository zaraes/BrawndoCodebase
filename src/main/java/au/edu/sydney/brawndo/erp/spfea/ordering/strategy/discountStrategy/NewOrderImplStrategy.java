package au.edu.sydney.brawndo.erp.spfea.ordering.strategy.discountStrategy;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public class NewOrderImplStrategy implements DiscountStrategy {
    @Override
    public double getTotalCost(Map<Product, Integer> products, double discountRate, int discountThreshold) {
        double cost = 0.0;
        for (Product product: products.keySet()) {
            cost +=  products.get(product) * product.getCost() * discountRate;
        }
        return cost;
    }
}
