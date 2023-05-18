package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.*;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.strategy.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactHandler {
    static Map<ContactMethod, SendInvoiceStrategy> strategyMap = new HashMap<>();
    public static boolean sendInvoice(AuthToken token, Customer customer, List<ContactMethod> priority, String data) {
        getAllSendInvoiceStrategies();
        for (ContactMethod method : priority) {
            SendInvoiceStrategy strategy = strategyMap.get(method);
            if (strategy != null && strategy.sendInvoice(token, customer, data)) {
                return strategy.sendInvoice(token, customer, data);
            }
        }
        return false;
    }

    public static void getAllSendInvoiceStrategies() {
        strategyMap.put(ContactMethod.SMS, new SMSSendInvoiceStrategy());
        strategyMap.put(ContactMethod.MAIL, new MailSendInvoiceStrategy());
        strategyMap.put(ContactMethod.EMAIL, new EmailSendInvoiceStrategy());
        strategyMap.put(ContactMethod.PHONECALL, new PhoneCallSendInvoiceStrategy());
        strategyMap.put(ContactMethod.MERCHANDISER, new MerchandiserSendInvoiceStrategy());
        strategyMap.put(ContactMethod.CARRIER_PIGEON, new CarrierPigeonSendInvoiceStrategy());
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
