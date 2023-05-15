package au.edu.sydney.brawndo.erp.spfea.ordering.strategy.generateInvoiceStrategy;

import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class GenerateInvoiceSubscriptionComplexStrategy implements GenerateInvoiceSubscriptionStrategy {
    @Override
    public String generateInvoiceData(SubscriptionOrder order, Map<Product, Integer> products) {
        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Brawndo© order!\n");
        sb.append("Your order comes to: $");
        sb.append(String.format("%,.2f", order.getRecurringCost()));
        sb.append(" each week, with a total overall cost of: $");
        sb.append(String.format("%,.2f", order.getTotalCost()));
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
}
