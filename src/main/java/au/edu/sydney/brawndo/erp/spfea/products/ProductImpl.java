package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.products.flyweight.Flyweight;
import au.edu.sydney.brawndo.erp.spfea.products.flyweight.ProductFlyweightFactory;

public class ProductImpl implements Product {
    private final Flyweight productFlyweight;
    public ProductImpl(String name,
                       double cost,
                       double[] manufacturingData,
                       double[] recipeData,
                       double[] marketingData,
                       double[] safetyData,
                       double[] licensingData) {
        this.productFlyweight = ProductFlyweightFactory.getProductFlyweight(name,
                                                                            cost,
                                                                            manufacturingData,
                                                                            recipeData,
                                                                            marketingData,
                                                                            safetyData,
                                                                            licensingData);

    }

    @Override
    public String getProductName() {
        return productFlyweight.getName();
    }

    @Override
    public double getCost() {
        return productFlyweight.getCost();
    }

    @Override
    public double[] getManufacturingData() {
        return productFlyweight.getManufacturingData();
    }

    @Override
    public double[] getRecipeData() {
        return productFlyweight.getRecipeData();
    }

    @Override
    public double[] getMarketingData() {
        return productFlyweight.getMarketingData();
    }

    @Override
    public double[] getSafetyData() {
        return productFlyweight.getSafetyData();
    }

    @Override
    public double[] getLicensingData() {
        return productFlyweight.getLicensingData();
    }

    @Override
    public String toString() {
        return String.format("%s", productFlyweight.getName());
    }
}
