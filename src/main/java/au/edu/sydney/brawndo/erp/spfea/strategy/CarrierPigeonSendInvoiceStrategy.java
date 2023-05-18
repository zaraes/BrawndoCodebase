package au.edu.sydney.brawndo.erp.spfea.strategy;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.CarrierPigeon;
import au.edu.sydney.brawndo.erp.ordering.Customer;

public class CarrierPigeonSendInvoiceStrategy implements SendInvoiceStrategy {
    @Override
    public boolean sendInvoice(AuthToken authToken, Customer customer, String data) {
        String pigeonCoopID = customer.getPigeonCoopID();
        if (null != pigeonCoopID) {
            CarrierPigeon.sendInvoice(authToken, customer.getfName(), customer.getlName(), data, pigeonCoopID);
            return true;
        }
        return false;
    }
}
