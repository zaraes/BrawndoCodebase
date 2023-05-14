# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

#### <design pattern name>

- participant name (as defined in the lectures): correlated java class
- participant name (as defined in the lectures): correlated java class
- participant name (as defined in the lectures): correlated java class

### Too Many Orders

#### Alternative Solution (400 words max)

##### Solution Summary

I solved the issue of too many orders by simplifying the class structure. Many classes used duplicate methods. 

I made the class 'ConcreteOrder' to act as the base class for all regular orders, and 'ConcreteSubscriptionOrder' for
all subscription orders. ConcreteOrders and ConcreteSubscriptionOrders both respectively have common methods for
shortDesc() and longDesc() so they were configured within the classes.

I then applied the Strategy pattern on the method getTotalCost(). The Strategies 'BusinessBulkDiscountStrategy', 
'FirstOrderStrategy', 'NewOrderImplStrategy', and Order66Strategy' all implement 'DiscountStrategy' and encapsulate the 
different logic. 

I also applied the Strategy pattern on the method generateInvoiceData(), as each class implemented this method slightly
differently. The Strategies 'GenerateInvoiceOrderSimpleStrategy', and GenerateInvoiceOrderComplexStrategy implement
'GenerateInvoiceOrderStrategy'. The Strategies 'GenerateInvoiceSubscriptionSimpleStrategy', and 
'GenerateInvoiceSubscriptionSimpleStrategy' implement 'GenerateInvoiceSubscriptionStrategy'.

##### Solution Benefit

The Strategy pattern solved the large number of classes by removing unneccessary duplicate methods, and making one 
common ConcreteOrder and one ConcreteSubscriptionOrder class. Adding new Strategies is easily extendable and only 
neccessary when there is a new way to calculate totalCost() or generateInvoice().

### Bulky Contact Method


### System Lag


### Hard to Compare Products


### Slow Order Creation


## Notes About the Submission