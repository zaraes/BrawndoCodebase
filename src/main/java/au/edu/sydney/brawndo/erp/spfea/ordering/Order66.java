package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Note from Frito: this is a personal customer, flat discounted order.
 *
 * Also note from Frito: Yes, there are another 64 Order types cut from this release.
 * There are a bunch of other customer types and different sorts of discounts out there.
 *
 * It would be really good if the new design could handle those well.
 */
@SuppressWarnings("Duplicates")
public class Order66 implements Order {
    private Map<Product, Integer> products = new HashMap<>();
    private int id;
    private LocalDateTime date;
    private double discountRate;
    private int customerID;
    private boolean finalised = false;

    public Order66(int id, LocalDateTime date, double discountRate, int customerID) {
        this.id = id;
        this.date = date;
        this.discountRate = discountRate;
        this.customerID = customerID;
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
    public int getCustomer() {
        return customerID;
    }

    @Override
    public Order copy() {
        Order copy = new Order66(id, date, discountRate, customerID);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    @Override
    public String generateInvoiceData() {
        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Brawndo© order!\n");
        sb.append("Your order comes to: $");
        sb.append(String.format("%,.2f", getTotalCost()));
        sb.append("\nPlease see below for details:\n");
        List<Product> keyList = new ArrayList<>(products.keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            sb.append("\tProduct name: ");
            sb.append(product.getProductName());
            sb.append("\tQty: ");
            sb.append(products.get(product));
            sb.append("\tCost per unit: ");
            sb.append(String.format("$%,.2f", product.getCost()));
            sb.append("\tSubtotal: ");
            sb.append(String.format("$%,.2f\n", product.getCost() * products.get(product)));
        }

        return sb.toString();
    }

    @Override
    public double getTotalCost() {
        double cost = 0.0;
        for (Product product: products.keySet()) {
            cost +=  products.get(product) * product.getCost() * discountRate;
        }
        return cost;
    }

    protected double getDiscountRate() {
        return this.discountRate;
    }

    protected Map<Product, Integer> getProducts() {
        return products;
    }

    @Override
    public int getOrderID() {
        return id;
    }

    @Override
    public void finalise() {
        this.finalised = true;
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
}
