package au.edu.sydney.brawndo.erp.spfea.ordering.strategy.discountStrategy;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public interface DiscountStrategy {
    double getTotalCost(Map<Product, Integer> products, double discountRate, int discountThreshold);
}
