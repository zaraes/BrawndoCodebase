package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.discountStrategy.DiscountStrategy;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.generateInvoiceStrategy.GenerateInvoiceSubscriptionStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConcreteSubscriptionOrder implements SubscriptionOrder {
    private Map<Product, Integer> products = new HashMap<>();
    private int id;
    private LocalDateTime date;
    private double discountRate;
    private int discountThreshold;
    private int customerID;
    private boolean finalised = false;
    private DiscountStrategy discountStrategy;
    private int numShipments;
    private GenerateInvoiceSubscriptionStrategy invoiceStrategy;

    public ConcreteSubscriptionOrder(int id, LocalDateTime date, double discountRate, int customerID, DiscountStrategy discountStrategy, int discountThreshold, int numShipments, GenerateInvoiceSubscriptionStrategy invoiceStrategy) {
        this.id = id;
        this.date = date;
        this.discountRate = discountRate;
        this.customerID = customerID;
        this.discountThreshold = discountThreshold;
        this.discountStrategy = discountStrategy;
        this.numShipments = numShipments;
        this.invoiceStrategy = invoiceStrategy;
    }

    @Override
    public int getOrderID() {
        return id;
    }

    @Override
    public double getTotalCost() {
        return discountStrategy.getTotalCost(this.products, discountRate, discountThreshold) * numShipments;
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
        return this.invoiceStrategy.generateInvoiceData(this, products);
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
        Order copy = new ConcreteSubscriptionOrder(id, date, discountRate, customerID, discountStrategy, discountThreshold, numShipments, invoiceStrategy);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f per shipment, $%,.2f total", getOrderID(), getRecurringCost(), getTotalCost());
    }

    @Override
    public String longDesc() {
        double fullCost = 0.0;
        double discountedCost = getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(getProducts().keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * getProducts().get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    getProducts().get(product),
                    product.getCost(),
                    subtotal));
        }
        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of shipments: %d\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                getOrderID(),
                getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                numShipments,
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost,
                getTotalCost()
        );
    }

    @Override
    public double getRecurringCost() {
        return this.getTotalCost();
    }

    @Override
    public int numberOfShipmentsOrdered() {
        return numShipments;
    }

    protected Map<Product, Integer> getProducts() {
        return products;
    }
}
