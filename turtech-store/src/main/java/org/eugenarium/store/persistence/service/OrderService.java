package org.eugenarium.store.persistence.service;

import org.eugenarium.store.persistence.domain.*;

/**
 * Class {@code BillingAddressService} is a general service to work with a
 * <i>BillingAddress</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface OrderService {

	/**
	 * Creates a {@code Order} by using the provided information.
	 * 
	 * @param shoppingCart    - a shopping cart which contains the items to be put
	 *                        in the order.
	 * @param shippingAddress - a shipping address specified by the user.
	 * @param billingAddress  - a billing address specified by the user.
	 * @param payment         - a payment method specified by the user.
	 * @param shippingMethod  - a shipping method chosen by the user.
	 * @param user            - the user who is placing the order.
	 * @return an {@code Order} that was created.
	 */
	Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user);

	/**
	 * Retrieves a {@code Order} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code Order} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	Order findById(Long id);
}
