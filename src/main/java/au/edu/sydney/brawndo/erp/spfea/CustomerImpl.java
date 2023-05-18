package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;

import java.util.function.Supplier;

public class CustomerImpl implements Customer {

    private final int id;
    private AuthToken token;
    private LazyLoad<String> fName;
    private LazyLoad<String> lName;
    private LazyLoad<String> phoneNumber;
    private LazyLoad<String> emailAddress;
    private LazyLoad<String> address;
    private LazyLoad<String> suburb;
    private LazyLoad<String> state;
    private LazyLoad<String> postCode;
    private LazyLoad<String> merchandiser;
    private LazyLoad<String> businessName;
    private LazyLoad<String> pigeonCoopID;

    public CustomerImpl(AuthToken token, int id) {
        this.id = id;
        this.token = token;
        this.fName = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "fName"));
        this.lName = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "lName"));
        this.phoneNumber = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "phoneNumber"));
        this.emailAddress = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "emailAddress"));
        this.address = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "address"));
        this.suburb = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "suburb"));
        this.state = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "state"));
        this.postCode = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "postCode"));
        this.merchandiser = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "merchandiser"));
        this.businessName = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "businessName"));
        this.pigeonCoopID = setupLazyAttribute(() -> TestDatabase.getInstance().getCustomerField(token, id, "pigeonCoopID"));
    }

    private <T> LazyLoad<T> setupLazyAttribute(Supplier<T> initialiser) {
        return new LazyLoad<>(initialiser);
    }

    public int getId() {
        return id;
    }

    @Override
    public String getfName() {
        return fName.getOrInitializeValue();
    }

    @Override
    public String getlName() {
        return lName.getOrInitializeValue();
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber.getOrInitializeValue();
    }

    @Override
    public String getEmailAddress() {
        return emailAddress.getOrInitializeValue();
    }

    @Override
    public String getAddress() {
        return address.getOrInitializeValue();
    }

    @Override
    public String getSuburb() {
        return suburb.getOrInitializeValue();
    }

    @Override
    public String getState() {
        return state.getOrInitializeValue();
    }

    @Override
    public String getPostCode() {
        return postCode.getOrInitializeValue();
    }

    @Override
    public String getMerchandiser() {
        return merchandiser.getOrInitializeValue();
    }

    @Override
    public String getBusinessName() {
        return businessName.getOrInitializeValue();
    }

    @Override
    public String getPigeonCoopID() {
        return pigeonCoopID.getOrInitializeValue();
    }

    private static class LazyLoad<T> {
        private T value;
        private final Supplier<T> initialiser;

        LazyLoad(Supplier<T> initialiser) {
            this.initialiser = initialiser;
        }

        T getOrInitializeValue() {
            if (value == null) {
                value = initialiser.get();
            }
            return value;
        }
    }
}

