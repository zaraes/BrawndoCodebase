package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.discountStrategy.DiscountStrategy;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.generateInvoiceStrategy.GenerateInvoiceOrderStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConcreteOrder implements Order {
    private Map<Product, Integer> products = new HashMap<>();
    private int id;
    private LocalDateTime date;
    private double discountRate;
    private int discountThreshold;
    private int customerID;
    private boolean finalised = false;
    private DiscountStrategy discountStrategy;
    private GenerateInvoiceOrderStrategy invoiceStrategy;

    public ConcreteOrder(int id, LocalDateTime date, double discountRate, int customerID, DiscountStrategy discountStrategy, int discountThreshold, GenerateInvoiceOrderStrategy invoiceStrategy) {
        this.id = id;
        this.date = date;
        this.discountRate = discountRate;
        this.customerID = customerID;
        this.discountStrategy = discountStrategy;
        this.discountThreshold = discountThreshold;
        this.invoiceStrategy = invoiceStrategy;
    }
    @Override
    public int getOrderID() {
        return id;
    }

    @Override
    public double getTotalCost() {
        return discountStrategy.getTotalCost(this.products, discountRate, discountThreshold);
    }

    @Override
    public LocalDateTime getOrderDate() {
        return date;
    }

    @Override
    public void setProduct(Product product, int qty) {
        if (finalised) throw new IllegalStateException("Order was already finalised.");

        // We can't rely on like products having the same object identity since they get
        // rebuilt over the network, so we had to check for presence and same values

        for (Product contained: products.keySet()) {
            if (contained.getCost() == product.getCost() &&
                    contained.getProductName().equals(product.getProductName()) &&
                    Arrays.equals(contained.getManufacturingData(), product.getManufacturingData()) &&
                    Arrays.equals(contained.getRecipeData(), product.getRecipeData()) &&
                    Arrays.equals(contained.getMarketingData(), product.getMarketingData()) &&
                    Arrays.equals(contained.getSafetyData(), product.getSafetyData()) &&
                    Arrays.equals(contained.getLicensingData(), product.getLicensingData())) {
                product = contained;
                break;
            }
        }

        products.put(product, qty);
    }

    @Override
    public Set<Product> getAllProducts() {
        return products.keySet();
    }

    @Override
    public int getProductQty(Product product) {
        // We can't rely on like products having the same object identity since they get
        // rebuilt over the network, so we had to check for presence and same values

        for (Product contained: products.keySet()) {
            if (contained.getCost() == product.getCost() &&
                    contained.getProductName().equals(product.getProductName()) &&
                    Arrays.equals(contained.getManufacturingData(), product.getManufacturingData()) &&
                    Arrays.equals(contained.getRecipeData(), product.getRecipeData()) &&
                    Arrays.equals(contained.getMarketingData(), product.getMarketingData()) &&
                    Arrays.equals(contained.getSafetyData(), product.getSafetyData()) &&
                    Arrays.equals(contained.getLicensingData(), product.getLicensingData())) {
                product = contained;
                break;
            }
        }

        Integer result = products.get(product);
        return null == result ? 0 : result;
    }

    @Override
    public String generateInvoiceData() {
        return this.invoiceStrategy.generateInvoiceData(this, this.products);
    }

    @Override
    public int getCustomer() {
        return customerID;
    }

    @Override
    public void finalise() {
        this.finalised = true;
    }

    @Override
    public Order copy() {
        Order copy = new ConcreteOrder(id, date, discountRate, customerID, discountStrategy, discountThreshold, invoiceStrategy);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f", id, getTotalCost());
    }

    @Override
    public String longDesc() {
        double fullCost = 0.0;
        double discountedCost = getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(products.keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * products.get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    products.get(product),
                    product.getCost(),
                    subtotal));
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost
        );
    }

    protected boolean isFinalised() {
        return finalised;
    }

    protected double getDiscountRate() {
        return this.discountRate;
    }

    protected int getDiscountThreshold() {
        return this.discountThreshold;
    }

    protected Map<Product, Integer> getProducts() {
        return products;
    }
}
