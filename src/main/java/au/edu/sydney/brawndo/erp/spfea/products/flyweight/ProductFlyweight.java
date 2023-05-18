package au.edu.sydney.brawndo.erp.spfea.products.flyweight;

public class ProductFlyweight implements Flyweight {
    private final String name;
    private final double[] manufacturingData;
    private final double cost;
    private final double[] recipeData;
    private final double[] marketingData;
    private final double[] safetyData;
    private final double[] licensingData;

    public ProductFlyweight(String name,
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
    public String getName() {
        return this.name;
    }

    @Override
    public double[] getManufacturingData() {
        return this.manufacturingData;
    }

    @Override
    public double[] getRecipeData() {
        return this.recipeData;
    }

    @Override
    public double[] getMarketingData() {
        return this.marketingData;
    }

    @Override
    public double[] getSafetyData() {
        return this.safetyData;
    }

    @Override
    public double[] getLicensingData() {
        return this.licensingData;
    }

    @Override
    public double getCost() {
        return this.cost;
    }
}
