package au.edu.sydney.brawndo.erp.spfea.ordering.strategy.generateInvoiceStrategy;

import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.util.Map;

public interface GenerateInvoiceSubscriptionStrategy {
    String generateInvoiceData(SubscriptionOrder order, Map<Product, Integer> products);
}
