# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

#### Flyweight

- Flyweight: `Flyweight`
- ConcreteFlyweight: `ProductFlyweight`
- FlyweightFactory: `ProductFlyweightFactory`
- Client: `ProductImpl`

##### Solution Summary
The issue of RAM is due to the Product storing a large amount of information within the class. By using the Flyweight design pattern, if a duplicate `Product` already exists, we can simply reuse the same object, as opposed to wasting memory making a duplicate. The `FlyweightFactory` stores `Flyweight`(s) and distributes the `Flyweight`(s) to the client when requested. The client then uses the `Flyweight` for all of it's attribute operations.

##### Solution Benefit
This solution reduces RAM by preventing duplicate classes.

### Too Many Orders
#### Strategy
- Strategy (1): `DiscountStrategy`
- ConcreteStrategy (1): `BusinessBulkDiscountStrategy`, `FirstOrderStrategy`, `NewOrderImplStrategy`, `Order66Strategy`

- Strategy (2.1): `GenerateInvoiceOrderStrategy`
- ConcreteStrategy (2): `GenerateInvoiceOrderSimpleStrategy`, `GenerateInvoiceOrderComplexStrategy`

- Strategy (2.2): `GenerateInvoiceSubscriptionStrategy`
- ConcreteStrategy (2.2): `GenerateInvoiceSubscriptionSimpleStrategy`, `GenerateInvoiceSubscriptionComplexStrategy`

- Context: `ConcreteOrder`, `ConcreteSubscriptionOrder`


##### Solution Summary

I solved the issue of too many orders by simplifying the class structure. Many classes used duplicate methods. 

I made the class `ConcreteOrder` to act as the base class for all regular orders, and `ConcreteSubscriptionOrder` for all subscription orders. `ConcreteOrders` and `ConcreteSubscriptionOrders` both respectively have common methods for `shortDesc()` and `longDesc()` so they were configured within the classes.

I then applied the Strategy pattern on the method `getTotalCost()`. The Strategies `BusinessBulkDiscountStrategy`, `FirstOrderStrategy`, `NewOrderImplStrategy`, and `Order66Strategy` all implement `DiscountStrategy` and encapsulate the different logic. 

I also applied the Strategy pattern on the method `generateInvoiceData()`, as each class implemented this method slightly
differently. The Strategies `GenerateInvoiceOrderSimpleStrategy`, and `GenerateInvoiceOrderComplexStrategy` implement
`GenerateInvoiceOrderStrategy`. The Strategies `GenerateInvoiceSubscriptionSimpleStrategy`, and 
`GenerateInvoiceSubscriptionComplexStrategy` implement `GenerateInvoiceSubscriptionStrategy`.

##### Solution Benefit

The Strategy pattern solved the large number of classes by removing unneccessary duplicate methods, and making one 
common `ConcreteOrder` and one `ConcreteSubscriptionOrder` class. Adding new Strategies is easily extendable and only 
neccessary when there is a new way to calculate `totalCost()` or `generateInvoice()`.

### Bulky Contact Method
#### Strategy
- Strategy: `SendInvoiceStrategy`
- ConcreteStrategy: `CarrierPigeonSendInvoiceStrategy`, `EmailSendInvoiceStrategy`, `MailSendInvoiceStrategy`, `MerchandiserSendInvoiceStrategy`, `PhoneCallSendInvoiceStrategy`, `SMSSendInvoiceStrategy`
- Context: `ContactHandler`

##### Solution Summary
The contact method in `ContactHandler` was bulky and long. I decided to break it up, however, I could not alter the contact classes which each had their own `sendInvoice()` method. I decided to make a strategy to deal with the different `sendInvoice()` method within `ContactHandler`. I made the strategy `SendInvoiceStrategy` and was implemented by `CarrierPigeonSendInvoiceStrategy`, `EmailSendInvoiceStrategy`, `MailSendInvoiceStrategy`, `MerchandiserSendInvoiceStrategy`, `PhoneCallSendInvoiceStrategy`, and `SMSSendInvoiceStrategy`. This `ContactHandler`(s) `sendInvoice` method to become a for loop, iterating over the different strategies for each `ContactMethod`. 
##### Solution Benefit
This solution allows us to break up the code, and add extra single responsibility classes when more contact methods are needed in future, as opposed to adding to a long method which stores all the logic.

### System Lag
#### Lazy load (Lazy initialization)
- Expensive Object: `CustomerImpl`
- Lazy load Initializer: `LazyLoad`

##### Solution Summary
Any time Customers are loaded from the database, the system loads for a long time – even if only 1 field of 1 customer is need. The `CustomerImpl` contains attributes that can be loaded when they are actually needed. The constructor for `CustomerImpl` initialises the `CustomerImpl` object with an authenitication token and customer id. It creates lazily loaded attributes using the `setupLazyAttribute()` method and assigns them function that retrieve the corresponding customer fields from the database.

The method `setupLazyAttribute()` is a helper method that creates a `LazyLoad<T>` object. It takes a `Supplier<T>` initializer function as a parameter, which is responsible for providing the inital value of the lazily loaded attribute.

The class provides getter methods for retrieving various customer attributes. These getter methods use the `getValue()` method of the corresponding `Lazy<T>` objects to retrieve the attribute values. If the value has not been loaded yet, the `getValue()` method triggers the initialisation by calling the initializer function and caches the value for future requests.

The inner class `Lazy<T>` is a generic class used to implement lazy initialization. It has a private instance variable `value` to store the value once it is initialized and a `Supplier<T>` initialiser to provide the initial value when needed. The `getValue()` method checks if the value is null and initializes it by calling the initializer function if necessary. It then returns the cached or newly initialized value.

##### Solution Benefit
The lazy initalization design pattern allows attribute values to be loaded on demand when they are first accessed, rather than eagerly loading all attributes at once. This approach improves performance by avoiding unnecessary loading of attributes that might not be needed and deferring resource intensive operations until necessary.
### Hard to Compare Products
#### Value Object
- Value Object: `ProductImpl`

##### Solution Summary
The presented problem was that it was difficult to compare `Product`(s) as `Product`(s) did not have id's, and each name was not neccessarily unique. A idea behind value object, is to make objects more self-contained and immutable, which makes them easier to compare for equality. I decided to use `ProductImpl` as my value object, making all the fields 'final' to make them immutable. I then created a `ProductComparison` class to encapsulte the logic of comparing 2 `Product` objects. I gave it a static method `compare()`, which allows it to be used anywhere without needing an instance of the class.

Ideally I would want to have an `equals()` method within the value object, however, I cannot modify the interface, and `Product`(s) are used via the interface. I decided it would be better practice to simply make a comparator class, which assists with single responsibility.

##### Solution Benefit
This solution removes repetative comparison code, and allows `ProductImpl` to be used as a reliable immutable object across the codebase.


### Slow Order Creation


## Notes About the Submission