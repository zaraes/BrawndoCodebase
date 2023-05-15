package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;

public class ProductComparison {
    public static boolean compare(Product p1, Product p2) {
        return p1.getProductName().equals(p2.getProductName()) &&
                p1.getCost() == p2.getCost() &&
                p1.getManufacturingData() == p2.getManufacturingData() &&
                p1.getLicensingData() == p2.getLicensingData() &&
                p1.getMarketingData() == p2.getManufacturingData() &&
                p1.getRecipeData() == p2.getRecipeData() &&
                p1.getSafetyData() == p2.getSafetyData();
    }
}
