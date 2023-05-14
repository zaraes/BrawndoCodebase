package au.edu.sydney.brawndo.erp.spfea.ordering.strategy.generateInvoiceStrategy;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public class GenerateInvoiceOrderSimpleStrategy implements GenerateInvoiceOrderStrategy {
    @Override
    public String generateInvoiceData(Order order, Map<Product, Integer> products) {
        return String.format("Your business account has been charged: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", order.getTotalCost());

    }
}
