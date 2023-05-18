package au.edu.sydney.brawndo.erp.spfea.products.flyweight;

public interface Flyweight {
    public String getName();

    public double[] getManufacturingData();

    public double[] getRecipeData();

    public double[] getMarketingData();

    public double[] getSafetyData();

    public double[] getLicensingData();

    public double getCost();
}
