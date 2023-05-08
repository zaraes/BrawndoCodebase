package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;

public class ProductImpl implements Product {

    private final String name;
    private final double[] manufacturingData;
    private final double cost;
    private double[] recipeData;
    private double[] marketingData;
    private double[] safetyData;
    private double[] licensingData;

    public ProductImpl(String name,
                       double cost,
                       double[] manufacturingData,
                       double[] recipeData,
                       double[] marketingData,
                       double[] safetyData,
                       double[] licensingData) {
        this.name = name;
        this.cost = cost;
        this.manufacturingData = manufacturingData;
        this.recipeData = recipeData;
        this.marketingData = marketingData;
        this.safetyData = safetyData;
        this.licensingData = licensingData;
    }

    @Override
    public String getProductName() {
        return name;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double[] getManufacturingData() {
        return manufacturingData;
    }

    @Override
    public double[] getRecipeData() {
        return recipeData;
    }

    @Override
    public double[] getMarketingData() {
        return marketingData;
    }

    @Override
    public double[] getSafetyData() {
        return safetyData;
    }

    @Override
    public double[] getLicensingData() {
        return licensingData;
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }
}
