package au.edu.sydney.brawndo.erp.spfea.ordering.strategy.generateInvoiceStrategy;

import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.util.Map;

public class GenerateInvoiceSubscriptionSimpleStrategy implements GenerateInvoiceSubscriptionStrategy {
    @Override
    public String generateInvoiceData(SubscriptionOrder order, Map<Product, Integer> products) {
        return String.format("Your business account will be charged: $%,.2f each week, with a total overall cost of: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", order.getRecurringCost(), order.getTotalCost());
    }
}
