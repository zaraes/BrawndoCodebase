package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.*;
import au.edu.sydney.brawndo.erp.ordering.Customer;

import java.util.Arrays;
import java.util.List;

public class ContactHandler {
    public static boolean sendInvoice(AuthToken token, Customer customer, List<ContactMethod> priority, String data) {
        for (ContactMethod method : priority) {
            switch (method) {
                case SMS:
                    String smsPhone = customer.getPhoneNumber();
                    if (null != smsPhone) {
                        SMS.sendInvoice(token, customer.getfName(), customer.getlName(), data, smsPhone);
                        return true;
                    }
                    break;
                case MAIL:
                    String address = customer.getAddress();
                    String suburb = customer.getSuburb();
                    String state = customer.getState();
                    String postcode = customer.getPostCode();
                    if (null != address && null != suburb &&
                        null != state && null != postcode) {
                        Mail.sendInvoice(token, customer.getfName(), customer.getlName(), data, address, suburb, state, postcode);
                        return true;
                    }
                    break;
                case EMAIL:
                    String email = customer.getEmailAddress();
                    if (null != email) {
                        Email.sendInvoice(token, customer.getfName(), customer.getlName(), data, email);
                        return true;
                    }
                    break;
                case PHONECALL:
                    String phone = customer.getPhoneNumber();
                    if (null != phone) {
                        PhoneCall.sendInvoice(token, customer.getfName(), customer.getlName(), data, phone);
                        return true;
                    }
                    break;
                case MERCHANDISER:
                    String merchandiser = customer.getMerchandiser();
                    String businessName = customer.getBusinessName();
                    if (null != merchandiser && null != businessName) {
                        Merchandiser.sendInvoice(token, customer.getfName(), customer.getlName(), data, merchandiser,businessName);
                        return true;
                    }
                    break;
                case CARRIER_PIGEON:
                    String pigeonCoopID = customer.getPigeonCoopID();
                    if (null != pigeonCoopID) {
                        CarrierPigeon.sendInvoice(token, customer.getfName(), customer.getlName(), data, pigeonCoopID);
                        return true;
                    }
                    break;
                default:
                    return false;
            }
        }
        return false;
    }
    public static List<String> getKnownMethods() {
        return Arrays.asList(
                "Carrier Pigeon",
                "Email",
                "Mail",
                "Merchandiser",
                "Phone call",
                "SMS"
        );
    }
}
