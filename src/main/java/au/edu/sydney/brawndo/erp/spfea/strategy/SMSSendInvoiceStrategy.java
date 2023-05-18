package au.edu.sydney.brawndo.erp.spfea.strategy;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.SMS;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class SMSSendInvoiceStrategy implements SendInvoiceStrategy {
    @Override
    public boolean sendInvoice(AuthToken authToken, Customer customer, String data) {
        String smsPhone = customer.getPhoneNumber();
        if (null != smsPhone) {
            SMS.sendInvoice(authToken, customer.getfName(), customer.getlName(), data, smsPhone);
            return true;
        }
        return false;
    }
}
