package au.edu.sydney.brawndo.erp.spfea.ordering;
import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderUnitOfWork {
    private List<Order> newOrders;
    private List<Order> updatedOrders;
    private List<Order> removedOrders;

    public OrderUnitOfWork() {
        newOrders = new ArrayList<>();
        updatedOrders = new ArrayList<>();
        removedOrders = new ArrayList<>();
    }

    public void addNewOrder(Order order) {
        newOrders.add(order);
    }

    public void updateOrder(Order order) {
        updatedOrders.add(order);
    }

    public void removeOrder(Order order) {
        removedOrders.add(order);
    }

    public void commit(AuthToken token) {
        TestDatabase database = TestDatabase.getInstance();

        for (Order order : newOrders) {
            database.saveOrder(token, order);
        }

        for (Order order : updatedOrders) {
            database.removeOrder(token, order.getOrderID());
            database.saveOrder(token, order);
        }

        for (Order order : removedOrders) {
            database.removeOrder(token, order.getOrderID());
        }
        newOrders.clear();
        updatedOrders.clear();
        removedOrders.clear();
    }

}
