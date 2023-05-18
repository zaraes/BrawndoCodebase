package au.edu.sydney.brawndo.erp.spfea.strategy;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Merchandiser;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class MerchandiserSendInvoiceStrategy implements SendInvoiceStrategy {
    @Override
    public boolean sendInvoice(AuthToken authToken, Customer customer, String data) {
        String merchandiser = customer.getMerchandiser();
        String businessName = customer.getBusinessName();
        if (null != merchandiser && null != businessName) {
            Merchandiser.sendInvoice(authToken, customer.getfName(), customer.getlName(), data, merchandiser,businessName);
            return true;
        }
        return false;
    }
}
