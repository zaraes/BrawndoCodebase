package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class Order66Subscription extends Order66 implements SubscriptionOrder {

    private int numShipments;

    public Order66Subscription(int id, LocalDateTime date, double discountRate, int customerID, int numShipments) {
        super(id, date, discountRate, customerID);
        this.numShipments = numShipments;
    }

    @Override
    public double getRecurringCost() {
        return super.getTotalCost();
    }

    @Override
    public int numberOfShipmentsOrdered() {
        return numShipments;
    }

    @Override
    public double getTotalCost() {
        return super.getTotalCost() * numShipments;
    }

    @Override
    public String generateInvoiceData() {
        Map<Product, Integer> products = getProducts();

        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Brawndo© order!\n");
        sb.append("Your order comes to: $");
        sb.append(String.format("%,.2f", getRecurringCost()));
        sb.append(" each week, with a total overall cost of: $");
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
    public Order copy() {
        Map<Product, Integer> products = super.getProducts();

        Order copy = new Order66Subscription(getOrderID(), getOrderDate(), getDiscountRate(), getCustomer(), numShipments);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f per shipment, $%,.2f total", super.getOrderID(), getRecurringCost(), super.getTotalCost());
    }

    @Override
    public String longDesc() {
        double fullCost = 0.0;
        double discountedCost = super.getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(super.getProducts().keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * super.getProducts().get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    super.getProducts().get(product),
                    product.getCost(),
                    subtotal));
        }

        return String.format(super.isFinalised() ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of shipments: %d\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                super.getOrderID(),
                super.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                numShipments,
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost,
                getTotalCost()
        );
    }
}
