package au.edu.sydney.brawndo.erp.spfea.ordering.strategy.discountStrategy;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public class BusinessBulkDiscountStrategy implements DiscountStrategy {
    @Override
    public double getTotalCost(Map<Product, Integer> products, double discountRate, int discountThreshold) {
        double cost = 0.0;
        for (Product product: products.keySet()) {
            int count = products.get(product);
            if (count >= discountThreshold) {
                cost +=  count * product.getCost() * discountRate;
            } else {
                cost +=  count * product.getCost();
            }
        }
        return cost;
    }
}
