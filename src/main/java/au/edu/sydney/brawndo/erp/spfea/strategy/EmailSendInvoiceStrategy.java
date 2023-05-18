package au.edu.sydney.brawndo.erp.spfea.strategy;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Email;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class EmailSendInvoiceStrategy implements SendInvoiceStrategy {
    @Override
    public boolean sendInvoice(AuthToken authToken, Customer customer, String data) {
        String email = customer.getEmailAddress();
        if (null != email) {
            Email.sendInvoice(authToken, customer.getfName(), customer.getlName(), data, email);
            return true;
        }
        return false;
    }
}
