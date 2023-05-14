package au.edu.sydney.brawndo.erp.spfea.ordering.strategy.generateInvoiceStrategy;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public interface GenerateInvoiceOrderStrategy {
    String generateInvoiceData(Order order, Map<Product, Integer> products);
}
