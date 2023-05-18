package au.edu.sydney.brawndo.erp.spfea.strategy;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public interface SendInvoiceStrategy {
    boolean sendInvoice(AuthToken authToken, Customer customer, String data);
}
