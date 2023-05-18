package au.edu.sydney.brawndo.erp.spfea.strategy;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.PhoneCall;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class PhoneCallSendInvoiceStrategy implements SendInvoiceStrategy {
    @Override
    public boolean sendInvoice(AuthToken authToken, Customer customer, String data) {
        String phone = customer.getPhoneNumber();
        if (null != phone) {
            PhoneCall.sendInvoice(authToken, customer.getfName(), customer.getlName(), data, phone);
            return true;
        }
        return false;
    }
}
